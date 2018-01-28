package com.cpuhoarder.entity

import javax.persistence.*

@Entity
data class Cpu(

        @GeneratedValue(strategy = GenerationType.AUTO)
        @Id var id: Long = -1,

        @Column var name: String,

        @Column(unique = true) var passmarkId: Int,

        @Column var interested: Boolean = false)
