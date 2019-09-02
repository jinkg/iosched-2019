package com.kinglloy.iosched.shared.util

import com.kinglloy.iosched.model.ConferenceDay
import com.kinglloy.iosched.shared.BuildConfig
import org.threeten.bp.ZonedDateTime

/**
 * Yalin on 2019-08-31
 */
object TimeUtils {
    val ConferenceDays = listOf(
        ConferenceDay(
            ZonedDateTime.parse(BuildConfig.CONFERENCE_DAY1_START),
            ZonedDateTime.parse(BuildConfig.CONFERENCE_DAY1_END)
        ),
        ConferenceDay(
            ZonedDateTime.parse(BuildConfig.CONFERENCE_DAY2_START),
            ZonedDateTime.parse(BuildConfig.CONFERENCE_DAY2_END)
        ),
        ConferenceDay(
            ZonedDateTime.parse(BuildConfig.CONFERENCE_DAY3_START),
            ZonedDateTime.parse(BuildConfig.CONFERENCE_DAY3_END)
        )
    )

    fun conferenceHasStarted(): Boolean {
//        return ZonedDateTime.now().isAfter(ConferenceDays.first().start)
        return false
    }

    fun conferenceHasEnded(): Boolean {
        return ZonedDateTime.now().isAfter(ConferenceDays.last().end)
    }
}