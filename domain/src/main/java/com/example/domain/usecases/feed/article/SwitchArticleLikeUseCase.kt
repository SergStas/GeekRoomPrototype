package com.example.domain.usecases.feed.article

import com.example.domain.models.ArticleData
import com.example.domain.models.UserData
import com.example.domain.repo.IArticleRepo
import com.example.domain.repo.IAuthRepo
import javax.inject.Inject

class SwitchArticleLikeUseCase @Inject constructor(
    private val articleRepo: IArticleRepo,
    private val authRepo: IAuthRepo,
) {
    suspend operator fun invoke(article: ArticleData): ArticleData {
        val user = authRepo.getUser()!!
        val oldValue = user.username in article.likedUsers.map(UserData::username)
        articleRepo.setArticleLike(article, !oldValue, user)
        val newLikesList = article.likedUsers.toMutableList().apply {
            if (oldValue) {
                removeAll { it.username == user.username }
            } else {
                add(user)
            }
        }
        return article.copy(likedUsers = newLikesList)
    }
}