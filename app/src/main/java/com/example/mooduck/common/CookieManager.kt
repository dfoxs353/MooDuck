package com.example.mooduck.common

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class CookieManager : CookieJar {
    private var refreshToken: String? = null

    fun setRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        refreshToken?.let {
            return listOf(Cookie.Builder()
                .domain(url.host)
                .path("/")
                .name("refresh_token")
                .value(it)
                .build())
        }
        return emptyList()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        // Handle any cookies received in the response if needed
    }
}