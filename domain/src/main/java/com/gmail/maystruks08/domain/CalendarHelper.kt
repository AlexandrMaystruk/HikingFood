package com.gmail.maystruks08.domain

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CalendarHelper @Inject constructor() {

    companion object {

        const val SERVER_UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"

        const val DATE_FORMAT = "dd.MM.yyyy"

        const val TIME_FORMAT = "HH:mm:ss"

        const val PRINT_FORMAT = "dd.MM.yyyy HH:mm:ss"

    }

    fun getCurrentDate(): Date = resetTimeOnDate(Date())

    fun format(date: Date, format: String) =
            SimpleDateFormat(format, Locale.getDefault()).format(date)

    fun format(timestamp: Long, format: String) =
            SimpleDateFormat(format, Locale.getDefault()).format(Date(timestamp))

     fun formatDateToUTC(date: Date): String {
        val df = SimpleDateFormat(SERVER_UTC_FORMAT, Locale.getDefault())
        df.timeZone = TimeZone.getTimeZone("UTC")
        return df.format(date)
    }

    fun getDate(formatStr: String, dateStr: String): Date =
            SimpleDateFormat(formatStr, Locale.getDefault()).parse(dateStr)

    fun toDate(timestamp: Long) = Date(timestamp)

    fun resetTimeOnDate (date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }

}