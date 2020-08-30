package com.example.lib.utils

import android.text.format.DateUtils
import java.util.*

object DateTimeUtil {

    fun nowTimeInMillis() =
        System.currentTimeMillis()


    fun getRelativeTimeSpanString(timeMillis: Long): String {


        return DateUtils.getRelativeTimeSpanString(timeMillis)
            .toString()


//        return DateUtils.getRelativeTimeSpanString(
//
//            nowTimeInMillis(),
//
//            timeMillis,
//
//            DateUtils.MINUTE_IN_MILLIS
//
//        ).toString()
    }


    fun timeMillisToDate(timeMillis: Long) =
        Date(timeMillis)

}