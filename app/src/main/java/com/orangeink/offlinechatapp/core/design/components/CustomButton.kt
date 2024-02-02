package com.orangeink.offlinechatapp.core.design.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.orangeink.offlinechatapp.core.design.theme.Primary
import com.orangeink.offlinechatapp.core.design.theme.Secondary

private object CustomRippleTheme : RippleTheme {

    @Composable
    override fun defaultColor() = Secondary

    @Composable
    override fun rippleAlpha(): RippleAlpha =
        RippleTheme.defaultRippleAlpha(
            Color.Black,
            lightTheme = true
        )
}

@Composable
fun CustomButton(buttonText: String, onClick: () -> Unit) {
    CompositionLocalProvider(LocalRippleTheme provides CustomRippleTheme) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 1.dp),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(color = Secondary, width = 1.dp),
            contentPadding = PaddingValues(all = 0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        ) {
            Text(
                text = buttonText,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                color = Primary
            )
        }
    }
}