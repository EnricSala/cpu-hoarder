package com.cpuhoarder.controller

import com.cpuhoarder.service.wiki.WikiClient
import com.cpuhoarder.service.wiki.XeonWikiResult
import com.cpuhoarder.utils.loggerOf
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wiki")
class WikiController(private val wikiClient: WikiClient) {

    @GetMapping("/xeon")
    fun findAll(): List<XeonWikiResult> {
        log.info("GET /wiki/xeon")
        return wikiClient.findXeons()
    }

    companion object {
        private val log = loggerOf(WikiController::class)
    }

}
