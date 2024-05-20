package br.com.fiap.postech.application.usecases

import br.com.fiap.postech.application.gateways.ProductGateway
import br.com.fiap.postech.domain.entities.Category
import br.com.fiap.postech.domain.entities.Product
import br.com.fiap.postech.domain.exceptions.ProductNotFoundException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

import kotlin.test.assertFailsWith

@ExperimentalCoroutinesApi
class UpdateProductInteractTest {

    private val gateway: ProductGateway = mockk()
    private val updateProductInteract = UpdateProductInteract(gateway)

    @Test
    fun `should throw ProductNotFoundException when product does not exist`() = runTest {
        val productId = 1L
        val newProduct = Product(
            id = productId,
            name = "UpdatedProduct",
            description = "Updated product description",
            image = "updated_image.jpg",
            price = 150,
            category = Category.MAIN
        )
        coEvery { gateway.findById(productId) } throws ProductNotFoundException(productId)

        assertFailsWith<ProductNotFoundException> {
            updateProductInteract.update(productId, newProduct)
        }

        coVerify(exactly = 0) { gateway.update(any()) }
    }

    @Test
    fun `should update product when product exists`() = runTest {
        val existingProduct = Product(
            id = 1,
            name = "ExistingProduct",
            description = "Existing product description",
            image = "existing_image.jpg",
            price = 100,
            category = Category.MAIN
        )
        val updatedProduct = Product(
            id = 1,
            name = "UpdatedProduct",
            description = "Updated product description",
            image = "updated_image.jpg",
            price = 150,
            category = Category.MAIN
        )
        coEvery { gateway.findById(existingProduct.id!!) } returns existingProduct
        coEvery { gateway.update(any()) } returns updatedProduct

        val result = updateProductInteract.update(existingProduct.id!!, updatedProduct)

        assert(result == updatedProduct)

        coVerify(exactly = 1) { gateway.findById(existingProduct.id!!) }
        coVerify(exactly = 1) { gateway.update(updatedProduct) }
    }
}