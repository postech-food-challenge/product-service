package br.com.fiap.postech.application.usecases

import br.com.fiap.postech.application.gateways.ProductGateway

class DeleteProductInteract(private val gateway: ProductGateway) {
    suspend fun delete(id: Long) = gateway.findById(id).run { gateway.delete(this) }
}