package br.com.fiap.postech.infraestucture.persistence.entitiy

import br.com.fiap.postech.domain.entities.Product
import org.jetbrains.exposed.dao.id.LongIdTable

data class ProductEntity(
    val id: Long? = 0L,
    val name: String,
    val description: String,
    val image: String,
    val price: Int,
    val category: String
) {
    companion object {
        fun fromDomain(domain: Product): ProductEntity {
            return ProductEntity(
                id = domain.id,
                name = domain.name,
                description = domain.description,
                image = domain.image,
                price = domain.price,
                category = domain.category.name
            )
        }
    }
}

object Products : LongIdTable() {
    val name = varchar("name", 30).uniqueIndex()
    val description = varchar("description", 50)
    val image = varchar("image", 1024)
    val price = integer("price")
    val category = varchar("category", 1024).index()
}