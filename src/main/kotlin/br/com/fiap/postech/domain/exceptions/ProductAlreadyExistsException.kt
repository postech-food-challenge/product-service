package br.com.fiap.postech.domain.exceptions

class ProductAlreadyExistsException(name: String) :
    RuntimeException("Product with name $name already exists.") {
}