package br.com.fiap.postech.domain.exceptions

class ProductNotFoundException(productId: Long) : RuntimeException("Product with ID: $productId not found.") {
}
