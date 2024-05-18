package br.com.fiap.postech.configuration

import br.com.fiap.postech.infraestucture.repository.entitiy.Products
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import io.ktor.util.logging.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseSingleton {
    fun init(config: ApplicationConfig, logger: Logger) {

        val hikariConfig = HikariConfig().apply {
            jdbcUrl = config.property("storage.url").getString()
            driverClassName = config.property("storage.driver").getString()
            username = config.property("storage.user").getString()
            password = config.property("storage.password").getString()
            maximumPoolSize = config.property("storage.poolSize").getString().toInt()
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }

        val dataSource = HikariDataSource(hikariConfig)
        Database.connect(dataSource)

        try {
            transaction {
                productsMigration()
            }
            logger.info("Database connection and migration successful.")
        } catch (ex: Exception) {
            logger.error("Error during database connection or migration: ${ex.message}", ex)
            throw ex
        }
    }
}

private fun productsMigration() {
    SchemaUtils.create(Products)
}