package com.cpuhoarder.service

import com.cpuhoarder.service.arkintel.ArkIntelClient
import org.junit.Before
import org.junit.Test

class ArkIntelClientTests {

    private lateinit var arkintel: ArkIntelClient

    @Before
    fun setup() {
        val api = HttpConfig().arkIntelApi()
        arkintel = ArkIntelClient(api)
    }

    @Test
    fun query_finds_specs() {
        val specs = arkintel.query(CPU_NAME)
        println(specs)
    }

    companion object {
        private const val CPU_NAME = "Intel Core i7-3770"
    }

}
