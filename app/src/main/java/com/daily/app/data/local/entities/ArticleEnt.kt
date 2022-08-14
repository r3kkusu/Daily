package com.daily.app.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_table")
data class ArticleEnt(
    @PrimaryKey(autoGenerate = true)
    var article_id: Long,
    var description: String?,
    var link: String,
    var published_date: String,
    @Embedded
    var source: SourceEnt,
    var thumbnail: String?,
    var title: String,
    var category: String
)