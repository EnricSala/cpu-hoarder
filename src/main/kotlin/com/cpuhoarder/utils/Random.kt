package com.cpuhoarder.utils

import java.time.Duration
import java.util.*

fun Random.duration(seconds: Int, margin: Float = 0.5f): Duration {
    val millis = seconds * 1000f * (1f + margin * nextFloat())
    return Duration.ofMillis(millis.toLong())
}
