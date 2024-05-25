package br.com.fiap.postech.infrastructure.controller

import kotlinx.serialization.Serializable

@Serializable
data class CreateProductRequest(
    val name: String,
    val description: String,
    val image: String,
    val price: Int,
    val category: String
)
