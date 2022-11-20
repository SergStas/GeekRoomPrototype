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
import com.example.geekroomprototype.util.extensions.formatDate
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
            creationDateToken = formatDate(context, article.creationDate),
            onOpen = ::onOpenArticle,
        )

    sealed class ArticlesLoadingState {
        object Loading: ArticlesLoadingState()
        data class Fetched(val content: List<FreshArticleRvItem>): ArticlesLoadingState()
    }
}