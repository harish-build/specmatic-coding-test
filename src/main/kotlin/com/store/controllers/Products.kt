package com.store.controllers

import com.store.Product
import com.store.ProductType
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController("productsController")
class Products(private val products: com.store.Products) {
    @GetMapping("/products")
    fun list(@RequestParam(required = false) type: ProductType?) = products.list(type)

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody(required = false) product: Product?) =
        mapOf("id" to products.create(product))
}
