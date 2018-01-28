package com.cpuhoarder.repository

import com.cpuhoarder.entity.Cpu
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.*

interface CpuRepository : PagingAndSortingRepository<Cpu, Long> {

    fun findByPassmarkId(passmarkId: Int): Optional<Cpu>

    fun findByInterestedIsTrue(): List<Cpu>

}
