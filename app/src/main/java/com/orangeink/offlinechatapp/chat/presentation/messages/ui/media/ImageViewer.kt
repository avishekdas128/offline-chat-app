package com.orangeink.offlinechatapp.chat.presentation.messages.ui.media

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState
import com.orangeink.offlinechatapp.R
import soup.compose.photo.ExperimentalPhotoApi
import soup.compose.photo.PhotoBox
import soup.compose.photo.rememberPhotoState

@OptIn(ExperimentalPhotoApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun ImageViewer(uri: Uri, onDismiss: () -> Unit) {
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false, dismissOnBackPress = true),
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            GlideSubcomposition(uri) {
                when (state) {
                    RequestState.Failure -> Unit
                    RequestState.Loading -> Unit
                    is RequestState.Success -> {
                        val photoState = rememberPhotoState()
                        photoState.setPhotoIntrinsicSize(painter.intrinsicSize)
                        PhotoBox(state = photoState) {
                            Image(
                                painter,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "close",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .clickable { onDismiss() }
            )
        }
    }
}