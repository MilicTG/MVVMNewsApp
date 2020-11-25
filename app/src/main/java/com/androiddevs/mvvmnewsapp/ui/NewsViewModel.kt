package com.androiddevs.mvvmnewsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.models.Article
import com.androiddevs.mvvmnewsapp.models.NewsResponse
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

//in Constructor ViewModel receives repository
//ViewModel can't use constructor use ViewModelProviderFactory
//in ViewModel call functions from repository
//Handle the responses
class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    //create LiveDataObject
    //wrap response object in resource
    //fragments subscribe to LiveData
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    //manage paging in viewModel
    var breakingNewsPage = 1
    var searchNewsPage = 1

    //start wit init
    init {
        getBreakingNews("us")
    }

    //the function from repo is suspend fun
    //don't call suspend fun in fragment
    //Use Coroutines ViewModelScope
    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        //emit a loading state to signal Network call
        breakingNews.postValue(Resource.Loading())
        //get a response
        //network response is stored in response
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    //here you omit if is response successful or error
    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}