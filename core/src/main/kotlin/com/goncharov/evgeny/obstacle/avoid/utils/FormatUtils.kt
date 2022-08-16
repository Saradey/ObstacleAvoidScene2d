package com.goncharov.evgeny.obstacle.avoid.utils

import java.text.SimpleDateFormat
import java.util.*

object FormatUtils {

    val calendar: Calendar = Calendar.getInstance()

    @Suppress("SimpleDateFormat")
    val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm:ss")
}