package com.cpuhoarder.repository

import com.cpuhoarder.entity.CpuSpecs
import org.springframework.data.repository.PagingAndSortingRepository

interface CpuSpecsRepository : PagingAndSortingRepository<CpuSpecs, Long>
