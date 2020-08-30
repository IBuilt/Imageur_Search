package com.example.lib.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class DefaultInterceptor : Interceptor {

    @Throws(Exception::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()

        val requestBuilder = originalRequest.newBuilder()
            .header("content-type", "application/json")
            .header("Authorization", "Client-ID 137cda6b5008a7c")
        val request = requestBuilder.build()

        return chain.proceed(request)
    }
}
