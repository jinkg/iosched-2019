package com.kinglloy.iosched.model

import org.threeten.bp.ZonedDateTime

/**
 * Yalin on 2019-08-31
 */
data class ConferenceDay(
    val start: ZonedDateTime,
    val end: ZonedDateTime
) {
    operator fun contains(session: Session) = start <= session.startTime && end >= session.endTime
}