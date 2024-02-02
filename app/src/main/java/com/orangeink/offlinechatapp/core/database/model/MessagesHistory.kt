package com.orangeink.offlinechatapp.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.orangeink.offlinechatapp.core.database.entity.Message
import com.orangeink.offlinechatapp.core.database.entity.User

data class MessagesHistory(
    @Embedded
    val message: Message,
    @Relation(entity = User::class, parentColumn = "fromId", entityColumn = "id")
    val fromUser: User,
    @Relation(entity = User::class, parentColumn = "toId", entityColumn = "id")
    val toUser: User,
)
