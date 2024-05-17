package br.com.fiap.postech.application.usecases

import br.com.fiap.postech.application.gateways.ProductGateway
import br.com.fiap.postech.domain.entities.Product
import br.com.fiap.postech.domain.exceptions.ProductNotFoundException

class UpdateProductInteract(private val gateway: ProductGateway) {
    fun update(id: Long, newProduct: Product) =
        gateway.findById(id)
            ?.let { domainProduct ->
                domainProduct.update(newProduct).let { updatedProduct ->
                    gateway.update(updatedProduct)
                }
            } ?: throw ProductNotFoundException(id)
}