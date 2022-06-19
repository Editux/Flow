package com.example.flow.domain.use_case

import com.example.flow.domain.repository.ArticleRepository

class AddArticle (  private val repo: ArticleRepository
) {
    suspend operator fun invoke(
        image:String,
        title: String,
        author: String,
        category:String,
        content:String,
    ) = repo.addArticleToFirestore( image,title, author,category,content)
}
