package com.cpuhoarder.service.logging

data class LogEvent(val time: Long,
                    val level: String,
                    val thread: String,
                    val logger: String,
                    val message: String)
