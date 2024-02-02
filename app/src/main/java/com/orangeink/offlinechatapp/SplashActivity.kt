package com.orangeink.offlinechatapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.asLiveData
import com.orangeink.offlinechatapp.auth.presentation.AuthActivity
import com.orangeink.offlinechatapp.chat.presentation.ChatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    @Inject
    lateinit var userDataStore: DataStore<Preferences>

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }

        userDataStore.data.map { it[intPreferencesKey("userId")] }.asLiveData()
            .observe(this) { username ->
                startActivity(
                    username?.let {
                        Intent(this, ChatActivity::class.java)
                    } ?: Intent(this, AuthActivity::class.java)
                )
                finish()
            }
    }
}
