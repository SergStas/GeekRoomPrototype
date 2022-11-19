package com.example.data.repo

import com.example.data.db.dao.ArticleDao
import com.example.data.db.dao.LikesDao
import com.example.data.db.dao.UserDao
import com.example.data.db.entities.ArticleEntity
import com.example.data.db.entities.ArticleEntity.Companion.toDbEntity
import com.example.data.db.entities.UserArticleLikeEntity
import com.example.domain.models.ArticleData
import com.example.domain.models.UserData
import com.example.domain.repo.IArticleRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticlesRepo @Inject constructor(
    private val likesDao: LikesDao,
    private val articleDao: ArticleDao,
    private val userDao: UserDao,
): IArticleRepo {
    override suspend fun createArticle(user: UserData, articleData: ArticleData) {
        withContext(Dispatchers.IO) {
            val authorId = userDao.getByName(user.username)[0].id
            val entity = articleData.toDbEntity(authorId)
            val articleId = articleDao.create(entity)
            articleData.likedUsers.forEach {
                val userId = userDao.getByName(it.username)[0].id
                likesDao.create(UserArticleLikeEntity(userId, articleId))
            }
        }
    }

    override suspend fun getLastArticles(limit: Int) =
        withContext(Dispatchers.IO) {
            val entities = articleDao.getLastCreated(limit)
            entities.map { mapArticleEntityToDomainData(it) }
        }

    override suspend fun getTrendingArticles(limit: Int) =
        withContext(Dispatchers.IO) {
            val articleIds = likesDao.getTopArticles(limit)
            val entities = articleIds.map { articleDao.getById(it)[0] }
            entities.map { mapArticleEntityToDomainData(it) }
        }

    private suspend fun mapArticleEntityToDomainData(article: ArticleEntity): ArticleData {
        val author = userDao.getByName(article.title)[0].toUserData()
        val likes = likesDao.getLikesForArticle(article.id).map {
            userDao.getById(it.userId)[0].toUserData()
        }
        return article.toDomainData(
            author = author,
            likedUsers = likes,
        )
    }
}