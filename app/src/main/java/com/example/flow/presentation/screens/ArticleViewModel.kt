package com.example.flow.presentation.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flow.domain.model.Article
import com.example.flow.domain.model.Response
import com.example.flow.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val useCases: UseCases
):ViewModel() {
    private val _articleState = mutableStateOf<Response<List<Article>>>(Response.Loading)
    val articleState: State<Response<List<Article>>> = _articleState

    private val _isArticleAddedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isArticleAddedState: State<Response<Void?>> = _isArticleAddedState

    private val _isArticleEditState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isArticleEditState: State<Response<Void?>> = _isArticleEditState

    private val _isArticleDeletedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isArticleDeletedState: State<Response<Void?>> = _isArticleDeletedState

    var openDialogState = mutableStateOf(false)

    init {
        getArticles()
    }

    private fun getArticles() {
        viewModelScope.launch {
            useCases.getArticle().collect { response ->
                _articleState.value = response
            }
        }
    }


    fun addArticle( image:String, title:String, author: String, category:String, content:String) {
        viewModelScope.launch {
            useCases.addArticle(image,title, author,category,content).collect { response ->
                _isArticleAddedState.value = response
            }
        }
    }

    fun editArticle(articleId:String, title:String, author: String, category:String, content:String) {
        viewModelScope.launch {
            useCases.editArticle(articleId, title, author, category, content)
                .collect { response ->
                    _isArticleEditState.value = response
                }
        }
    }

    fun deleteArticle(articleId: String) {
        viewModelScope.launch {
            useCases.deleteArticle(articleId).collect { response ->
                _isArticleDeletedState.value = response
            }
        }
    }

}