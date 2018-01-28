package com.cpuhoarder.utils

import reactor.core.Disposable

fun Disposable.swap(swap: Disposable.Swap) = swap.update(this)

fun Disposable.Swap.clear() = this.update(null)
