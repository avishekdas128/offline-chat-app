package com.orangeink.offlinechatapp.auth.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orangeink.offlinechatapp.R
import com.orangeink.offlinechatapp.core.design.components.CustomButton
import com.orangeink.offlinechatapp.core.design.components.CustomTextField

sealed interface AuthType {
    data object Login : AuthType
    data object Register : AuthType
}

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    register: (String, String) -> Unit,
    login: (String, String) -> Unit,
    isLoading: Boolean,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var accountType by remember { mutableStateOf<AuthType>(AuthType.Register) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Image(
            modifier = Modifier
                .padding(top = 80.dp, bottom = 48.dp)
                .height(230.dp)
                .width(210.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.offline_chat_app_illustration),
            contentDescription = "login-illustration"
        )
        CustomTextField(value = username, hint = "Username", onTextChanged = { username = it })
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            value = password,
            hint = "Password",
            onTextChanged = { password = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(48.dp))
        CustomButton(
            buttonText = if (accountType == AuthType.Login)
                "Login"
            else
                "Register",
            onClick = {
                if (accountType == AuthType.Login)
                    login(username, password)
                else
                    register(username, password)
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = if (accountType == AuthType.Login)
                "Don't have an account? Register"
            else
                "Already have an account? Login",
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    accountType =
                        if (accountType == AuthType.Login)
                            AuthType.Register
                        else
                            AuthType.Login
                }
                .padding(bottom = 48.dp)
        )
    }
    AnimatedVisibility(visible = isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthScreenPreview() {
    AuthScreen(
        login = { _, _ -> },
        register = { _, _ -> },
        isLoading = true,
    )
}