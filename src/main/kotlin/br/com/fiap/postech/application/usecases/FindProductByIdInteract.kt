package br.com.fiap.postech.application.usecases

import br.com.fiap.postech.application.gateways.ProductGateway

class FindProductByIdInteract(private val gateway: ProductGateway) {
    suspend fun find(id: Long) = gateway.findById(id)
}