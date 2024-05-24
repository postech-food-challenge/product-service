package br.com.fiap.postech.infrastucture.persistence

import br.com.fiap.postech.infrastucture.persistence.entitiy.ProductEntity


interface ProductFacade {
    suspend fun findById(id: Long): ProductEntity?
    suspend fun findByName(name: String): ProductEntity?
    suspend fun findByCategory(category: String): List<ProductEntity>?
    suspend fun update(product: ProductEntity): ProductEntity
    suspend fun insert(product: ProductEntity): ProductEntity
    suspend fun delete(product: ProductEntity): Int?
}