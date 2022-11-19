package com.example.domain.usecases.feed.article

import com.example.domain.repo.IArticleRepo
import javax.inject.Inject

class QueryTrendingArticlesUseCase @Inject constructor(
    private val articlesRepo: IArticleRepo,
) {
    suspend operator fun invoke() = articlesRepo.getTrendingArticles()
}