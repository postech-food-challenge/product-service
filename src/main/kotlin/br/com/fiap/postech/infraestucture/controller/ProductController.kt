package br.com.fiap.postech.infraestucture.controller

import br.com.fiap.postech.application.usecases.*
import br.com.fiap.postech.domain.entities.Product
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
        createProductInteract.create(product)
        call.respondText("Product created successfully", status = HttpStatusCode.Created)
    }
}

fun Route.deleteProductRoute() {
    val deleteProductInteract: DeleteProductInteract by inject()
    delete(path = "/v1/products/{id?}") {
        val productId = call.parameters["id"]?.toLongOrNull() ?: return@delete call.respondText(
            "Missing or malformed id",
            status = HttpStatusCode.BadRequest
        )
        deleteProductInteract.delete(productId)
        call.respond(HttpStatusCode.NoContent, "Product deleted")
    }
}

fun Route.updateProductRoute() {
    val updateProductInteract: UpdateProductInteract by inject()

    put("/v1/products/{id?}") {
        val id = call.parameters["id"]?.toLongOrNull() ?: return@put call.respondText(
            "Missing or malformed id",
            status = HttpStatusCode.BadRequest
        )

        val productRequest = call.receive<UpdateProductRequest>()
        val product = Product.fromRequest(productRequest)
        updateProductInteract.update(id, product)
        call.respond(HttpStatusCode.OK, "Product created successfully")
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
        val id = call.parameters["id"]?.toLongOrNull() ?: return@get call.respondText(
            "Missing or malformed id",
            status = HttpStatusCode.BadRequest
        )

        val product = findProductByIdInteract.find(id)?.let { product -> ProductResponse.fromDomain(product) }
        if (product != null) {
            call.respond(product)
        } else {
            call.respondText(
                "No product found with id $id",
                status = HttpStatusCode.NotFound
            )
        }
    }
}