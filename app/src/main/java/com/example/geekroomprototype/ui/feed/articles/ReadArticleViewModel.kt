package com.example.geekroomprototype.ui.feed.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ArticleData
import com.example.domain.models.UserData
import com.example.domain.usecases.feed.article.SwitchArticleLikeUseCase
import com.example.domain.usecases.login.GetLoggedInUserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReadArticleViewModel @Inject constructor(
    private val switchArticleLike: SwitchArticleLikeUseCase,
    private val getLoggedInUser: GetLoggedInUserUseCase,
): ViewModel() {
    private val _article = MutableLiveData<ArticleData?>()
    val article: LiveData<ArticleData?> get() = _article
    private var _user: UserData? = null
    val user get() = _user

    fun loadArticle(articleData: ArticleData) {
        viewModelScope.launch {
            _user = getLoggedInUser()
            _article.value = articleData
        }
    }

    fun switchLike() {
        viewModelScope.launch {
            _article.value = switchArticleLike(_article.value!!)
        }
    }
}