package com.cpuhoarder.service.wiki

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.springframework.stereotype.Service

@Service
class WikiClient {

    fun findXeons(): List<XeonWikiResult> {
        val doc = Jsoup.connect(XEON_WIKI_URL).get()

        // Extract the items in each table
        val tables = doc.select(".wikitable")
        return tables.flatMap {
            it.select("tr")
                    .drop(1)
                    .filter { it.select("td").size > 1 }
                    .map { rowToXeoResult(it) }
        }
    }

    private fun rowToXeoResult(row: Element): XeonWikiResult {
        val cell = row.selectFirst("td")
        val link = cell.children().first()
        return if (link == null || !link.`is`("a")) {
            val name = cell.ownText()
            val url = null
            XeonWikiResult(name, url)
        } else {
            val name = link.text()
            val url = link.attr("href")
            XeonWikiResult(name, url)
        }
    }

    companion object {
        private const val XEON_WIKI_URL = "https://en.wikipedia.org/wiki/List_of_Intel_Xeon_microprocessors"
    }

}
