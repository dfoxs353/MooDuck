package com.example.mooduck.common

import okhttp3.Interceptor
import okhttp3.Response

class CookieInterceptor(private var cookie: String?): Interceptor {
    fun setToken(cookie: String?) {
        this.cookie = cookie
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        var originalRequest = chain.request()

        if(cookie != null){
            originalRequest = originalRequest.newBuilder()
                .header("Cookie", cookie!!)
                .method(originalRequest.method, originalRequest.body)
                .build()
        }

        return chain.proceed(originalRequest)
    }
}