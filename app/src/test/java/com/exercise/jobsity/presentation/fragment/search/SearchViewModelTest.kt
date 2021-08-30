package com.exercise.jobsity.presentation.fragment.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.exercise.jobsity.data.api.Resource
import com.exercise.jobsity.data.api.Status
import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.domain.usecase.SearchShowUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verifyOrder
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    @MockK
    lateinit var searchShowUseCase: SearchShowUseCase

    @RelaxedMockK
    private lateinit var showObserver: Observer<List<Show>>

    @RelaxedMockK
    private lateinit var loadingObserver: Observer<Boolean>

    @RelaxedMockK
    private lateinit var errorObservable: Observer<String>

    lateinit var searchViewModel: SearchViewModel

    private val emptyResourceShowList = Resource(Status.SUCCESS, null, "")
    private val show = Show(1, "", "", "", listOf(""), null, null, "", "", "", 60)
    private val resourceShowList: Resource<List<Show>> = Resource(Status.SUCCESS, listOf(show), "")
    private val errorRequest: Resource<List<Show>> = Resource(Status.ERROR, null, "erro")

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        searchViewModel = SearchViewModel(searchShowUseCase)
        every { showObserver.onChanged(any()) } answers {}
        every { loadingObserver.onChanged(any()) } answers {}
        every { errorObservable.onChanged(any()) } answers {}
        searchViewModel.getShowsLiveData().observeForever(showObserver)
        searchViewModel.getLoadingLiveData().observeForever(loadingObserver)
        searchViewModel.getErrorLiveData().observeForever(errorObservable)
    }

    @Test
    fun `test search call when request is success`() = runBlocking {
        coEvery { searchShowUseCase.execute(any()) } coAnswers { resourceShowList }
        searchViewModel.search("abc").join()
        verifyOrder {
            loadingObserver.onChanged(true)
            showObserver.onChanged(resourceShowList.data)
            loadingObserver.onChanged(false)
        }
    }

    @Test
    fun `test search call when request is error`() = runBlocking {
        coEvery { searchShowUseCase.execute(any()) } coAnswers { errorRequest }
        searchViewModel.search("abc").join()
        verifyOrder {
            loadingObserver.onChanged(true)
            errorObservable.onChanged(errorRequest.message)
            loadingObserver.onChanged(false)
        }
    }

    @Test
    fun `test search call when request is empty`() = runBlocking {
        coEvery { searchShowUseCase.execute(any()) } coAnswers { emptyResourceShowList }
        searchViewModel.search("abc").join()
        verifyOrder {
            loadingObserver.onChanged(true)
            loadingObserver.onChanged(false)
        }
    }
}