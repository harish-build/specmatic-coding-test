package com.store

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

enum class ProductType {
    book,
    food,
    gadget,
    other,
}

data class Product(
    @param:JsonProperty("name")
    val name: String,
    @param:JsonProperty("type")
    val type: ProductType,
    @param:JsonProperty("inventory")
    @get:Min(1)
    @get:Max(9999)
    val inventory: Int,
    @param:JsonProperty("cost")
    @param:JsonSetter(nulls = Nulls.FAIL)
    @get:JsonInclude(JsonInclude.Include.NON_NULL)
    val cost: Double? = null,
) {
    var id: Int? = null
}

class Products {
    private val products = mutableMapOf<Int, Product>()
    private var nextId = 1

    fun create(product: Product? = null): Int {
        val id = nextId++
        if (product != null) {
            products[id] = product.copy().also { it.id = id }
        }
        return id
    }

    fun list(type: ProductType?): List<Product> =
        products.values.filter { type == null || it.type == type }
}
