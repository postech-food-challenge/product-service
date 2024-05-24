package br.com.fiap.postech.configuration

import br.com.fiap.postech.application.gateways.ProductGateway
import br.com.fiap.postech.application.usecases.*
import br.com.fiap.postech.infrastucture.gateway.ProductGatewayImpl
import br.com.fiap.postech.infrastucture.persistence.ProductFacade
import br.com.fiap.postech.infrastucture.persistence.ProductFacadeImpl
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(appModules)
    }
}

val appModules = module {
    single<ProductFacade> { ProductFacadeImpl() }
    single<ProductGateway> { ProductGatewayImpl(get()) }
    single { CreateProductInteract(get()) }
    single { DeleteProductInteract(get()) }
    single { FindProductByCategoryInteract(get()) }
    single { FindProductByIdInteract(get()) }
    single { UpdateProductInteract(get()) }
}
