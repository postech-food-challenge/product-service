package br.com.fiap.postech.configuration

import br.com.fiap.postech.application.usecases.*
import br.com.fiap.postech.domain.entities.Product
import br.com.fiap.postech.infraestucture.controller.CreateProductRequest
import br.com.fiap.postech.infraestucture.controller.UpdateProductRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val findProductByNameInteract by inject<FindProductByNameInteract>()
    val findProductByIdInteract by inject<FindProductByIdInteract>()
    val findProductByCategoryInteract by inject<FindProductByCategoryInteract>()
    val createProductInteract by inject<CreateProductInteract>()
    val updateProductInteract by inject<UpdateProductInteract>()
    val deleteProductInteract by inject<DeleteProductInteract>()

    // TODO: TIRAR ESSE !! DOS MÉTODOS AQUI
    routing {
        route("/product") {
            get("/{id}") {
                val productId = call.parameters["id"]?.toLongOrNull();

                if (productId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                    return@get
                }

                call.respond(
                    HttpStatusCode.OK,
                    findProductByIdInteract.find(productId)!!
                );
            }

            get("/nome/{nome}") {
                val name = call.parameters["nome"];

                if (name == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                    return@get
                }

                call.respond(
                    HttpStatusCode.OK,
                    findProductByNameInteract.find(name)!!
                );
            }

            get("/category/{category}") {
                val category = call.parameters["category"];

                if (category == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                    return@get
                }

                call.respond(
                    HttpStatusCode.OK,
                    findProductByCategoryInteract.find(category)
                );
            }

            post("/") {
                val category = call.parameters["category"];

                val body = call.receive<CreateProductRequest>()

                // TODO: ADICIONAR VALIDAÇÃO AQUI ?

                call.respond(
                    HttpStatusCode.OK,
                    createProductInteract.create(Product.fromRequest(body))!!
                );
            }

            put("/{id}") {
                val id = call.parameters["id"]?.toLongOrNull();

                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                    return@put
                }

                val body = call.receive<UpdateProductRequest>()

                // TODO: ADICIONAR VALIDAÇÃO AQUI ?

                call.respond(
                    HttpStatusCode.OK,
                    updateProductInteract.update(id, Product.fromRequest(body))
                );
            }

            delete("/{id}") {
                val id = call.parameters["id"]?.toLongOrNull();

                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                    return@delete
                }

                call.respond(
                    HttpStatusCode.OK,
                    deleteProductInteract.delete(id)
                );
            }


        }

    }
}
