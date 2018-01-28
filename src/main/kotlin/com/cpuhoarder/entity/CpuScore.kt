package com.cpuhoarder.entity

import java.util.*
import javax.persistence.*

@Entity
data class CpuScore(

        @GeneratedValue(strategy = GenerationType.AUTO)
        @Id var id: Long = -1,

        @OneToOne var cpu: Cpu,

        @Column var time: Date,

        @Column var average: Int,

        @Column var singleThread: Int,

        @Column var samples: Int)
