package com.example.flow.di

import com.example.flow.database.ArticleRepositoryImpl
import com.example.flow.domain.repository.ArticleRepository
import com.example.flow.domain.use_case.*
import com.example.flow.utils.Constants.NEWS
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore
    @Provides
    fun provideArticleRef(
        db: FirebaseFirestore
    ) = db.collection(NEWS)

    @Provides
    fun provideBooksRepository(
        articlesRef: CollectionReference
    ): ArticleRepository = ArticleRepositoryImpl(articlesRef)

    @Provides
    fun provideUseCases(
        repo: ArticleRepository
    ) = UseCases(
        getArticle = GetArticle(repo),
        addArticle = AddArticle(repo),
        deleteArticle = DeleteArticle(repo),
        editArticle= EditArticle(repo)

    )

}