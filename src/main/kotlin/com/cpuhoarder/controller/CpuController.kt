package com.cpuhoarder.controller

import com.cpuhoarder.entity.Cpu
import com.cpuhoarder.repository.CpuRepository
import com.cpuhoarder.utils.loggerOf
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/cpu")
class CpuController(private val cpuRepository: CpuRepository) {

    @GetMapping
    fun findAll(): Iterable<Cpu> {
        log.info("GET /cpu")
        return cpuRepository.findAll()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): Optional<Cpu> {
        log.info("GET /cpu/{}", id)
        return cpuRepository.findById(id)
    }

    @GetMapping("/{id}", params = ["name"])
    fun updateName(@PathVariable id: Long,
                   @RequestParam name: String): Optional<Cpu> {
        log.info("GET /cpu/{} name={}", id, name)
        return cpuRepository.findById(id)
                .map { it.apply { this.name = name } }
                .map { cpuRepository.save(it) }
    }

    @GetMapping("/{id}", params = ["interested"])
    fun updateInterested(@PathVariable id: Long,
                         @RequestParam interested: Boolean): Optional<Cpu> {
        log.info("GET /cpu/{} interested={}", id, interested)
        return cpuRepository.findById(id)
                .map { it.apply { this.interested = interested } }
                .map { cpuRepository.save(it) }
    }

    companion object {
        private val log = loggerOf(CpuController::class)
    }

}
