package com.orangeink.offlinechatapp.di

import android.app.Application
import androidx.room.Room
import com.orangeink.offlinechatapp.core.database.dao.UserDao
import com.orangeink.offlinechatapp.core.database.AppDatabase
import com.orangeink.offlinechatapp.core.database.dao.ConversationDao
import com.orangeink.offlinechatapp.core.database.dao.MessageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideUserDatabase(
        application: Application
    ): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.getUserDao()
    }

    @Provides
    fun provideConversationDao(db: AppDatabase): ConversationDao {
        return db.getConversationDao()
    }

    @Provides
    fun provideMessageDao(db: AppDatabase): MessageDao {
        return db.getMessageDao()
    }
}