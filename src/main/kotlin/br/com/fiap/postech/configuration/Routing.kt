package br.com.fiap.postech.configuration

import br.com.fiap.postech.infraestucture.controller.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        createProductRoute()
        updateProductRoute()
        deleteProductRoute()
        findProductByIdRoute()
        findProductByCategoryRoute()
    }
}
