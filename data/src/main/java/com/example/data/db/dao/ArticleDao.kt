package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.db.entities.ArticleEntity

@Dao
interface ArticleDao {
    @Query("select * from articles order by creationDate desc limit :limit")
    suspend fun getLastCreated(limit: Int): List<ArticleEntity>

    @Update
    suspend fun updateArticle(articleEntity: ArticleEntity)

    @Insert
    suspend fun create(articleEntity: ArticleEntity): Long

    @Query("select * from articles where authorId = :userId")
    suspend fun getByUserId(userId: Long): List<ArticleEntity>

    @Query("delete from articles where authorId = :authorId and title = :title")
    suspend fun deleteByTitleAndAuthor(title: String, authorId: Long)

    @Query("select * from articles where id = :articleId")
    suspend fun getById(articleId: Long): List<ArticleEntity>

    @Query("select * from articles where authorId = :authorId and title = :title")
    suspend fun getByTitleAndId(title: String, authorId: Long): List<ArticleEntity>

    @Query("select * from articles where title like '%' || :query || '%' or content like '%' || :query || '%'")
    suspend fun searchByTitleAndContent(query: String): List<ArticleEntity>
}