package com.example.lib.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

internal class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val t1 = System.nanoTime()
        val response = chain.proceed(request)
        val t2 = System.nanoTime()
        return response
    }
}