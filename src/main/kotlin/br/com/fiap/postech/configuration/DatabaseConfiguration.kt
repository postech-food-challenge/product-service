package br.com.fiap.postech.configuration

import br.com.fiap.postech.infraestucture.repository.entitiy.Products
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseConfiguration {
    fun init() {
        val database = Database.connect(
            url = "jdbc:postgresql://localhost:5432/products-db",
            driver = "org.postgresql.Driver",
            user = "food-challenge",
            password = "root"
        )

        transaction (database) {
            org.jetbrains.exposed.sql.SchemaUtils.create(Products)

            productsMigration()
        }
    }
}

private fun productsMigration() {
    Products.insert {
        it[Products.name] = "X-BACON"
        it[Products.price] = 22
        it[Products.image] = "NOHAVE"
        it[Products.category] = "SANDUICHE"
        it[Products.description] = "Sanduiche com bacon"
    }
    Products.insert {
        it[Products.name] = "COCA-COLA"
        it[Products.price] = 8
        it[Products.image] = "NOHAVE"
        it[Products.category] = "BEBIDA"
        it[Products.description] = "Uma bebida maravilhosa a base de cola"
    }
}