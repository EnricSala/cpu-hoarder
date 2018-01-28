package com.cpuhoarder.service.aliexpress

import com.cpuhoarder.entity.CpuPrice
import com.cpuhoarder.repository.CpuPriceRepository
import com.cpuhoarder.repository.CpuRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AliExpressService(private val aliExpress: AliExpressClient,
                        private val cpuRepository: CpuRepository,
                        private val priceRepository: CpuPriceRepository) {

    @Transactional
    fun updateListingsOf(cpuId: Long): List<CpuPrice> {
        val cpu = cpuRepository.findById(cpuId)
                .orElseThrow { IllegalArgumentException("cpu not found") }
        val time = Date()
        val listings = aliExpress.query(cpu.name).map {
            CpuPrice(cpu = cpu, time = time, item = it.item,
                    amount = it.price, url = it.url)
        }
        return priceRepository.saveAll(listings).toList()
    }

}
