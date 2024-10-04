package com.example.firstcomposeproject.domain

import android.os.Bundle
import androidx.navigation.NavType
import com.example.firstcomposeproject.R
import com.example.firstcomposeproject.navigation.Screen
import com.example.firstcomposeproject.navigation.decode
import com.example.firstcomposeproject.navigation.encode
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.random.Random

@Serializable
data class FeedPost(
    val id: Int = 0,
    val communityName: String = "/dev/null",
    val publicationDate: String = "${Random.nextInt(24)}:${Random.nextInt(60)}",
    val avatarResId: Int = R.drawable.post_comunity_thumbnail,
    val contentText: String = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
    val contentImageResId: Int = R.drawable.post_content_image,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(type = StatisticType.VIEWS, count = Random.nextInt(1000)),
        StatisticItem(type = StatisticType.SHARES, count = Random.nextInt(1000)),
        StatisticItem(type = StatisticType.COMMENTS, count = Random.nextInt(100)),
        StatisticItem(type = StatisticType.LIKES, count = Random.nextInt(1000))
    )
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
