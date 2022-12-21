package com.example.geekroomprototype.ui.search.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ArticleData
import com.example.domain.usecases.search.SearchArticlesUseCase
import com.example.geekroomprototype.ui.feed.models.ArticlePreviewFreshListItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchArticles: SearchArticlesUseCase,
    private val context: Context,
): ViewModel() {
    val state get() = _state.asSharedFlow()
    private val _state = MutableSharedFlow<State>()

    fun search(query: String) =
        viewModelScope.launch {
            _state.emit(State.Loading)
            val mapped = if (query.isBlank() || query.isEmpty()) {
                emptyList()
            } else {
                val results = searchArticles(query)
                results.map {
                    ArticlePreviewFreshListItem.fromDomain(it, context, ::openArticle)
                }
            }
            _state.emit(State.ResultsLoaded(mapped))
            _state.emit(State.Waiting)
        }

    private fun openArticle(article: ArticlePreviewFreshListItem) =
        viewModelScope.launch {
            _state.emit(State.OpenArticle(article.toDomain()))
            _state.emit(State.Waiting)
        }

    sealed interface State {
        object Waiting: State
        object Loading: State
        data class ResultsLoaded(val items: List<ArticlePreviewFreshListItem>): State
        data class OpenArticle(val article: ArticleData): State
    }
}