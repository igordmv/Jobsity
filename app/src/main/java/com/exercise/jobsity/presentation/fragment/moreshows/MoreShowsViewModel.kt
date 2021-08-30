package com.exercise.jobsity.presentation.fragment.moreshows

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
class MoreShowsViewModel @Inject constructor(
    private val getShowsUseCase: GetShowsUseCase
) : ViewModel() {

    var isAlreadyRequesting: Boolean = false
    private var firstPageFetched: Boolean = false
    private val loadingObservable = MutableLiveData<Boolean>().apply { value = true }
    private val errorObservable = MutableLiveData<String>()
    private val showsObservable = MutableLiveData<List<Show>>()
    private var page = 0

    fun getLoadingLiveData(): LiveData<Boolean> = loadingObservable
    fun getShowsLiveData(): LiveData<List<Show>> = showsObservable
    fun getErrorObservable() : LiveData<String> = errorObservable

    fun canPaginate(): Boolean {
        if (!firstPageFetched) {
            return false
        }
        return !isAlreadyRequesting
    }

    fun fetchShows() = CoroutineScope(Dispatchers.IO).launch {
        loadingObservable.postValue(true)
        val showRequest = getShowsUseCase.execute(page++)
        when (showRequest.status) {
            Status.SUCCESS -> {
                handleSuccess(showRequest.data)
            }
            Status.ERROR -> {
                errorObservable.postValue(showRequest.message)
            }
        }
        firstPageFetched = true
        loadingObservable.postValue(false)
    }

    fun getMoreShows() = CoroutineScope(Dispatchers.IO).launch {
        loadingObservable.postValue(true)
        val showRequest = getShowsUseCase.execute(page++)
        when (showRequest.status) {
            Status.SUCCESS -> {
                handleSuccess(showRequest.data)
            }
            Status.ERROR -> {
                errorObservable.postValue(showRequest.message)
            }
        }
        isAlreadyRequesting = false
        loadingObservable.postValue(false)
    }

    private fun handleSuccess(data: List<Show>?) {
        data?.let { shows ->
            showsObservable.postValue(shows)
        }
    }
}