package com.example.firstcomposeproject.data.network

import com.example.firstcomposeproject.data.models.NewsFeedResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter


class ApiService(private val client: HttpClient) {

    suspend fun getNewsFeed(token: String): NewsFeedResponseDto = client.get("https://api.vk.ru/method/newsfeed.getRecommended?v=5.199") {
        url {
            parameters.append("access_token", token)
//            parameters.append("count", "3")
        }
    }.body()

}