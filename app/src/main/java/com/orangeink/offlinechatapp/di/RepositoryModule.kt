package com.orangeink.offlinechatapp.di

import com.orangeink.offlinechatapp.auth.data.AuthRepositoryImpl
import com.orangeink.offlinechatapp.auth.domain.AuthRepository
import com.orangeink.offlinechatapp.chat.data.ChatRepositoryImpl
import com.orangeink.offlinechatapp.chat.domain.ChatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindChatRepository(impl: ChatRepositoryImpl): ChatRepository
}