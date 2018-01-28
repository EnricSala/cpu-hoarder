package com.cpuhoarder.controller

import com.cpuhoarder.entity.CpuPrice
import com.cpuhoarder.repository.CpuPriceRepository
import com.cpuhoarder.utils.loggerOf
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/price")
class PriceController(private val priceRepository: CpuPriceRepository) {

    @GetMapping
    fun findAll(): Iterable<CpuPrice> {
        log.info("GET /price")
        return priceRepository.findAll()
    }

    companion object {
        private val log = loggerOf(PriceController::class)
    }

}
