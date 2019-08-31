package com.kinglloy.iosched.model

import org.threeten.bp.ZonedDateTime


typealias SessionId = String

/**
 * Describes a conference session. Sessions have specific start and end times, and they represent a
 * variety of conference events: talks, sandbox demos, office hours, etc. A session is usually
 * associated with one or more [Tag]s.
 */
data class Session(
    /**
     * Unique string identifying this session.
     */
    val id: SessionId,
    /**
     * Start time of the session
     */
    val startTime: ZonedDateTime,

    /**
     * End time of the session
     */
    val endTime: ZonedDateTime
)