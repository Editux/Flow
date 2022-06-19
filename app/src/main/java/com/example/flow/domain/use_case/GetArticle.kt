package com.example.flow.domain.use_case

import com.example.flow.domain.repository.ArticleRepository

class GetArticle  (
    private val repo: ArticleRepository
) {
    operator fun invoke() = repo.getArticleFromFirestore()
}