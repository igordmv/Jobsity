package com.exercise.jobsity.presentation.fragment.home

import android.util.Log
import androidx.lifecycle.ViewModel
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

    fun fetchShows() = CoroutineScope(Dispatchers.IO).launch {
        val test = getShowUseCase.execute(0)
        Log.d("TEST", test.toString())
    }

}