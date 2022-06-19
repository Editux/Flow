package com.example.flow.domain.repository


import com.example.flow.domain.model.Article
import com.example.flow.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    fun getArticleFromFirestore(): Flow<Response<List<Article>>>

    suspend fun addArticleToFirestore(image:String, title:String, author: String, category:String, content:String): Flow<Response<Void?>>

    suspend fun editArticleToFirestore( articleId:String, title:String, author: String, category:String, content:String): Flow<Response<Void?>>

    suspend fun deleteArticleFromFirestore(articleId: String): Flow<Response<Void?>>
}