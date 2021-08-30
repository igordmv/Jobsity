package com.exercise.jobsity.presentation.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exercise.jobsity.data.api.Status
import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.domain.usecase.GetFavoriteListUseCase
import com.exercise.jobsity.domain.usecase.GetShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getShowUseCase: GetShowsUseCase,
    private val getFavoriteListUseCase: GetFavoriteListUseCase
) : ViewModel() {

    private val loadingObservable = MutableLiveData<Boolean>().apply { value = true }
    private val showsObservable = MutableLiveData<List<Show>>()
    private val favoriteListObservable = MutableLiveData<List<Show>>()
    private val errorObservable = MutableLiveData<String>()

    fun getLoadingLiveData(): LiveData<Boolean> = loadingObservable
    fun getShowsLiveData(): LiveData<List<Show>> = showsObservable
    fun getFavoriteListLiveData(): LiveData<List<Show>> = favoriteListObservable
    fun getErrorObservable() : LiveData<String> = errorObservable

    fun setShows(shows: List<Show>) {
        showsObservable.postValue(shows)
    }

    fun fetchShows() = CoroutineScope(Dispatchers.IO).launch {
        loadingObservable.postValue(true)
        val showRequest = getShowUseCase.execute(FIRST_PAGE)
        when (showRequest.status) {
            Status.SUCCESS -> {
                handleSuccess(showRequest.data, RequestType.SHOW_LIST)
            }
            Status.ERROR -> {
                errorObservable.postValue(showRequest.message)
            }
        }
        loadingObservable.postValue(false)
    }

    fun fetchFavoriteList() = CoroutineScope(Dispatchers.IO).launch {
        val favoriteListRequest = getFavoriteListUseCase.execute()
        when (favoriteListRequest.status) {
            Status.SUCCESS -> {
                handleSuccess(favoriteListRequest.data, RequestType.FAVORITE)
            }
            Status.ERROR -> {
                errorObservable.postValue(favoriteListRequest.message)
            }
        }
    }

    private fun handleSuccess(data: List<Show>?, requestType: RequestType) {
        when (requestType) {
            RequestType.SHOW_LIST -> {
                data?.let { shows ->
                    showsObservable.postValue(shows.take(MAX_SHOWS_AT_HOME))
                }
            }
            RequestType.FAVORITE -> {
                data?.let { shows ->
                    favoriteListObservable.postValue(shows)
                }
            }
        }
    }

    companion object {
        private const val FIRST_PAGE = 0
        private const val MAX_SHOWS_AT_HOME = 8 //Unfortunately, TvMaze api don't have offset at API
    }
}