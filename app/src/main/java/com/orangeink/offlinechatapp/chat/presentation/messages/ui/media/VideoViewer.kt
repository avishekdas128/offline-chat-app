package com.orangeink.offlinechatapp.chat.presentation.messages.ui.media

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.REPEAT_MODE_OFF
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.orangeink.offlinechatapp.R

@SuppressLint("OpaqueUnitKey")
@Composable
fun VideoViewer(uri: Uri, onDismiss: () -> Unit) {
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false, dismissOnBackPress = true),
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            val context = LocalContext.current
            val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
            val exoPlayer = remember(context) {
                ExoPlayer.Builder(context)
                    .build()
                    .apply {
                        setMediaItem(MediaItem.fromUri(uri))
                        playWhenReady = false
                        repeatMode = REPEAT_MODE_OFF
                        prepare()
                    }
            }
            DisposableEffect(
                key1 = AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = {
                        PlayerView(context).apply { player = exoPlayer }
                    }),
                effect = {
                    val observer = LifecycleEventObserver { _, event ->
                        when (event) {
                            Lifecycle.Event.ON_RESUME -> exoPlayer.play()
                            Lifecycle.Event.ON_PAUSE -> exoPlayer.pause()
                            else -> Unit
                        }
                    }

                    val lifecycle = lifecycleOwner.value.lifecycle
                    lifecycle.addObserver(observer)

                    onDispose {
                        exoPlayer.release()
                        lifecycle.removeObserver(observer)
                    }
                }
            )
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