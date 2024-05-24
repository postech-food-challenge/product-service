package br.com.fiap.postech.infrastucture.controller

import br.com.fiap.postech.domain.entities.Product
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val id: Long?,
    val name: String,
    val description: String,
    val image: String,
    val price: Int,
    val category: String
) {
    companion object {
        fun fromDomain(domain: Product): ProductResponse {
            return ProductResponse(
                id = domain.id,
                name = domain.name,
                description = domain.description,
                image = domain.image,
                price = domain.price,
                category = domain.category.name
            )
        }
    }
}