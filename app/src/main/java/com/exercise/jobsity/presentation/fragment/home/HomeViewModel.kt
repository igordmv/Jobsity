package com.exercise.jobsity.presentation.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exercise.jobsity.data.api.Status
import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.domain.usecase.GetShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(
    private val getShowUseCase: GetShowsUseCase
) : ViewModel()  {

    private val loadingObservable = MutableLiveData<Boolean>().apply { value = true }
    private val showsObservable = MutableLiveData<List<Show>>().apply { value = emptyList() }

    fun getLoadingLiveData() : LiveData<Boolean> = loadingObservable
    fun getShowsLiveData() : LiveData<List<Show>> = showsObservable

    fun setShows(shows : List<Show>) {
        showsObservable.postValue(shows)
    }

    fun fetchShows() = CoroutineScope(Dispatchers.IO).launch {
        loadingObservable.postValue(true)
        val showRequest = getShowUseCase.execute(FIRST_PAGE)
        when(showRequest.status) {
            Status.SUCCESS -> { handleSuccess(showRequest.data)}
            Status.ERROR -> {}
        }
        loadingObservable.postValue(false)
    }

    private fun handleSuccess(data: List<Show>?) {
        data?.let { shows ->
            showsObservable.postValue(shows.take(MAX_SHOWS_AT_HOME))
        }
    }

    companion object {
        private const val FIRST_PAGE = 0
        private const val MAX_SHOWS_AT_HOME = 8 //Unfortunately, TvMaze api don't have offset at API
    }
}