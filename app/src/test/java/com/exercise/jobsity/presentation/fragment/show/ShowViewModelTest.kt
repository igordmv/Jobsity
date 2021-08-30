package com.exercise.jobsity.presentation.fragment.show

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.exercise.jobsity.data.api.Resource
import com.exercise.jobsity.data.api.Status
import com.exercise.jobsity.domain.model.Episode
import com.exercise.jobsity.domain.model.Season
import com.exercise.jobsity.domain.model.Show
import com.exercise.jobsity.domain.usecase.*
import com.exercise.jobsity.presentation.fragment.home.HomeViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ShowViewModelTest {

    @MockK
    lateinit var getSeasonUseCase: GetSeasonsUseCase

    @MockK
    lateinit var getSeasonEpisodesUseCase: GetSeasonEpisodesUseCase

    @MockK
    lateinit var getIfShowIsFavoriteUseCase: GetIfShowIsFavoriteUseCase

    @MockK
    lateinit var setFavoriteShowUseCase: SetFavoriteShowUseCase

    @MockK
    lateinit var removeFavoriteShowUseCase: RemoveFavoriteShowUseCase

    @RelaxedMockK
    private lateinit var isFavoriteObservable: Observer<Boolean>

    @RelaxedMockK
    private lateinit var loadingObserver: Observer<Boolean>

    @RelaxedMockK
    private lateinit var errorObserver: Observer<String>

    @RelaxedMockK
    private lateinit var seasonsObservable: Observer<List<Season>>

    @RelaxedMockK
    private lateinit var episodesObservable: Observer<List<Episode>>

    lateinit var showViewModel: ShowViewModel

    private val emptyResourceShowList = Resource(Status.SUCCESS, null, "")
    private val show = Show(1, "", "", "", listOf(""), null, null, "", "", "", 60)
    private val resourceShowList: Resource<List<Show>> = Resource(Status.SUCCESS, listOf(show), "")
    private val errorEpisodeRequest: Resource<List<Episode>> = Resource(Status.ERROR, null, "erro")
    private val errorSeasonRequest: Resource<List<Season>> = Resource(Status.ERROR, null, "erro")
    private val season : Season = mockk()

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        showViewModel = ShowViewModel(
            getSeasonUseCase,
            getSeasonEpisodesUseCase,
            getIfShowIsFavoriteUseCase,
            setFavoriteShowUseCase,
            removeFavoriteShowUseCase
        )
        every { isFavoriteObservable.onChanged(any()) } answers {}
        every { loadingObserver.onChanged(any()) } answers {}
        every { episodesObservable.onChanged(any()) } answers {}
        every { seasonsObservable.onChanged(any()) } answers {}
        every { errorObserver.onChanged(any()) } answers {}

        showViewModel.getIsFavoriteObservable().observeForever(isFavoriteObservable)
        showViewModel.getLoadingLiveData().observeForever(loadingObserver)
        showViewModel.getEpisodeLiveData().observeForever(episodesObservable)
        showViewModel.getSeasonLiveData().observeForever(seasonsObservable)
        showViewModel.getErrorLiveData().observeForever(errorObserver)
    }

    @Test
    fun `test set selected show is calling observers correctly test case 1`() = runBlocking {
        coEvery { getIfShowIsFavoriteUseCase.execute(show) } coAnswers { Resource(Status.SUCCESS, true, "") }
        coEvery { getSeasonUseCase.execute(show.id!!) } coAnswers { Resource(Status.SUCCESS, listOf(season), "") }
        showViewModel.setSelectedShow(show)
        verify { isFavoriteObservable.onChanged(true) }
        verify { seasonsObservable.onChanged(listOf(season)) }
    }

    @Test
    fun `test set selected show is calling observers correctly test case 2`() = runBlocking {
        coEvery { getIfShowIsFavoriteUseCase.execute(show) } coAnswers { Resource(Status.SUCCESS, false, "") }
        coEvery { getSeasonUseCase.execute(show.id!!) } coAnswers { Resource(Status.SUCCESS, null, "") }
        showViewModel.setSelectedShow(show)
        verify { isFavoriteObservable.onChanged(false) }
        verify(exactly = 0) { seasonsObservable.onChanged(null) }
    }

    @Test
    fun `test removeFavorite`(){
        coEvery { removeFavoriteShowUseCase.execute(show) } just runs
        showViewModel.removeFavorite(show)
        verify { isFavoriteObservable.onChanged(false) }
    }

    @Test
    fun `test setFavorite`(){
        coEvery { setFavoriteShowUseCase.execute(show) } just runs
        showViewModel.setFavorite(show)
        verify { isFavoriteObservable.onChanged(true) }
    }

    @Test
    fun `test seasonSelected`() {
        coEvery { getSeasonEpisodesUseCase.execute(any()) } coAnswers { errorEpisodeRequest }
        coEvery { season.episodeOrder } coAnswers { 1 }
        coEvery { season.id } coAnswers { 1 }
        showViewModel.seasonSelected(season)
        verify {
            loadingObserver.onChanged(true)
            errorObserver.onChanged(errorEpisodeRequest.message)
            isFavoriteObservable.onChanged(false)
        }
    }
}