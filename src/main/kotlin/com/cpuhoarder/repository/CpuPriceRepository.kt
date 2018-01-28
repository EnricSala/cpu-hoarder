package com.cpuhoarder.repository

import com.cpuhoarder.entity.CpuPrice
import org.springframework.data.repository.PagingAndSortingRepository

interface CpuPriceRepository : PagingAndSortingRepository<CpuPrice, Long>
