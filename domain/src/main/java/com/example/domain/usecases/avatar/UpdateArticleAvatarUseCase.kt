package com.example.domain.usecases.avatar

import com.example.domain.models.ArticleData
import com.example.domain.repo.IAvatarsRepo
import javax.inject.Inject

class UpdateArticleAvatarUseCase @Inject constructor(
    private val avatarsRepo: IAvatarsRepo,
) {
    suspend operator fun invoke(url: String, articleData: ArticleData) =
        avatarsRepo.updateArticle(articleData, url)
}