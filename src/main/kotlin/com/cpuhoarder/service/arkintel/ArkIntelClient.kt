package com.cpuhoarder.service.arkintel

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Service

@Service
class ArkIntelClient(private val api: ArkIntelApi) {

    fun query(cpuName: String): ArkIntelSpecs {
        val results = api.search(cpuName).execute().body()
        val arkIntelId = results!!.first().id
        val body = api.getSpecs(arkIntelId).execute().body()
        val doc = Jsoup.parse(body!!.string())
        return doc.arkIntelSpecs()
    }

    private fun Document.arkIntelSpecs(): ArkIntelSpecs {
        TODO("parse cpu specs")
    }

    companion object {
        private val INTEL_KEYWORDS = listOf("Core", "Duo", "Quad",
                "Xeon", "Celeron", "Pentium", "Atom", "Itanium", "Intel")
    }

}
