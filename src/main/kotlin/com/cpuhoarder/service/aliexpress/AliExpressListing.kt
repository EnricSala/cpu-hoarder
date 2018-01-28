package com.cpuhoarder.service.aliexpress

import java.math.BigDecimal

data class AliExpressListing(val item: String,
                             val price: BigDecimal,
                             val url: String)
