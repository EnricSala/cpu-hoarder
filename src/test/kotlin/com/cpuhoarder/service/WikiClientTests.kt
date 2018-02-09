package com.cpuhoarder.service

import com.cpuhoarder.service.wiki.WikiClient
import org.junit.Assert.assertTrue
import org.junit.Test

class WikiClientTests {

    @Test
    fun finds_xeons() {
        val client = WikiClient()
        val xeons = client.findXeons()
        assertTrue(xeons.isNotEmpty())
        xeons.forEach { assertTrue(it.name.isNotBlank()) }
    }

}
