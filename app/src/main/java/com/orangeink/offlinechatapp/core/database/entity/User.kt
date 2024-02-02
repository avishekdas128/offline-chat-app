package com.orangeink.offlinechatapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_table", indices = [Index(value = ["username"], unique = true)])
data class User(
    val username: String,
    val password: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true)
    val id: Int = 0
)
