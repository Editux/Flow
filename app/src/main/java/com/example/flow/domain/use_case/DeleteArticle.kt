package com.example.flow.domain.use_case

import com.example.flow.domain.repository.ArticleRepository

class DeleteArticle (private val repo: ArticleRepository
) {
    suspend operator fun invoke(ArticleId: String) = repo.deleteArticleFromFirestore(ArticleId)
}