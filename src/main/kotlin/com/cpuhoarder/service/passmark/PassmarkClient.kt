package com.cpuhoarder.service.passmark

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Service

@Service
class PassmarkClient {

    fun query(passmarkId: Int): PassmarkScore {
        val queryUrl = "$PASSMARK_URL?id=$passmarkId"
        val doc = Jsoup.connect(queryUrl).get()
        return doc.passmarkScore()
    }

    private fun Document.passmarkScore(): PassmarkScore {
        // Parse the CPU name
        val name = this.selectFirst(".cpuname")
                .text().substringBefore("@").trim()

        // Get the specs and marks cells
        val table = this.selectFirst("table.desc")
        val descriptionRow = table.select("tr")[1]
        val descriptionColumns = descriptionRow.select("td")
        val specsCell = descriptionColumns[0]
        val marksCell = descriptionColumns[1]

        // Parse the marks
        val average = marksCell.selectFirst("span").text().toInt()
        val scoreHtml = marksCell.html()
        val singleThread = Regex("Single Thread Rating: (\\d+)")
                .find(scoreHtml)?.groupValues?.get(1)?.toInt() ?: -1
        val samples = Regex("Samples: (\\d+)")
                .find(scoreHtml)?.groupValues?.get(1)?.toInt() ?: -1

        // Build result
        return PassmarkScore(name, average, singleThread, samples)
    }

    companion object {
        private const val PASSMARK_URL = "https://www.cpubenchmark.net/cpu.php"
    }

}
