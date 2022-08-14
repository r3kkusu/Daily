package com.daily.app.data.local

import androidx.room.*
import com.daily.app.data.local.entities.ArticleEnt

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(articleEnt: List<ArticleEnt>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(articleEnt: ArticleEnt)

    @Update
    suspend fun update(articleEnt: ArticleEnt)

    @Delete
    suspend fun delete(articleEnt: ArticleEnt)

    @Query("SELECT * FROM article_table WHERE category = :cat")
    fun getArticles(cat: String): List<ArticleEnt>

    @Query("DELETE FROM article_table WHERE category == :cat")
    suspend fun clear(cat: String)
}
