package com.cpuhoarder.service

import com.cpuhoarder.service.passmark.PassmarkClient
import org.junit.Assert.assertEquals
import org.junit.Test

class PassmarkClientTests {

    @Test
    fun query_finds_score() {
        val passmark = PassmarkClient()
        val score = passmark.query(CPU_PASSMARK_ID)
        assertEquals(CPU_NAME, score.name)
    }

    companion object {
        private const val CPU_NAME = "Intel Core i7-3770"
        private const val CPU_PASSMARK_ID = 896
    }

}
