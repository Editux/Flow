package com.example.flow.database

import com.example.flow.domain.model.Article
import com.example.flow.domain.model.Response
import com.example.flow.domain.model.Response.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import com.example.flow.domain.repository.ArticleRepository
import com.example.flow.utils.Constants.TITLE
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepositoryImpl @Inject constructor(
    private val articleRef:CollectionReference
):ArticleRepository {
    override fun getArticleFromFirestore() =callbackFlow {
        val snapshotListener = articleRef.orderBy(TITLE).addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
                val article = snapshot.toObjects(Article::class.java)

                Response.Success(article)
            } else {
                Error(e?.message ?: e.toString())
            }
            trySend(response).isSuccess
        }
        awaitClose {
            snapshotListener.remove()
        }
    }


//TODO: Change members
override suspend fun addArticleToFirestore( image:String, title:String, author: String, category:String, content:String) = flow {
    try {
        emit(Loading)
        val id = articleRef.document().id
        val article = Article(
            id = id,
            image= image,
            title = title,
            author = author,
            category= category,
            content= content

        )
        val addition = articleRef.document(id).set(article).await()
        emit(Success(addition))
    } catch (e: Exception) {
        emit(Error(e.message ?: e.toString()))
    }
}

    override suspend fun editArticleToFirestore( articleID: String, title:String, author: String, category:String, content:String) = flow {
        try {
            emit(Loading)
            val article = Article(
                id = articleID,
                title = title,
                author = author,
                category= category,
                content= content

            )

           // val eddition = articleRef.document(articleID).set(article, SetOptions.merge()).await()
            val eddition= articleRef.document(articleID).update("title", title,
                "author", author,
                "category",category,
                "content",content).await()
            emit(Success(eddition))
        } catch (e: Exception) {
            emit(Error(e.message ?: e.toString()))
        }
    }


        override suspend fun deleteArticleFromFirestore(articleId: String) = flow {
            try {
                emit(Loading)
                val deletion = articleRef.document(articleId).delete().await()
                emit(Success(deletion))
            } catch (e: Exception) {
                emit(Error(e.message ?: e.toString()))
            }
        }

}