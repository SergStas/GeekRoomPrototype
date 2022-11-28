package com.example.geekroomprototype.ui.feed.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.models.ArticleData
import javax.inject.Inject

class ReadArticleViewModel @Inject constructor(

): ViewModel() {
    private val _article = MutableLiveData<ArticleData?>()
    val article: LiveData<ArticleData?> get() = _article


}