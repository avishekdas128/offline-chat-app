package com.orangeink.offlinechatapp.chat.presentation.messages.ui.components

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orangeink.offlinechatapp.R
import com.orangeink.offlinechatapp.core.design.theme.GreyDark
import com.orangeink.offlinechatapp.core.design.theme.Primary
import com.orangeink.offlinechatapp.core.design.theme.Secondary

@Composable
fun MessagesBottomBar(onSendMessage: (body: String?, mediaPath: String?) -> Unit) {
    var message by remember { mutableStateOf("") }
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                onSendMessage(null, it.toString())
            }
        }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Secondary)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = message,
            onValueChange = { message = it },
            modifier = Modifier
                .weight(1f)
                .background(color = Color.White, shape = RoundedCornerShape(5.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            textStyle = TextStyle(
                fontSize = 12.sp
            ),
            maxLines = 4,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (message.isEmpty()) {
                            Text(
                                text = "Message",
                                color = GreyDark,
                                fontSize = 12.sp
                            )
                        }
                        innerTextField()
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                launcher.launch(
                                    PickVisualMediaRequest(
                                        mediaType = ActivityResultContracts.PickVisualMedia.ImageAndVideo
                                    )
                                )
                            },
                        painter = painterResource(id = R.drawable.ic_media),
                        tint = Primary,
                        contentDescription = "media"
                    )
                }
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                onSendMessage(message, null)
                message = ""
            },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.size(32.dp),
            enabled = message.isNotEmpty(),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Primary
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_send),
                contentDescription = "send-message"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessagesBottomBarPreview() {
    MessagesBottomBar { _, _ -> }
}