package com.gmail.maystruks08.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class MenuTable(
    @PrimaryKey
    var id: String
)




