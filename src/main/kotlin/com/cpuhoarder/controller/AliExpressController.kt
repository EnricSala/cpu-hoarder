package com.cpuhoarder.controller

import com.cpuhoarder.entity.Cpu
import com.cpuhoarder.entity.CpuPrice
import com.cpuhoarder.repository.CpuRepository
import com.cpuhoarder.service.aliexpress.AliExpressService
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
@RequestMapping("/aliexpress")
class AliExpressController(private val aliExpress: AliExpressService,
                           private val cpuRepository: CpuRepository) {

    private val random = Random()
    private val disposable = Disposables.swap()

    @GetMapping("/start")
    fun start(@RequestParam delay: Int) {
        log.info("GET /aliexpress/start delay={}", delay)
        Flux.fromIterable(cpuRepository.findByInterestedIsTrue())
                .concatMap { updateListingsOf(it, delay) }
                .doOnComplete { log.info("AliExpress scan completed") }
                .doOnCancel { log.warn("AliExpress scan cancelled") }
                .doOnError { log.error("AliExpress scan failed", it) }
                .subscribeOn(Schedulers.newSingle("aliexpress"))
                .subscribe()
                .swap(disposable)
    }

    @GetMapping("/stop")
    fun stop() {
        log.info("GET /aliexpress/stop")
        disposable.clear()
    }

    private fun updateListingsOf(cpu: Cpu, delay: Int): Mono<List<CpuPrice>> =
            Mono.fromCallable { aliExpress.updateListingsOf(cpu.id) }
                    .delaySubscription(random.duration(delay))
                    .doOnSuccess { log.info("Found {} listings for {}", it.size, cpu.name) }
                    .doOnError { log.warn("Failed to scan {}", cpu) }
                    .onErrorResume { Mono.empty() }

    companion object {
        private val log = loggerOf(AliExpressController::class)
    }

}
