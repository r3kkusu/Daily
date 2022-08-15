package com.daily.app.common.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.abs

class DateUtils {

    companion object {

        val DATE_FORMAT_1 : String = "LLL dd, yyyy"
        val DATE_FORMAT_2 : String = "dd LLL yyyy 'at' h:mm a"
        val DATE_FORMAT_3 : String = "yyyy-MM-dd'T'HH:mm:ssZ"

        @SuppressLint("SimpleDateFormat", "NewApi")
        fun format(epoch: Long, format: String): String {
            val sdf = SimpleDateFormat(format)
            val netDate = Date(epoch * 1000)
            return sdf.format(netDate)
        }

        fun format(dateRaw: String, format: String): Date {
            return SimpleDateFormat(format).parse(dateRaw)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun now(): Date {
            val localDatetime = LocalDateTime.ofInstant(Date().toInstant(), ZoneId.systemDefault())
            return Date.from(localDatetime.atZone(ZoneId.systemDefault()).toInstant())
        }

        fun dateDiff(date1: Date, date2: Date) : HashMap<String, Long> {

            val map: HashMap<String, Long> = hashMapOf()

            val seconds: Long = (date1.time - date2.time) / 1000
            map.put("seconds", abs(seconds))

            val minutes = seconds / 60
            map.put("minutes", abs(minutes))

            val hours = minutes / 60
            map.put("hours", abs(hours))

            val days = hours / 24
            map.put("days", abs(days))

            val weeks = days / 7
            map.put("weeks", abs(weeks))

            val months = weeks / 4
            map.put("months", abs(months))

            val years = months / 12
            map.put("years", abs(years))

            return map
        }

        fun dateDiffDescription(map: HashMap<String, Long>) : String {
            return if (map["years"]!! > 0) {
                dateDetailHelper(map["years"]!!,"year")
            } else if (map["months"]!! > 0) {
                dateDetailHelper(map["months"]!!,"month")
            } else if  (map["weeks"]!! > 0) {
                dateDetailHelper(map["weeks"]!!,"week")
            } else if (map["days"]!! > 0) {
                dateDetailHelper(map["days"]!!,"day")
            } else if (map["hours"]!! > 0) {
                dateDetailHelper(map["hours"]!!,"hour")
            } else if (map["minutes"]!! > 0) {
                dateDetailHelper(map["minutes"]!!,"minute")
            } else {
                dateDetailHelper(map["seconds"]!!,"second")
            }
        }

        private fun dateDetailHelper(date: Long, detail: String) : String {
            return "${if (date > 1) "$date ${detail}s" else "1 $detail"} ago"
        }
    }
}