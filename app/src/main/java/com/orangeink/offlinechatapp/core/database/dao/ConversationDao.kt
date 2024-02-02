package com.orangeink.offlinechatapp.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.orangeink.offlinechatapp.core.database.entity.Conversation
import com.orangeink.offlinechatapp.core.database.model.ChatHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversationDao {

    @Insert
    suspend fun addConversation(conversation: Conversation)

    @Query("SELECT * FROM conversation_table WHERE participantOneId LIKE :id OR participantTwoId LIKE :id")
    fun getAllConversations(id: Int): Flow<List<ChatHistory>>
}