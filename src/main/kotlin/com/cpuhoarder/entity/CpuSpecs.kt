package com.cpuhoarder.entity

import java.util.*
import javax.persistence.*

@Entity
data class CpuSpecs(

        @GeneratedValue(strategy = GenerationType.AUTO)
        @Id var id: Long = -1,

        @OneToOne var cpu: Cpu,

        @Column var time: Date,

        @Column var url: String)
