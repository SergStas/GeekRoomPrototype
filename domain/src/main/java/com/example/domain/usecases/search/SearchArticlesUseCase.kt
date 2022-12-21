package com.example.domain.usecases.search

import com.example.domain.models.ArticleData
import com.example.domain.repo.IArticleRepo
import javax.inject.Inject

class SearchArticlesUseCase @Inject constructor(
    private val articlesRepo: IArticleRepo,
) {
    suspend operator fun invoke(query: String): List<ArticleData> =
        articlesRepo.search(query)
}