package br.com.fiap.postech.application.usecases

import br.com.fiap.postech.application.gateways.ProductGateway
import br.com.fiap.postech.domain.entities.Product

class UpdateProductInteract(private val gateway: ProductGateway) {
    suspend fun update(id: Long, newProduct: Product) = gateway.findById(id)
        .run { update(newProduct) }
        .run { gateway.update(this) }
}
