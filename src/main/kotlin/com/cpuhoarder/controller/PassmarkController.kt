package com.cpuhoarder.controller

import com.cpuhoarder.entity.CpuScore
import com.cpuhoarder.service.passmark.PassmarkService
import com.cpuhoarder.utils.clear
import com.cpuhoarder.utils.duration
import com.cpuhoarder.utils.loggerOf
import com.cpuhoarder.utils.swap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.Disposables
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.*

@RestController
@RequestMapping("/passmark")
class PassmarkController(private val passmark: PassmarkService) {

    private val random = Random()
    private val disposable = Disposables.swap()

    @GetMapping("/start")
    fun start(@RequestParam from: Int, @RequestParam to: Int, @RequestParam delay: Int) {
        log.info("GET /passmark/start from={} to={} delay={}", from, to, delay)
        Flux.fromIterable(from..to)
                .concatMap { updateScoreOf(it, delay) }
                .doOnComplete { log.info("Passmark scan completed") }
                .doOnCancel { log.warn("Passmark scan cancelled") }
                .doOnError { log.error("Passmark scan failed", it) }
                .subscribeOn(Schedulers.newSingle("passmark"))
                .subscribe()
                .swap(disposable)
    }

    @GetMapping("/stop")
    fun stop() {
        log.info("GET /passmark/stop")
        disposable.clear()
    }

    private fun updateScoreOf(passmarkId: Int, delay: Int): Mono<CpuScore> =
            Mono.fromCallable { passmark.updateScoreOf(passmarkId) }
                    .delaySubscription(random.duration(delay))
                    .doOnSuccess { log.info("Updated {}", it) }
                    .doOnError { log.warn("Failed to scan passmarkId={}", passmarkId) }
                    .onErrorResume { Mono.empty() }

    companion object {
        private val log = loggerOf(PassmarkController::class)
    }

}
