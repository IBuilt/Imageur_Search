package com.example.lib.remote

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.net.UnknownHostException

@Throws(IOException::class)
fun isReachable(targetUrl: String?): Boolean {
    val httpUrlConnection: HttpURLConnection = URL(
        targetUrl
    ).openConnection() as HttpURLConnection
    httpUrlConnection.requestMethod = "HEAD"
    return try {
        val responseCode: Int = httpUrlConnection.responseCode
        responseCode == HttpURLConnection.HTTP_OK
    } catch (noInternetConnection: UnknownHostException) {
        false
    }
}