package com.cpuhoarder.service.arkintel

import com.cpuhoarder.entity.CpuSpecs
import com.cpuhoarder.repository.CpuRepository
import com.cpuhoarder.repository.CpuSpecsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ArkIntelService(private val arkIntel: ArkIntelClient,
                      private val cpuRepository: CpuRepository,
                      private val specsRepository: CpuSpecsRepository) {

    @Transactional
    fun updateSpecsOf(cpuId: Long): CpuSpecs {
        val cpu = cpuRepository.findById(cpuId)
                .orElseThrow { IllegalArgumentException("cpu not found") }
        val info = arkIntel.query(cpu.name)
        return specsRepository.save(
                CpuSpecs(cpu = cpu, time = Date(), url = info.url))
    }

}
