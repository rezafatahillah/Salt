package com.salt.reza.tools

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    fun convertDateFormat(inputDate: String): String {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
            val date = inputFormat.parse(inputDate)
            return outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}