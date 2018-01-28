package com.cpuhoarder.controller

import com.cpuhoarder.entity.CpuScore
import com.cpuhoarder.repository.CpuScoreRepository
import com.cpuhoarder.utils.loggerOf
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/score")
class ScoreController(private val scoreRepository: CpuScoreRepository) {

    @GetMapping
    fun findAll(): Iterable<CpuScore> {
        log.info("GET /score")
        return scoreRepository.findAll()
    }

    companion object {
        private val log = loggerOf(ScoreController::class)
    }

}
