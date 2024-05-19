package br.com.fiap.postech.infraestucture.persistence

import br.com.fiap.postech.configuration.DatabaseSingleton.dbQuery
import br.com.fiap.postech.domain.exceptions.DatabaseOperationException
import br.com.fiap.postech.infraestucture.persistence.entitiy.ProductEntity
import br.com.fiap.postech.infraestucture.persistence.entitiy.Products
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ProductFacadeImpl : ProductFacade {
    private fun resultRowToProductEntity(row: ResultRow?): ProductEntity? {
        return if (row == null) {
            null
        } else {
            ProductEntity(
                id = row[Products.id].value,
                name = row[Products.name],
                description = row[Products.description],
                image = row[Products.image],
                price = row[Products.price],
                category = row[Products.category]
            )
        }
    }

    override suspend fun findById(id: Long): ProductEntity? = dbQuery {
        Products
            .select { Products.id eq id }
            .map(::resultRowToProductEntity)
            .singleOrNull()
    }

    override suspend fun findByName(name: String): ProductEntity? = dbQuery {
        Products
            .select { Products.name eq name }
            .map(::resultRowToProductEntity)
            .singleOrNull()
    }

    override suspend fun findByCategory(category: String): List<ProductEntity>? = dbQuery {
        Products
            .select { Products.category eq category }
            .mapNotNull(::resultRowToProductEntity)
    }


    override suspend fun update(product: ProductEntity): ProductEntity = dbQuery {
        val productId = product.id ?: throw DatabaseOperationException("Product ID cannot be null for update operation")

        val rowsUpdated = Products.update({ Products.id eq productId }) {
            it[name] = product.name
            it[description] = product.description
            it[image] = product.image
            it[price] = product.price
            it[category] = product.category
        }

        if (rowsUpdated > 0) {
            Products.select { Products.id eq productId }
                .mapNotNull { resultRowToProductEntity(it) }
                .singleOrNull()
                ?: throw DatabaseOperationException("Failed to retrieve updated product with ID: $productId")
        } else {
            throw DatabaseOperationException("Failed to update product with ID: $productId")
        }
    } ?: throw DatabaseOperationException("Failed to update product")

    override suspend fun insert(product: ProductEntity): ProductEntity = dbQuery {
        val id = Products.insertAndGetId {
            it[name] = product.name
            it[description] = product.description
            it[image] = product.image
            it[price] = product.price
            it[category] = product.category
        }.value

        product.copy(id = id)
    } ?: throw DatabaseOperationException("Failed to insert product")

    override suspend fun delete(product: ProductEntity) = dbQuery {
        Products.deleteWhere { Products.id eq product.id }
    }
}