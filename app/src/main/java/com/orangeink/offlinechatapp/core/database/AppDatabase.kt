package com.orangeink.offlinechatapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.orangeink.offlinechatapp.core.database.dao.ConversationDao
import com.orangeink.offlinechatapp.core.database.dao.MessageDao
import com.orangeink.offlinechatapp.core.database.dao.UserDao
import com.orangeink.offlinechatapp.core.database.entity.Conversation
import com.orangeink.offlinechatapp.core.database.entity.Message
import com.orangeink.offlinechatapp.core.database.entity.User

@Database(
    entities = [User::class, Conversation::class, Message::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    abstract fun getConversationDao(): ConversationDao

    abstract fun getMessageDao(): MessageDao
}