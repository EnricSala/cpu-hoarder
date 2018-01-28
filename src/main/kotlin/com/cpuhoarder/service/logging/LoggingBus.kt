package com.cpuhoarder.service.logging

import reactor.core.publisher.Flux
import reactor.core.publisher.ReplayProcessor
import reactor.core.publisher.toFlux

object LoggingBus {

    private const val historySize = 10
    private val emitter = ReplayProcessor.create<String>(historySize).serialize()

    fun publish(message: String) = emitter.onNext(message)

    fun observe(): Flux<String> = emitter.toFlux()

}
