package br.com.fiap.postech.infraestucture.persistence

import br.com.fiap.postech.infraestucture.persistence.entitiy.ProductEntity


interface ProductFacade {
    suspend fun findById(id: Long): ProductEntity?
    suspend fun findByName(name: String): ProductEntity?
    suspend fun findByCategory(category: String): List<ProductEntity>?
    suspend fun save(product: ProductEntity): ProductEntity?
    suspend fun delete(product: ProductEntity): Int?
}