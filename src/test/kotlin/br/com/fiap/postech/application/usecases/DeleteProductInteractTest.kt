package br.com.fiap.postech.application.usecases

import br.com.fiap.postech.application.gateways.ProductGateway
import br.com.fiap.postech.domain.entities.Category
import br.com.fiap.postech.domain.entities.Product
import br.com.fiap.postech.domain.exceptions.ProductNotFoundException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith


class DeleteProductInteractTest {
    private val gateway: ProductGateway = mockk()
    private val deleteProductInteract = DeleteProductInteract(gateway)

    @Test
    fun `should throw ProductNotFoundException when product does not exist`() = runTest {
        val productId = 1L

        coEvery { gateway.findById(id = productId) } throws ProductNotFoundException(productId)

        assertFailsWith<ProductNotFoundException> {
            deleteProductInteract.delete(productId)
        }

        coVerify(exactly = 0) { gateway.delete(any()) }
    }

    @Test
    fun `should delete product when product exists`() = runTest {
        val existingProduct = Product(
            id = 1,
            name = "ExistingProduct",
            description = "Existing product description",
            image = "existing_image.jpg",
            price = 100,
            category = Category.MAIN
        )

        coEvery { gateway.findById(existingProduct.id!!) } returns existingProduct
        coEvery { gateway.delete(existingProduct) } returns Unit

        deleteProductInteract.delete(existingProduct.id!!)

        coVerify(exactly = 1) { gateway.findById(existingProduct.id!!) }
        coVerify(exactly = 1) { gateway.delete(existingProduct) }
    }
}