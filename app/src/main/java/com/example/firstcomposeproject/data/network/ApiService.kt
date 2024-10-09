package com.example.firstcomposeproject.data.network

import com.example.firstcomposeproject.data.models.ChangeLikesResponseDto
import com.example.firstcomposeproject.data.models.CommentsResponseDto
import com.example.firstcomposeproject.data.models.IgnoreItemResponseDto
import com.example.firstcomposeproject.data.models.NewsFeedResponseDto
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.core.annotation.Single


@Single
class ApiService() {
    private companion object {
        const val BASE_URL = "https://api.vk.ru/method/"
        const val API_VER = "5.199"
        const val ACCESS_TOKEN_KEY = "access_token"
    }

    private val client = ApiFactory.client

    suspend fun getComments(
        token: String,
        itemId: Long,
        ownerId: Long,
    ): CommentsResponseDto =
        client.get("$BASE_URL/wall.getComments") {
            url {
                parameters.append("v", API_VER)
                parameters.append(ACCESS_TOKEN_KEY, token)
                parameters.append("type", "post")
                parameters.append("owner_id", ownerId.toString())
                parameters.append("post_id", itemId.toString())
                parameters.append("extended", "1")
                parameters.append("fields", "photo_100")
            }
        }.body()

    suspend fun getNewsFeed(token: String): NewsFeedResponseDto =
        client.get("$BASE_URL/newsfeed.getRecommended") {
            url {
                parameters.append("v", API_VER)
                parameters.append(ACCESS_TOKEN_KEY, token)
                parameters.append("filter", "post")
//                parameters.append("count", "1")
            }
        }.body()

    suspend fun getNewsFeed(token: String, startFrom: String): NewsFeedResponseDto =
        client.get("$BASE_URL/newsfeed.getRecommended") {
            url {
                parameters.append("v", API_VER)
                parameters.append(ACCESS_TOKEN_KEY, token)
                parameters.append("filter", "post")
                parameters.append("start_from", startFrom)
//                parameters.append("count", "1")
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

    suspend fun ignoreItem(
        token: String,
        itemId: Long,
        ownerId: Long,
    ): IgnoreItemResponseDto = client.get("$BASE_URL/newsfeed.ignoreItem") {
        url {
            parameters.append("v", API_VER)
            parameters.append(ACCESS_TOKEN_KEY, token)
            parameters.append("type", "wall")
            parameters.append("owner_id", ownerId.toString())
            parameters.append("item_id", itemId.toString())
        }
    }.body()

}