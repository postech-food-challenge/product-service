package br.com.fiap.postech.application.usecases

import br.com.fiap.postech.application.gateways.ProductGateway
import br.com.fiap.postech.domain.entities.Category
import br.com.fiap.postech.domain.entities.Product
import br.com.fiap.postech.domain.exceptions.NoObjectFoundException

class FindProductByCategoryInteract(private val gateway: ProductGateway) {
    suspend fun find(category: String): List<Product> =
        Category.validateCategory(category).let { validCategory ->
            gateway.findByCategory(validCategory)?.takeIf { it.isNotEmpty() }
                ?: throw NoObjectFoundException("No products found for category $category.")
        }
}