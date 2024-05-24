package br.com.fiap.postech.infrastucture.controller

import br.com.fiap.postech.application.usecases.*
import br.com.fiap.postech.domain.entities.Product
import br.com.fiap.postech.domain.exceptions.InvalidParameterException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.createProductRoute() {
    val createProductInteract: CreateProductInteract by inject()

    post("/v1/products") {
        val productRequest = call.receive<CreateProductRequest>()
        val product = Product.fromRequest(productRequest)
        val createdProduct = ProductResponse.fromDomain(createProductInteract.create(product))
        call.response.header(HttpHeaders.Location, "/v1/products/${createdProduct.id}")
        call.respond(HttpStatusCode.Created, createdProduct)
    }
}

fun Route.deleteProductRoute() {
    val deleteProductInteract: DeleteProductInteract by inject()
    delete(path = "/v1/products/{id?}") {
        val id = call.parameters["id"]?.toLongOrNull() ?: throw InvalidParameterException("Missing or malformed id")
        deleteProductInteract.delete(id)
        call.respond(HttpStatusCode.NoContent, "Product deleted")
    }
}

fun Route.updateProductRoute() {
    val updateProductInteract: UpdateProductInteract by inject()

    put("/v1/products/{id?}") {
        val id = call.parameters["id"]?.toLongOrNull() ?: throw InvalidParameterException("Missing or malformed id")

        val productRequest = call.receive<UpdateProductRequest>()
        val product = Product.fromRequest(productRequest)
        val updatedProduct = ProductResponse.fromDomain(updateProductInteract.update(id, product))
        call.respond(updatedProduct)
    }
}

fun Route.findProductByCategoryRoute() {
    val findProductByCategoryInteract: FindProductByCategoryInteract by inject()

    get("/v1/products/category/{category}") {
        val category = call.parameters["category"] ?: throw IllegalArgumentException("Category is missing")
        val products = findProductByCategoryInteract.find(category).map { ProductResponse.fromDomain(it) }
        call.respond(products)
    }
}

fun Route.findProductByIdRoute() {
    val findProductByIdInteract: FindProductByIdInteract by inject()

    get("/v1/products/{id?}") {
        val id = call.parameters["id"]?.toLongOrNull() ?: throw InvalidParameterException("Missing or malformed id")
        val product = findProductByIdInteract.find(id).run { ProductResponse.fromDomain(this) }
        call.respond(product)
    }
}