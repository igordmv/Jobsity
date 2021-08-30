package com.exercise.jobsity.presentation.fragment.moreshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.exercise.jobsity.data.api.Resource
import com.exercise.jobsity.data.api.Status
import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.domain.usecase.GetShowsUseCase
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

class MoreShowsViewModelTest {

    @MockK
    lateinit var getShowsUseCase: GetShowsUseCase

    @RelaxedMockK
    private lateinit var showObserver: Observer<List<Show>>

    @RelaxedMockK
    private lateinit var loadingObserver: Observer<Boolean>

    @RelaxedMockK
    private lateinit var errorObservable: Observer<String>

    lateinit var moreShowsViewModel: MoreShowsViewModel

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
        moreShowsViewModel = MoreShowsViewModel(getShowsUseCase)
        every { showObserver.onChanged(any()) } answers {}
        every { loadingObserver.onChanged(any()) } answers {}
        every { errorObservable.onChanged(any()) } answers {}
        moreShowsViewModel.getShowsLiveData().observeForever(showObserver)
        moreShowsViewModel.getLoadingLiveData().observeForever(loadingObserver)
        moreShowsViewModel.getErrorObservable().observeForever(errorObservable)
    }

    @Test
    fun `test when call fetchShows with success response, observables is being called correctly`()  = runBlocking {
        coEvery { getShowsUseCase.execute(any()) } coAnswers { resourceShowList }
        moreShowsViewModel.fetchShows().join()
        verifyOrder {
            loadingObserver.onChanged(true)
            showObserver.onChanged(resourceShowList.data)
            loadingObserver.onChanged(false)
        }
    }

    @Test
    fun `test when call fetchShows with error response, observables is being called correctly`()  = runBlocking {
        coEvery { getShowsUseCase.execute(any()) } coAnswers { errorRequest }
        moreShowsViewModel.fetchShows().join()
        verifyOrder {
            loadingObserver.onChanged(true)
            errorObservable.onChanged(errorRequest.message)
            loadingObserver.onChanged(false)
        }
    }

    @Test
    fun `test when call getMoreShows with error response, observables is being called correctly`()  = runBlocking {
        coEvery { getShowsUseCase.execute(any()) } coAnswers { errorRequest }
        moreShowsViewModel.getMoreShows().join()
        verifyOrder {
            loadingObserver.onChanged(true)
            errorObservable.onChanged(errorRequest.message)
            loadingObserver.onChanged(false)
        }
    }

    @Test
    fun `test when call getMoreShows with success response, observables is being called correctly`()  = runBlocking {
        coEvery { getShowsUseCase.execute(any()) } coAnswers { resourceShowList }
        moreShowsViewModel.getMoreShows().join()
        verifyOrder {
            loadingObserver.onChanged(true)
            showObserver.onChanged(resourceShowList.data)
            loadingObserver.onChanged(false)
        }
    }

    @Test
    fun `test when call fetchShows with success response but empty list`()  = runBlocking {
        coEvery { getShowsUseCase.execute(any()) } coAnswers { emptyResourceShowList }
        moreShowsViewModel.fetchShows().join()
        verifyOrder {
            loadingObserver.onChanged(true)
            loadingObserver.onChanged(false)
        }
    }

    @Test
    fun `test when call moreShows with success response but empty list`()  = runBlocking {
        coEvery { getShowsUseCase.execute(any()) } coAnswers { emptyResourceShowList }
        moreShowsViewModel.getMoreShows().join()
        verifyOrder {
            loadingObserver.onChanged(true)
            loadingObserver.onChanged(false)
        }
    }

}