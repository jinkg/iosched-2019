package com.kinglloy.iosched.ui.schedule

import com.kinglloy.iosched.model.ConferenceDay


/** An indicator for days on the Schedule. */
class DayIndicator(
    val day: ConferenceDay,
    val checked: Boolean = false,
    val enabled: Boolean = true
) {
    override fun equals(other: Any?) =
        this === other || (other is DayIndicator && day == other.day)

    override fun hashCode() = day.hashCode()

    fun areUiContentsTheSame(other: DayIndicator): Boolean {
        return checked == other.checked && enabled == other.enabled
    }
}