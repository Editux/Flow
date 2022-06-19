package com.example.flow.domain.use_case

import com.example.flow.domain.repository.ArticleRepository

class EditArticle (private val repo: ArticleRepository){
        suspend operator fun invoke(
            articleId: String,
            title: String,
            author: String,
            category: String,
            content:String,
        ) = repo.editArticleToFirestore(articleId, title, author, category, content)
    }
