package com.example.domain.usecases.feed.newarticle

import com.example.domain.models.ArticleData
import com.example.domain.repo.IArticleRepo
import com.example.domain.repo.IAuthRepo
import javax.inject.Inject

class CreateArticleUseCase @Inject constructor(
    private val articlesRepo: IArticleRepo,
    private val authRepo: IAuthRepo,
) {
    suspend operator fun invoke(title: String, content: String) {
        val user = authRepo.getUser()!!
        val article = ArticleData(
            title = title,
            imageUrl = "",
            content = content,
            author = user,
            likedUsers = emptyList(),
            creationDate = System.currentTimeMillis(),
        )
        articlesRepo.createArticle(user, articleData = article)
    }
}