package com.cpuhoarder.repository

import com.cpuhoarder.entity.CpuScore
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.*

interface CpuScoreRepository : PagingAndSortingRepository<CpuScore, Long> {

    fun findByCpuPassmarkId(passmarkId: Int): Optional<CpuScore>

}
