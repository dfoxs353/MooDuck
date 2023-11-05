package com.example.mooduck.common

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private var token: String?) : Interceptor {
    fun setToken(token: String?) {
        this.token = token
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (token != null) {
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        }
        return chain.proceed(request)
    }
}