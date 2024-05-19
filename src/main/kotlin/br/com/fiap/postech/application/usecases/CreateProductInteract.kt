package br.com.fiap.postech.application.usecases

import br.com.fiap.postech.application.gateways.ProductGateway
import br.com.fiap.postech.domain.entities.Product
import br.com.fiap.postech.domain.exceptions.ProductAlreadyExistsException

class CreateProductInteract(private val gateway: ProductGateway) {
    suspend fun create(product: Product) =
        gateway.findByName(product.name)?.let {
            throw ProductAlreadyExistsException(product.name)
        } ?: gateway.insert(product)
}