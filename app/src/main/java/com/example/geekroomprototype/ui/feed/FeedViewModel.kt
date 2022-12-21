package com.example.geekroomprototype.ui.feed

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ArticleData
import com.example.domain.usecases.feed.article.QueryLastArticlesUseCase
import com.example.domain.usecases.feed.article.QueryTrendingArticlesUseCase
import com.example.geekroomprototype.ui.feed.models.ArticlePreviewFreshListItem
import kotlinx.coroutines.launch
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

    private val _openArticleState = MutableLiveData<OpenArticleState>(OpenArticleState.None)
    val openArticleState: LiveData<OpenArticleState> get() = _openArticleState

    fun loadDigest() {
        loadFreshArticles()
        loadTrendingArticles()
    }

    private fun onOpenArticle(article: ArticlePreviewFreshListItem) {
        _openArticleState.value = OpenArticleState.Article(article.toDomain())
        _openArticleState.value = OpenArticleState.None
    }

    private fun loadFreshArticles() {
        viewModelScope.launch {
            _freshArticles.value = ArticlesLoadingState.Fetched(
                queryLastArticles().map {
                    ArticlePreviewFreshListItem.fromDomain(it, context, ::onOpenArticle)
                },
            )
        }
    }

    private fun loadTrendingArticles() {
        viewModelScope.launch {
            _trendingArticles.value = ArticlesLoadingState.Fetched(
                queryTrendingArticles().map {
                    ArticlePreviewFreshListItem.fromDomain(it, context, ::onOpenArticle)
                },
            )
        }
    }
    sealed class ArticlesLoadingState {
        object Loading: ArticlesLoadingState()
        data class Fetched(val content: List<ArticlePreviewFreshListItem>): ArticlesLoadingState()
    }

    sealed class OpenArticleState {
        object None: OpenArticleState()
        data class Article(val article: ArticleData): OpenArticleState()
    }
}