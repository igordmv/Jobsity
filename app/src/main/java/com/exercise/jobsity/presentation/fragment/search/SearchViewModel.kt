package com.exercise.jobsity.presentation.fragment.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exercise.jobsity.data.api.Status
import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.domain.usecase.SearchShowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchShowUseCase: SearchShowUseCase
) : ViewModel() {

    private val loadingObservable = MutableLiveData<Boolean>().apply { value = false }
    private val showsObservable = MutableLiveData<List<Show>>().apply { value = emptyList() }

    fun getLoadingLiveData(): LiveData<Boolean> = loadingObservable
    fun getShowsLiveData(): LiveData<List<Show>> = showsObservable

    fun search(query: String) = CoroutineScope(Dispatchers.IO).launch {
        loadingObservable.postValue(true)
        val searchRequest = searchShowUseCase.execute(query)
        when (searchRequest.status) {
            Status.SUCCESS -> {
                handleSuccess(searchRequest.data)
            }
            Status.ERROR -> {
            }
        }
        loadingObservable.postValue(false)
    }

    private fun handleSuccess(data: List<Show>?) {
        data?.let {
            showsObservable.postValue(it)
        }
    }

}