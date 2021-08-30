package com.exercise.jobsity.presentation.fragment.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.exercise.jobsity.data.api.Resource
import com.exercise.jobsity.data.api.Status
import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.domain.usecase.GetFavoriteListUseCase
import com.exercise.jobsity.domain.usecase.GetShowsUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest() {

    @MockK
    lateinit var getShowsUseCase: GetShowsUseCase

    @MockK
    lateinit var getFavoriteListUseCase: GetFavoriteListUseCase

    @RelaxedMockK
    private lateinit var showObserver: Observer<List<Show>>

    @RelaxedMockK
    private lateinit var loadingObserver: Observer<Boolean>

    @RelaxedMockK
    private lateinit var favoriteListObservable: Observer<List<Show>>

    @RelaxedMockK
    private lateinit var errorObservable: Observer<String>

    lateinit var homeViewModel: HomeViewModel

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
        homeViewModel = HomeViewModel(getShowsUseCase, getFavoriteListUseCase)
        every { showObserver.onChanged(any()) } answers {}
        every { loadingObserver.onChanged(any()) } answers {}
        every { favoriteListObservable.onChanged(any()) } answers {}
        every { errorObservable.onChanged(any()) } answers {}
        homeViewModel.getShowsLiveData().observeForever(showObserver)
        homeViewModel.getLoadingLiveData().observeForever(loadingObserver)
        homeViewModel.getFavoriteListLiveData().observeForever(favoriteListObservable)
        homeViewModel.getErrorObservable().observeForever(errorObservable)
    }

    @Test
    fun `test when call setShows showsObservable is called`() = runBlocking {
        homeViewModel.setShows(listOf(show))
        verify { showObserver.onChanged(listOf(show)) }
    }

    @Test
    fun `test fetchShows when result list is not null`() = runBlocking {
        coEvery { getShowsUseCase.execute(any()) } coAnswers { resourceShowList }
        homeViewModel.fetchShows().join()
        verifyOrder {
            loadingObserver.onChanged(true)
            showObserver.onChanged(resourceShowList.data)
            loadingObserver.onChanged(false)
        }
    }

    @Test
    fun `test fetchShows when result list is null`() = runBlocking {
        coEvery { getShowsUseCase.execute(any()) } coAnswers { emptyResourceShowList }
        homeViewModel.fetchShows().join()
        verifyOrder {
            loadingObserver.onChanged(true)
            loadingObserver.onChanged(false)
        }
        verify(exactly = 0) {
            showObserver.onChanged(emptyResourceShowList.data)
        }
    }

    @Test
    fun `test fetch favorite list`() = runBlocking {
        coEvery { getFavoriteListUseCase.execute() } coAnswers { resourceShowList }
        homeViewModel.fetchFavoriteList().join()
        verify(exactly = 1) { favoriteListObservable.onChanged(resourceShowList.data) }
    }

    @Test
    fun `test fetchShows error request`() = runBlocking {
        coEvery { getShowsUseCase.execute(any()) } coAnswers { errorRequest }
        homeViewModel.fetchShows().join()
        verifyOrder {
            loadingObserver.onChanged(true)
            errorObservable.onChanged(errorRequest.message)
            loadingObserver.onChanged(false)
        }
    }

    @Test
    fun `test favoriteList error request`() = runBlocking {
        coEvery { getFavoriteListUseCase.execute() } coAnswers { errorRequest }
        homeViewModel.fetchFavoriteList().join()
        verify {
            errorObservable.onChanged(errorRequest.message)
        }
    }
}