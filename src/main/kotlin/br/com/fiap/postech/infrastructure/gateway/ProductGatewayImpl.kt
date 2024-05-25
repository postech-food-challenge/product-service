package br.com.fiap.postech.infrastructure.gateway

import br.com.fiap.postech.application.gateways.ProductGateway
import br.com.fiap.postech.domain.entities.Category
import br.com.fiap.postech.domain.entities.Product
import br.com.fiap.postech.domain.exceptions.ProductNotFoundException
import br.com.fiap.postech.infrastructure.persistence.ProductFacade
import br.com.fiap.postech.infrastructure.persistence.entitiy.ProductEntity

class ProductGatewayImpl(private val facade: ProductFacade) : ProductGateway {
    override suspend fun findById(id: Long) =
        facade.findById(id)?.let { Product.fromEntity(it) } ?: throw ProductNotFoundException(id)

    override suspend fun findByName(name: String) = facade.findByName(name)?.let { Product.fromEntity(it) }

    override suspend fun findByCategory(category: Category): List<Product>? =
        facade.findByCategory(category.name)?.map { Product.fromEntity(it) }

    override suspend fun update(product: Product): Product {
        ProductEntity.fromDomain(product).let { productEntity ->
            val savedEntity = facade.update(productEntity)
            return Product.fromEntity(savedEntity)
        }
    }

    override suspend fun insert(product: Product): Product {
        ProductEntity.fromDomain(product).let { productEntity ->
            val savedEntity = facade.insert(productEntity)
            return Product.fromEntity(savedEntity)
        }
    }

    override suspend fun delete(product: Product) {
        ProductEntity.fromDomain(product)
            .let { entity -> facade.delete(entity) }
    }
}