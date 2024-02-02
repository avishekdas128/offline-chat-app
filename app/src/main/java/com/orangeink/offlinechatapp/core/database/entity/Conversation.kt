package com.orangeink.offlinechatapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "conversation_table", foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["participantOneId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["participantTwoId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Conversation(
    @ColumnInfo(index = true)
    val participantOneId: Int,
    @ColumnInfo(index = true)
    val participantTwoId: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
