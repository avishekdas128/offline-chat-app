package com.orangeink.offlinechatapp.chat.presentation.messages.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.orangeink.offlinechatapp.R
import com.orangeink.offlinechatapp.core.design.theme.Secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesTopBar(navController: NavController, toUserName: String, onEchoClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    painter = painterResource(id = R.drawable.round_arrow_back_ios_new_24),
                    contentDescription = "logout"
                )
            }
        },
        title = {
            Text(
                text = toUserName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Secondary,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
            navigationIconContentColor = Color.White
        ),
        actions = {
            IconButton(onClick = onEchoClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_echo),
                    contentDescription = "echo"
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MessagesTopBarPreview() {
    MessagesTopBar(rememberNavController(), "Robert") {}
}