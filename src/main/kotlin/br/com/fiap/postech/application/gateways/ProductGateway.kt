package br.com.fiap.postech.application.gateways

import br.com.fiap.postech.domain.entities.Category
import br.com.fiap.postech.domain.entities.Product
import br.com.fiap.postech.infraestucture.repository.entitiy.ProductEntity

interface ProductGateway {
    fun findById(id: Long): Product?
    fun findByName(name: String): Product?
    fun findByCategory(category: Category): List<Product>
    fun create(newProduct: Product): Product?
    fun update(updatedProduct: Product): Product?
    fun delete(id: Long)
}