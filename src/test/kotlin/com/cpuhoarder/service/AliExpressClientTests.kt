package com.cpuhoarder.service

import com.cpuhoarder.service.aliexpress.AliExpressClient
import org.junit.Assert.assertFalse
import org.junit.Test

class AliExpressClientTests {

    @Test
    fun query_finds_items() {
        val aliexpress = AliExpressClient()
        val prices = aliexpress.query(CPU_NAME)
        assertFalse(prices.isEmpty())
        prices.forEach { println(it) }
    }

    companion object {
        private const val CPU_NAME = "Intel Core i7-3770"
    }

}
