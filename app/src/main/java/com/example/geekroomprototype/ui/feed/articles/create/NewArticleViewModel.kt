package com.example.geekroomprototype.ui.feed.articles.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.feed.newarticle.CreateArticleUseCase
import com.example.domain.usecases.feed.article.QueryUserArticlesUseCase
import com.example.geekroomprototype.util.Constants
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewArticleViewModel @Inject constructor(
    private val queryUserArticles: QueryUserArticlesUseCase,
    private val createArticle: CreateArticleUseCase,
): ViewModel() {
    private val _creationState = MutableLiveData<CreationState>(CreationState.Waiting)
    val creationState: LiveData<CreationState> get() = _creationState

    fun create(title: String, content: String) {
        viewModelScope.launch {
            val errorMsg = validate(title, content)
            if (errorMsg != null) {
                _creationState.value = CreationState.Error(errorMsg)
            } else {
                createArticle(title, content)
                _creationState.value = CreationState.Success
                _creationState.value = CreationState.Waiting
            }
        }
    }

    private suspend fun validate(title: String, content: String): String? {
        val existingTitles = queryUserArticles().map { it.title }
        return when {
            title in existingTitles -> "You already have article with same title"
            title.isEmpty() -> "You specified empty title"
            content.length < Constants.ARTICLE_MIN_LENGTH ->
                "Minimal article length is 50 symbols (${Constants.ARTICLE_MIN_LENGTH - content.length} left)"
            else -> null
        }
    }

    sealed class CreationState {
        object Waiting: CreationState()
        data class Error(val message: String): CreationState()
        object Success: CreationState()
    }
}