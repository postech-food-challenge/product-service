package br.com.fiap.postech.configuration

import br.com.fiap.postech.application.usecases.*
import br.com.fiap.postech.infraestucture.gateway.ProductGatewayImpl
import br.com.fiap.postech.infraestucture.repository.ProductRepositoryImpl
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(modules)
    }
}

val modules = module {
    val productRepository = ProductRepositoryImpl()
    val productGateway = ProductGatewayImpl(productRepository)
    single<ProductGatewayImpl> { productGateway }
    single<CreateProductInteract> { CreateProductInteract(productGateway) }
    single<DeleteProductInteract> { DeleteProductInteract(productGateway) }
    single<FindProductByCategoryInteract> { FindProductByCategoryInteract(productGateway) }
    single<FindProductByIdInteract> { FindProductByIdInteract(productGateway) }
    single<FindProductByNameInteract> { FindProductByNameInteract(productGateway) }
    single<UpdateProductInteract> { UpdateProductInteract(productGateway) }
}

