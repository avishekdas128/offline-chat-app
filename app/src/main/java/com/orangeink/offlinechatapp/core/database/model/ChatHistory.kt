package com.orangeink.offlinechatapp.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.orangeink.offlinechatapp.core.database.entity.Conversation
import com.orangeink.offlinechatapp.core.database.entity.Message
import com.orangeink.offlinechatapp.core.database.entity.User

data class ChatHistory(
    @Embedded
    val conversation: Conversation,
    @Relation(entity = User::class, parentColumn = "participantOneId", entityColumn = "id")
    val participantOneUser: User,
    @Relation(entity = User::class, parentColumn = "participantTwoId", entityColumn = "id")
    val participantTwoUser: User,
    @Relation(entity = Message::class, parentColumn = "id", entityColumn = "conversationId")
    val messages: List<Message>
)