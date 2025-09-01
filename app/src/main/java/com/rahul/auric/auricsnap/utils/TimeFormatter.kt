// In: utils/TimeFormatter.kt

package com.rahul.auric.auricsnap.utils

import java.util.concurrent.TimeUnit

fun formatTime(timeInMillis: Long): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis) % 60
    val hundredths = (timeInMillis % 1000) / 10

    return "%02d:%02d.%02d".format(minutes, seconds, hundredths)
}