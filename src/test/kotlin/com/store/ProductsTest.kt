package com.store

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class ProductsTest {
    @Test
    fun `list returns only products of the requested type`() {
        val products = Products()
        val bookId = products.create(Product(name = "Notebook", type = ProductType.book, inventory = 3))
        products.create(Product(name = "Apple", type = ProductType.food, inventory = 4))

        val listed = products.list(ProductType.book)

        assertEquals(listOf(bookId), listed.map { it.id })
        assertEquals("Notebook", listed.single().name)
        assertEquals(ProductType.book, listed.single().type)
        assertEquals(3, listed.single().inventory)
    }

    @Test
    fun `list without a type returns every created product under distinct ids`() {
        val products = Products()
        val first = products.create(Product(name = "Notebook", type = ProductType.book, inventory = 3))
        val second = products.create(Product(name = "Apple", type = ProductType.food, inventory = 4))

        assertNotEquals(first, second)
        assertEquals(setOf(first, second), products.list(null).map { it.id }.toSet())
    }

    @Test
    fun `an omitted product receives an id and is not listed`() {
        val products = Products()
        val omitted = products.create(null)
        val created = products.create(Product(name = "Notebook", type = ProductType.book, inventory = 3))

        assertNotEquals(omitted, created)
        assertEquals(listOf(created), products.list(null).map { it.id })
    }

    @Test
    fun `list keeps a cost when one was given and leaves it unset when it was not`() {
        val products = Products()
        val priced = products.create(Product(name = "Phone", type = ProductType.gadget, inventory = 2, cost = 12.5))
        val unpriced = products.create(Product(name = "Notebook", type = ProductType.book, inventory = 3))

        val listed = products.list(null).associateBy { it.id }

        assertEquals(12.5, listed[priced]?.cost)
        assertEquals(null, listed[unpriced]?.cost)
    }
}
