package com.cpuhoarder.service.passmark

data class PassmarkScore(val name: String,
                         val average: Int,
                         val singleThread: Int,
                         val samples: Int)
