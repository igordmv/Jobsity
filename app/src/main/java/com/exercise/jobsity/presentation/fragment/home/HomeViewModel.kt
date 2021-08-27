package com.exercise.jobsity.presentation.fragment.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exercise.jobsity.data.api.Status
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

    private val loading = MutableLiveData<Boolean>().apply { value = true }

    fun getLoadingLiveData() : LiveData<Boolean> = loading

    fun fetchShows() = CoroutineScope(Dispatchers.IO).launch {
        loading.postValue(true)
        val showRequest = getShowUseCase.execute(FIRST_PAGE)
        when(showRequest.status) {
            Status.SUCCESS -> {}
            Status.ERROR -> {}
        }
        loading.postValue(false)
    }


    companion object {
        private const val FIRST_PAGE = 0
    }

}