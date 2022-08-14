package com.daily.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.daily.app.data.local.entities.ArticleEnt
import com.daily.app.data.local.entities.SourceEnt

@Database(
    entities = [
        ArticleEnt::class,
        SourceEnt::class
    ],
    version = 1
)
abstract class AppDB: RoomDatabase() {
    abstract val dao: AppDao
}