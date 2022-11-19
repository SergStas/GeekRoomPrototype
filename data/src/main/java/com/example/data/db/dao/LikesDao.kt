package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.db.entities.UserArticleLikeEntity

@Dao
interface LikesDao {
    @Query("select * from likes where articleId = :articleId")
    suspend fun getLikesForArticle(articleId: Long): List<UserArticleLikeEntity>

    @Insert
    suspend fun create(likeEntity: UserArticleLikeEntity)

    @Query("delete from likes where userId = :userId and articleId = :articleId")
    suspend fun deleteByIds(userId: Long, articleId: Long)

    @Query("select articleId from (" +
            "select articleId, count(*) as count from likes group by articleId" +
            ") order by count limit :limit")
    suspend fun getTopArticles(limit: Int): List<Long>
}