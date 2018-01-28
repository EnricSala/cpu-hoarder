package com.cpuhoarder.service.logging

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.filter.Filter
import ch.qos.logback.core.spi.FilterReply
import java.text.SimpleDateFormat
import java.util.*

class RxLoggingFilter : Filter<ILoggingEvent>() {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

    private fun Long.time(): String = dateFormat.format(Date(this))

    override fun decide(event: ILoggingEvent): FilterReply {
        LoggingBus.publish(StringBuilder()
                .append(event.timeStamp.time())
                .append(" ${event.level}")
                .append(" [${event.threadName}]")
                .append(" - ${event.loggerName}")
                .append(" : ${event.formattedMessage}")
                .toString())
        return FilterReply.NEUTRAL
    }

}
