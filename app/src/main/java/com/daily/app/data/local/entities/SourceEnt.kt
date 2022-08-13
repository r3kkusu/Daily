package com.daily.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "source_table")
data class SourceEnt(
    @PrimaryKey(autoGenerate = true)
    var source_id: Long,
    var favicon: String?,
    var name: String,
    var url: String
)