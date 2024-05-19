package br.com.fiap.postech.application.gateways

import br.com.fiap.postech.domain.entities.Category
import br.com.fiap.postech.domain.entities.Product

interface ProductGateway {
    suspend fun findById(id: Long): Product
    suspend fun findByName(name: String): Product?
    suspend fun findByCategory(category: Category): List<Product>?
    suspend fun update(product: Product): Product
    suspend fun delete(product: Product)
    suspend fun insert(product: Product): Product
}