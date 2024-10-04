package com.example.firstcomposeproject.samples.lesson2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstcomposeproject.R
import com.example.firstcomposeproject.ui.theme.FirstComposeProjectTheme

@Composable
fun InstagramProfile(
    profile: InstagramModel,
    onChangeFollowingStatusClick:(InstagramModel) -> Unit
) {

    Card(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        border = BorderStroke(
            width = 1.dp, color = MaterialTheme.colorScheme.onBackground
        ),
    ) {
        Column(
            modifier = Modifier.padding(all = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Avatar()
                UserStatistics(title = "Posts", value = "6,950")
                UserStatistics(title = "Followers", value = "436M")
                UserStatistics(title = "Following", value = "76")
            }
            Text(
                text = "Instagram${profile.id}",
                fontFamily = FontFamily.Cursive,
                fontSize = 24.sp,
            )
            Text(
                text = "#${profile.title}",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            FollowButton(
                isFollowed = profile.isFollowed,
                onClick = { onChangeFollowingStatusClick(profile) }
            )
        }
    }
}

@Composable
private fun FollowButton(
    onClick: () -> Unit,
    isFollowed: Boolean,
) {
    ElevatedButton(
        onClick = { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isFollowed) {
                Color.Blue.copy(alpha = .4f)
            } else {
                Color.Blue
            },
            contentColor = Color.White
        ),
    ) {
        Text(text = if (isFollowed) "Unfollow" else "Follow")
    }
}

@Composable
private fun Avatar() {
    Image(
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(Color.White)
            .padding(8.dp),
        painter = painterResource(id = R.drawable.instagram_logo),
        contentDescription = ""
    )
}

@Composable
private fun UserStatistics(
    title: String, value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = value, fontSize = 24.sp, fontFamily = FontFamily.Cursive
        )
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InstagramProfilePreviewLight() {
    val profile = InstagramModel (id = 1, title = "Title", isFollowed = true)
    FirstComposeProjectTheme(darkTheme = false) {
        InstagramProfile(profile, onChangeFollowingStatusClick = {})
    }

}

@Preview(showBackground = true)
@Composable
fun InstagramProfilePreviewDark() {
    val profile = InstagramModel (id = 1, title = "Title", isFollowed = true)
    FirstComposeProjectTheme(darkTheme = true) {
        InstagramProfile(profile, onChangeFollowingStatusClick = {})
    }
}