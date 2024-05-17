package br.com.fiap.postech.infraestucture.repository

import br.com.fiap.postech.infraestucture.repository.entitiy.ProductEntity
import br.com.fiap.postech.infraestucture.repository.entitiy.Products
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ProductRepositoryImpl: ProductRepository {
    override fun findById(id: Long): ProductEntity? {
        return transaction {
            ProductEntity.findById(id)
        }
    }

    override fun findByCategory(category: String): List<ProductEntity> {
        return transaction {
            ProductEntity.find(Products.category eq category).toList()
        }
    }

    override fun create(newProduct: ProductEntity): ProductEntity? {
        TODO("Not yet implemented")
    }

    override fun update(id: Long, newStatus: String): ProductEntity? {
        TODO("Not yet implemented")
    }

    override fun delete(status: String): List<ProductEntity> {
        TODO("Not yet implemented")
    }
}