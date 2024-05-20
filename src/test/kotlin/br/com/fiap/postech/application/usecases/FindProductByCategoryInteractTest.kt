package br.com.fiap.postech.application.usecases

import br.com.fiap.postech.application.gateways.ProductGateway
import br.com.fiap.postech.domain.entities.Category
import br.com.fiap.postech.domain.entities.Product
import br.com.fiap.postech.domain.exceptions.InvalidParameterException
import br.com.fiap.postech.domain.exceptions.NoObjectFoundException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class FindProductByCategoryInteractTest {

    private val gateway: ProductGateway = mockk()
    private val findProductByCategoryInteract = FindProductByCategoryInteract(gateway)

    @Test
    fun `should throw InvalidParameterException for invalid category`() = runTest {
        val invalidCategory = "INVALID_CATEGORY"

        assertFailsWith<InvalidParameterException> {
            findProductByCategoryInteract.find(invalidCategory)
        }

        coVerify(exactly = 0) { gateway.findByCategory(any()) }
    }

    @Test
    fun `should throw NoObjectFoundException when no products found`() = runTest {
        val category = Category.DRINK.name

        coEvery { gateway.findByCategory(Category.DRINK) } returns emptyList()

        assertFailsWith<NoObjectFoundException> {
            findProductByCategoryInteract.find(category)
        }

        coVerify(exactly = 1) { gateway.findByCategory(Category.DRINK) }
    }

    @Test
    fun `should return list of products when products are found`() = runTest {
        val category = Category.DESSERT.name
        val products = listOf(
            Product(
                id = 1,
                name = "Cake",
                description = "Delicious cake",
                image = "cake.jpg",
                price = 500,
                category = Category.DESSERT
            ),
            Product(
                id = 2,
                name = "Ice Cream",
                description = "Cool ice cream",
                image = "icecream.jpg",
                price = 300,
                category = Category.DESSERT
            )
        )

        coEvery { gateway.findByCategory(Category.DESSERT) } returns products

        val result = findProductByCategoryInteract.find(category)

        assertTrue { result.size == 2 }
        assertTrue { result.containsAll(products) }

        coVerify(exactly = 1) { gateway.findByCategory(Category.DESSERT) }
    }
}