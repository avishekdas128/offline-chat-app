package com.orangeink.offlinechatapp.chat.presentation.messages.ui.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.orangeink.offlinechatapp.R
import com.orangeink.offlinechatapp.chat.presentation.messages.MessagesItem
import com.orangeink.offlinechatapp.chat.presentation.messages.MessagesType
import com.orangeink.offlinechatapp.chat.presentation.messages.ui.media.ImageViewer
import com.orangeink.offlinechatapp.chat.presentation.messages.ui.media.VideoViewer
import com.orangeink.offlinechatapp.core.design.theme.GreyLight
import com.orangeink.offlinechatapp.core.design.theme.Primary
import com.orangeink.offlinechatapp.core.util.formatCreatedAt
import com.orangeink.offlinechatapp.core.util.getMediaType

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MessagesItem(messagesItem: MessagesItem) {
    val showMediaViewer = remember { mutableStateOf(false) }
    var paddingModifier = PaddingValues(end = 48.dp)
    if (messagesItem.type == MessagesType.Sent) paddingModifier = PaddingValues(start = 48.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingModifier),
        horizontalAlignment = if (messagesItem.type == MessagesType.Sent)
            Alignment.End
        else
            Alignment.Start
    ) {
        Text(text = messagesItem.createdAt.formatCreatedAt(), fontSize = 8.sp)
        Spacer(modifier = Modifier.height(4.dp))
        messagesItem.body?.let {
            Text(
                text = messagesItem.body,
                fontSize = 10.sp,
                lineHeight = 1.7.em,
                color = if (messagesItem.type == MessagesType.Sent) Color.White else Color.Black,
                modifier = Modifier
                    .background(
                        color = if (messagesItem.type == MessagesType.Sent) Primary else GreyLight,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
        messagesItem.mediaPath?.let {
            val uri = Uri.parse(it)
            val mediaType = LocalContext.current.getMediaType(uri)
            Box(
                modifier = Modifier
                    .width(170.dp)
                    .height(135.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { showMediaViewer.value = true }
            ) {
                GlideImage(
                    model = Uri.parse(it),
                    contentDescription = "message-media",
                    contentScale = ContentScale.Crop
                )
                if (mediaType.startsWith("video")) {
                    Icon(
                        painter = painterResource(id = R.drawable.round_play_circle_24),
                        tint = Color.White,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
    if (showMediaViewer.value) {
        messagesItem.mediaPath?.let {
            val uri = Uri.parse(it)
            val mediaType = LocalContext.current.getMediaType(uri)
            if (mediaType.startsWith("image")) {
                ImageViewer(uri = uri) {
                    showMediaViewer.value = false
                }
            } else {
                VideoViewer(uri = uri) {
                    showMediaViewer.value = false
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessagesReceivedItemPreview() {
    MessagesItem(
        MessagesItem(
            body = "hey, hello? hey, hello? hey, hello? hey, hello? hey, hello? hey, hello? hey, hello? hey, hello? hey, hello?",
            createdAt = "2024-02-01 17:30:26",
            type = MessagesType.Received
        )
    )
}

@Preview(showBackground = true)
@Composable
fun MessagesSentItemPreview() {
    MessagesItem(
        MessagesItem(
            body = "hey, hello? hey, hello? hey, hello? hey, , hello? hey, hello?",
            createdAt = "2024-02-01 17:30:28",
            type = MessagesType.Sent
        )
    )
}