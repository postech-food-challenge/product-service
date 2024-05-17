package br.com.fiap.postech.infraestucture.repository

import br.com.fiap.postech.infraestucture.repository.entitiy.ProductEntity


interface ProductRepository {
    fun findById(id: Long): ProductEntity?
    fun findByCategory(category: String): List<ProductEntity>
    fun create(newProduct: ProductEntity): ProductEntity?
    fun update(id: Long, newStatus: String): ProductEntity?
    fun delete(status: String): List<ProductEntity>
}