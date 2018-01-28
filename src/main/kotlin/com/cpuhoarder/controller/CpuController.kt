package com.cpuhoarder.controller

import com.cpuhoarder.entity.Cpu
import com.cpuhoarder.repository.CpuRepository
import com.cpuhoarder.utils.loggerOf
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cpu")
class CpuController(private val cpuRepository: CpuRepository) {

    @GetMapping
    fun findAll(): Iterable<Cpu> {
        log.info("GET /cpu")
        return cpuRepository.findAll()
    }

    companion object {
        private val log = loggerOf(CpuController::class)
    }

}
