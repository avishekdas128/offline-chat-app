package com.orangeink.offlinechatapp.chat.presentation.messages.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orangeink.offlinechatapp.core.design.theme.Secondary

@Composable
fun MessagesDateItem(date: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = date,
            fontSize = 8.sp,
            color = Color.White,
            modifier = Modifier
                .background(color = Secondary, shape = RoundedCornerShape(5.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MessagesDateItemPreview() {
    MessagesDateItem("28 Jan, 2024")
}