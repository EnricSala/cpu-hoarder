package com.cpuhoarder.entity

import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
data class CpuPrice(

        @GeneratedValue(strategy = GenerationType.AUTO)
        @Id var id: Long = -1,

        @ManyToOne var cpu: Cpu,

        @Column var time: Date,

        @Column var item: String,

        @Column var amount: BigDecimal,

        @Column var url: String)
