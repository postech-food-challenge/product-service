package br.com.fiap.postech.domain.exceptions

import br.com.fiap.postech.domain.entities.Category

class ProductsNotFoundInCategoryException(category: Category) :
    RuntimeException("There are no products for category $category.") {
}