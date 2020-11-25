package com.androiddevs.mvvmnewsapp.repository

import com.androiddevs.mvvmnewsapp.api.RetrofitInstance
import com.androiddevs.mvvmnewsapp.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.models.Article

//Repo gets the data and pass it to ViewModel
//Repo gets the data
class NewsRepository(
    val db: ArticleDatabase
) {

    //call the retroFitInstance than the get fun from interface
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    //call the news search query
    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    //add to database
    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    //get all saved articles
    fun getSavedNews() = db.getArticleDao().getAllArticles()

    //delete article
    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}