package br.com.fiap.postech.infraestucture.gateway

import br.com.fiap.postech.application.gateways.ProductGateway
import br.com.fiap.postech.domain.entities.Category
import br.com.fiap.postech.domain.entities.Product
import br.com.fiap.postech.infraestucture.persistence.ProductFacade
import br.com.fiap.postech.infraestucture.persistence.entitiy.ProductEntity

class ProductGatewayImpl(private val facade: ProductFacade) : ProductGateway {
    override suspend fun findById(id: Long) = facade.findById(id)?.let { Product.fromEntity(it) }

    override suspend fun findByName(name: String) = facade.findByName(name)?.let { Product.fromEntity(it) }

    override suspend fun findByCategory(category: Category): List<Product>? =
        facade.findByCategory(category.name)?.map { Product.fromEntity(it) }

    override suspend fun save(product: Product): Product {
        ProductEntity.fromDomain(product).let { productEntity ->
            val savedEntity =
                facade.save(productEntity) ?: throw IllegalArgumentException("Saved ProductEntity cannot be null")
            return Product.fromEntity(savedEntity)
        }
    }

    override suspend fun delete(product: Product) {
        ProductEntity.fromDomain(product)
            .let { entity -> facade.delete(entity) }
    }
}