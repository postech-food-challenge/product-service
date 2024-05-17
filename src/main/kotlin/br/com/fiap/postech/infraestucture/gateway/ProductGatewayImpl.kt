package br.com.fiap.postech.infraestucture.gateway

import br.com.fiap.postech.application.gateways.ProductGateway
import br.com.fiap.postech.domain.entities.Category
import br.com.fiap.postech.domain.entities.Product
import br.com.fiap.postech.infraestucture.repository.ProductRepository

class ProductGatewayImpl(private val repository: ProductRepository) : ProductGateway {
    override fun findById(id: Long): Product? {
        TODO("Not yet implemented")
    }

    override fun findByName(name: String): Product? {
        TODO("Not yet implemented")
    }

    override fun findByCategory(category: Category): List<Product> {
        TODO("Not yet implemented")
    }

    override fun create(newProduct: Product): Product? {
        TODO("Not yet implemented")
    }

    override fun update(updatedProduct: Product): Product? {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }

}