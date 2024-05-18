package br.com.fiap.postech.application.usecases

import br.com.fiap.postech.application.gateways.ProductGateway
import br.com.fiap.postech.domain.entities.Product

class FindProductByIdInteract(private val gateway: ProductGateway) {
    suspend fun find(id: Long): Product? = gateway.findById(id)
}