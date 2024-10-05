package com.example.firstcomposeproject.domain

import android.os.Bundle
import androidx.navigation.NavType
import com.example.firstcomposeproject.navigation.Screen
import com.example.firstcomposeproject.navigation.decode
import com.example.firstcomposeproject.navigation.encode
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class FeedPost(
    val id: Long,
    val communityId: Long,
    val communityName: String,
    val publicationDate: String,
    val communityImageUrl: String,
    val contentText: String,
    val contentImageUrl: String?,
    val statistics: List<StatisticItem>,
    val isLiked: Boolean
) {
    companion object {
        val NavigationType: NavType<FeedPost> = object : NavType<FeedPost>(false) {
            override fun get(bundle: Bundle, key: String): FeedPost? {
                val feedPostString = bundle.getString(Screen.KEY_FEED_POST)
                    ?: return null

                val feedPost = Json.decodeFromString<FeedPost>(feedPostString)

                return feedPost
            }

            override fun parseValue(value: String): FeedPost {
                return Json.decodeFromString<FeedPost>(value.decode())
            }

            override fun serializeAsValue(value: FeedPost): String {
                return Json.encodeToString(value).encode()
            }

            override fun put(bundle: Bundle, key: String, value: FeedPost) {
                bundle.putString(key, Json.encodeToString(value))
            }

        }
    }
}
