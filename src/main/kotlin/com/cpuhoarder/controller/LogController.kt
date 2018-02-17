package com.cpuhoarder.controller

import com.cpuhoarder.service.logging.LogEvent
import com.cpuhoarder.service.logging.LoggingBus
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/log")
class LogController {

    @GetMapping
    fun display(): Resource = ClassPathResource("static/log.html")

    @GetMapping("/stream")
    fun stream(): Flux<LogEvent> = LoggingBus.observe()

}
