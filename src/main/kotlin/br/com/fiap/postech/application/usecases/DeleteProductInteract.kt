package br.com.fiap.postech.application.usecases

import br.com.fiap.postech.application.gateways.ProductGateway
import br.com.fiap.postech.domain.exceptions.ProductNotFoundException

class DeleteProductInteract(private val gateway: ProductGateway) {
    fun delete(id: Long) =
        gateway.findById(id)
            ?.let { domainProduct ->
                gateway.delete(domainProduct.id!!)
            } ?: throw ProductNotFoundException(id)
}