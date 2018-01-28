package com.cpuhoarder.service.passmark

import com.cpuhoarder.entity.Cpu
import com.cpuhoarder.entity.CpuScore
import com.cpuhoarder.repository.CpuRepository
import com.cpuhoarder.repository.CpuScoreRepository
import com.cpuhoarder.utils.loggerOf
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PassmarkService(private val passmark: PassmarkClient,
                      private val cpuRepository: CpuRepository,
                      private val scoreRepository: CpuScoreRepository) {

    @Transactional
    fun updateScoreOf(passmarkId: Int): CpuScore {
        val info = passmark.query(passmarkId)
        val cpu = cpuRepository.findByPassmarkId(passmarkId).orElseGet {
            log.info("Discovered: {}", info.name)
            cpuRepository.save(
                    Cpu(name = info.name, passmarkId = passmarkId))
        }
        val scoreId = scoreRepository.findByCpuPassmarkId(passmarkId)
                .map { it.id }.orElse(-1)
        return scoreRepository.save(
                CpuScore(id = scoreId, cpu = cpu,
                        time = Date(), average = info.average,
                        singleThread = info.singleThread, samples = info.samples))
    }

    companion object {
        private val log = loggerOf(PassmarkService::class)
    }

}
