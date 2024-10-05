package com.example.firstcomposeproject.data.network

import com.example.firstcomposeproject.data.models.ChangeLikesResponseDto
import com.example.firstcomposeproject.data.models.NewsFeedResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


class ApiService(private val client: HttpClient) {

    private companion object {
        const val BASE_URL = "https://api.vk.ru/method/"
        const val API_VER = "5.199"
        const val ACCESS_TOKEN_KEY = "access_token"
    }

    suspend fun getNewsFeed(token: String): NewsFeedResponseDto =
        client.get("$BASE_URL/newsfeed.getRecommended") {
            url {
                parameters.append("v", API_VER)
                parameters.append(ACCESS_TOKEN_KEY, token)
                parameters.append("filter", "post")
            }
        }.body()

    suspend fun setLike(
        token: String,
        itemId: Long,
        ownerId: Long,
    ): ChangeLikesResponseDto = client.get("$BASE_URL/likes.add") {
        url {
            parameters.append("v", API_VER)
            parameters.append(ACCESS_TOKEN_KEY, token)
            parameters.append("type", "post")
            parameters.append("owner_id", ownerId.toString())
            parameters.append("item_id", itemId.toString())
        }
    }.body()

    suspend fun deleteLike(
        token: String,
        itemId: Long,
        ownerId: Long,
    ): ChangeLikesResponseDto = client.get("$BASE_URL/likes.delete") {
        url {
            parameters.append("v", API_VER)
            parameters.append(ACCESS_TOKEN_KEY, token)
            parameters.append("type", "post")
            parameters.append("owner_id", ownerId.toString())
            parameters.append("item_id", itemId.toString())
        }
    }.body()

}