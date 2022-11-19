package com.example.domain.usecases.feed.article

import com.example.domain.models.ArticleData
import com.example.domain.repo.IArticleRepo
import com.example.domain.repo.IAuthRepo
import javax.inject.Inject

class QueryUserArticlesUseCase @Inject constructor(
    private val articlesRepo: IArticleRepo,
    private val authRepo: IAuthRepo,
) {
    suspend operator fun invoke(): List<ArticleData> {
        val user = authRepo.getUser()!!
        return articlesRepo.getUserArticles(user)
    }
}