package com.cpuhoarder.service.aliexpress

import com.cpuhoarder.utils.loggerOf
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class AliExpressClient {

    private fun buildUrl(item: String): String {
        val query = item.replace(Regex(" +"), "+")
        return "$ALIEXPRESS_URL?SearchText=$query"
    }

    fun query(cpuName: String): List<AliExpressListing> {
        val doc = Jsoup.connect(buildUrl(cpuName)).get()
        return doc.aliExpressListings()
    }

    private fun Document.aliExpressListings(): List<AliExpressListing> {
        // Find the items
        val table = this.selectFirst("#hs-list-items")
        val items = table.select(".info")

        // Extract the data from each
        return items.map { it.parseSingleListing() }
    }

    private fun Element.parseSingleListing(): AliExpressListing {
        // Find the item and url
        val link = this.selectFirst("a.product")
        val item = link.attr("title")
        val url = "https:" + link.attr("href").substringBefore("?")

        // Find the price
        val rawPrice = this.selectFirst("span.price").child(0).text()
        val cleanPrice = Regex("([\\d\\.,]+)")
                .find(rawPrice)?.groupValues?.get(1)
                ?.replace(".", "")
                ?.replace(",", ".") ?: "-1.0"
        val price = BigDecimal(cleanPrice)

        // Build the result
        return AliExpressListing(item, price, url)
    }

    companion object {
        private val log = loggerOf(AliExpressClient::class)
        private const val ALIEXPRESS_URL = "https://es.aliexpress.com/wholesale"
    }

}
