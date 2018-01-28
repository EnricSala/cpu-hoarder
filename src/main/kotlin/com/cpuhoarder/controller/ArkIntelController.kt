package com.cpuhoarder.controller

import com.cpuhoarder.entity.Cpu
import com.cpuhoarder.entity.CpuSpecs
import com.cpuhoarder.repository.CpuRepository
import com.cpuhoarder.service.arkintel.ArkIntelService
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
@RequestMapping("/arkintel")
class ArkIntelController(private val arkIntel: ArkIntelService,
                         private val cpuRepository: CpuRepository) {

    private val random = Random()
    private val disposable = Disposables.swap()

    @GetMapping("/start")
    fun start(@RequestParam delay: Int) {
        log.info("GET /arkintel/start delay={}", delay)
        Flux.fromIterable(cpuRepository.findByInterestedIsTrue())
                .concatMap { updateSpecsOf(it, delay) }
                .doOnComplete { log.info("ArkIntel scan completed") }
                .doOnCancel { log.warn("ArkIntel scan cancelled") }
                .doOnError { log.error("ArkIntel scan failed", it) }
                .subscribeOn(Schedulers.newSingle("arkintel"))
                .subscribe()
                .swap(disposable)
    }

    @GetMapping("/stop")
    fun stop() {
        log.info("GET /arkintel/stop")
        disposable.clear()
    }

    private fun updateSpecsOf(cpu: Cpu, delay: Int): Mono<CpuSpecs> =
            Mono.fromCallable { arkIntel.updateSpecsOf(cpu.id) }
                    .delaySubscription(random.duration(delay))
                    .doOnSuccess { log.info("Updated {}", it) }
                    .doOnError { log.warn("Failed to scan {}", cpu) }
                    .onErrorResume { Mono.empty() }

    companion object {
        private val log = loggerOf(ArkIntelController::class)
    }

}
