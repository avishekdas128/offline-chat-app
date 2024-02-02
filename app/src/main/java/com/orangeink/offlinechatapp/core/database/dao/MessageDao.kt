package com.orangeink.offlinechatapp.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.orangeink.offlinechatapp.core.database.model.MessagesHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Query("INSERT INTO message_table (body, mediaPath, conversationId, toId, fromId) VALUES(:body, :mediaPath, :conversationId, :toId, :fromId)")
    suspend fun addMessage(
        body: String?,
        mediaPath: String?,
        conversationId: Int,
        toId: Int,
        fromId: Int
    )

    @Query("SELECT * FROM message_table WHERE conversationId LIKE :conversationId ORDER BY createdAt ASC")
    fun getAllMessages(conversationId: Int): Flow<List<MessagesHistory>>
}