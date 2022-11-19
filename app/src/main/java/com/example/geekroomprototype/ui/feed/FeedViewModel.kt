package com.example.geekroomprototype.ui.feed

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ArticleData
import com.example.domain.usecases.feed.article.QueryLastArticlesUseCase
import com.example.domain.usecases.feed.article.QueryTrendingArticlesUseCase
import com.example.geekroomprototype.R
import com.example.geekroomprototype.ui.feed.models.FreshArticleRvItem
import com.example.geekroomprototype.util.extensions.toastInDevelopment
import kotlinx.coroutines.launch
import java.lang.Long.max
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val queryTrendingArticles: QueryTrendingArticlesUseCase,
    private val queryLastArticles: QueryLastArticlesUseCase,
    private val context: Context,
): ViewModel() {
    private val _trendingArticles = MutableLiveData<ArticlesLoadingState>(ArticlesLoadingState.Loading)
    val trendingArticles: LiveData<ArticlesLoadingState> get() = _trendingArticles

    private val _freshArticles = MutableLiveData<ArticlesLoadingState>(ArticlesLoadingState.Loading)
    val freshArticles: LiveData<ArticlesLoadingState> get() = _freshArticles

    fun loadDigest() {
        loadFreshArticles()
        loadTrendingArticles()
    }

    private fun onOpenArticle(article: FreshArticleRvItem) {
        context.toastInDevelopment()
    }

    private fun loadFreshArticles() {
        viewModelScope.launch {
            _freshArticles.value = ArticlesLoadingState.Fetched(
                queryLastArticles().map(::mapToFreshArticleItem),
            )
        }
    }

    private fun loadTrendingArticles() {
        viewModelScope.launch {
            _trendingArticles.value = ArticlesLoadingState.Fetched(
                queryTrendingArticles().map(::mapToFreshArticleItem),
            )
        }
    }

    private fun mapToFreshArticleItem(article: ArticleData) =
        FreshArticleRvItem(
            title = article.title,
            imageUrl = article.imageUrl,
            content = article.content,
            authorAvatarUrl = article.author.avatarUrl,
            authorName = article.author.username,
            authorTag = article.author.username,
            likesCount = article.likedUsers.size,
            creationDateToken = formatDate(article.creationDate),
            onOpen = ::onOpenArticle,
        )

    private fun formatDate(ts: Long): String {
        val seconds = max((System.currentTimeMillis() - ts) / 1000, 1)
        return when {
            seconds < 60 -> context.getString(R.string.listitem_article_date_ph_sec).format(seconds)
            seconds < 60 * 60 -> context.getString(R.string.listitem_article_date_ph_minutes).format(seconds / 60)
            seconds < 60 * 60 * 24 -> context.getString(R.string.listitem_article_date_ph_hours).format(seconds / 60 / 60)
            seconds < 60 * 60 * 24 * 7 -> context.getString(R.string.listitem_article_date_ph_days).format(seconds / 60 / 60 / 24)
            seconds < 60 * 60 * 24 * 7 * 30 -> context.getString(R.string.listitem_article_date_ph_weeks).format(seconds / 60 / 60 / 24 / 7)
            seconds < 60 * 60 * 24 * 7 * 30 * 12 -> context.getString(R.string.listitem_article_date_ph_months).format(seconds / 60 / 60 / 24 / 7 / 30)
            else -> context.getString(R.string.listitem_article_date_ph_years).format(seconds / 60 / 60 / 24 / 7 / 30 / 12)
        }
    }

    sealed class ArticlesLoadingState {
        object Loading: ArticlesLoadingState()
        data class Fetched(val content: List<FreshArticleRvItem>): ArticlesLoadingState()
    }
}