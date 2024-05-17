package br.com.fiap.postech.application.usecases

import br.com.fiap.postech.application.gateways.ProductGateway
import br.com.fiap.postech.domain.entities.Product

class FindProductByNameInteract(private val gateway: ProductGateway) {

    fun find(name: String): Product? {
        return gateway.findByName(name)
    }

}