package com.example.domain.repo

import com.example.domain.models.ArticleData
import com.example.domain.models.UserData

interface IArticleRepo {
    suspend fun createArticle(user: UserData, articleData: ArticleData)

    suspend fun getLastArticles(limit: Int = 10): List<ArticleData>

    suspend fun getTrendingArticles(limit: Int = 10): List<ArticleData>

    suspend fun getUserArticles(user: UserData): List<ArticleData>
}