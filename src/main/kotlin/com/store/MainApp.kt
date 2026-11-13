package com.store

import com.fasterxml.jackson.databind.cfg.CoercionAction
import com.fasterxml.jackson.databind.cfg.CoercionInputShape
import com.fasterxml.jackson.databind.type.LogicalType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
open class Application {
    @Bean
    open fun products(): Products = Products()

    @Bean
    open fun jsonCoercion(): Jackson2ObjectMapperBuilderCustomizer =
        Jackson2ObjectMapperBuilderCustomizer { builder ->
            builder.postConfigurer { mapper ->
                mapper.coercionConfigFor(LogicalType.Textual).apply {
                    setCoercion(CoercionInputShape.Integer, CoercionAction.Fail)
                    setCoercion(CoercionInputShape.Float, CoercionAction.Fail)
                    setCoercion(CoercionInputShape.Boolean, CoercionAction.Fail)
                }
            }
        }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}