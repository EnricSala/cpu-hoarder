package com.cpuhoarder.service.logging

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.filter.Filter
import ch.qos.logback.core.spi.FilterReply

class RxLoggingFilter : Filter<ILoggingEvent>() {

    override fun decide(event: ILoggingEvent): FilterReply {
        LoggingBus.publish(LogEvent(
                time = event.timeStamp,
                level = event.level.toString(),
                thread = event.threadName,
                logger = event.loggerName,
                message = event.formattedMessage))
        return FilterReply.NEUTRAL
    }

}
