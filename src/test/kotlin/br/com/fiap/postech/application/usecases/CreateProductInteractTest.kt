package br.com.fiap.postech.application.usecases

import br.com.fiap.postech.application.gateways.ProductGateway
import br.com.fiap.postech.domain.entities.Category
import br.com.fiap.postech.domain.entities.Product
import br.com.fiap.postech.domain.exceptions.ProductAlreadyExistsException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

@ExperimentalCoroutinesApi
class CreateProductInteractTest {
    private val gateway: ProductGateway = mockk()
    private val createProductInteract = CreateProductInteract(gateway)

    @Test
    fun `should throw ProductAlreadyExistsException when product already exists`() = runTest {
        val existingProduct = Product(
            id = 1,
            name = "ExistingProduct",
            description = "Existing product description",
            image = "existing_image.jpg",
            price = 100,
            category = Category.MAIN
        )
        coEvery { gateway.findByName(existingProduct.name) } returns existingProduct

        assertFailsWith<ProductAlreadyExistsException> {
            createProductInteract.create(existingProduct)
        }

        coVerify(exactly = 0) { gateway.insert(any()) }
    }

    @Test
    fun `should insert product when product does not exist`() = runTest {
        val newProduct = Product(
            name = "NewProduct",
            description = "New product description",
            image = "new_image.jpg",
            price = 200,
            category = Category.MAIN
        )
        coEvery { gateway.findByName(newProduct.name) } returns null
        coEvery { gateway.insert(newProduct) } returns newProduct

        createProductInteract.create(newProduct)

        coVerify(exactly = 1) { gateway.insert(newProduct) }
    }
}