package com.technologies.androidassessment.flight_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.technologies.androidassessment.core.data.entity.Airport
import com.technologies.androidassessment.core.extension.getOrAwaitValue
import com.technologies.androidassessment.core.network.flight.FlightRepository
import com.technologies.androidassessment.core.network.fligth_rx.FlightRepositoryRx
import com.technologies.androidassessment.feature.dashboard.airport_list.AirportsViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import junit.framework.Assert
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class AirportsViewModelTest {

    @MockK
    lateinit var airportsViewModel: AirportsViewModel

    @MockK(relaxed = true)
    lateinit var flightRepository: FlightRepository

    @MockK(relaxed = true)
    lateinit var flightRepositoryRx: FlightRepositoryRx

    @MockK(relaxed = true)
    lateinit var airportsObserver: Observer<List<Airport>>

    @MockK(relaxed = true)
    lateinit var loadingObserver: Observer<Boolean>

    @MockK(relaxed = true)
    lateinit var errorObserver: Observer<String>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val immediateScheduler: Scheduler = object : Scheduler() {

        override fun createWorker() = ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)

        // This prevents errors when scheduling a delay
        override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
            return super.scheduleDirect(run, 0, unit)
        }

    }

    @Before
    fun setup() {

        MockKAnnotations.init(this)

        RxJavaPlugins.setIoSchedulerHandler { immediateScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediateScheduler }
        RxAndroidPlugins.setMainThreadSchedulerHandler { immediateScheduler }

        airportsViewModel = AirportsViewModel(
            flightRepository,
            flightRepositoryRx
        )
        airportsViewModel.apply {
            airports.observeForever(airportsObserver)
            loading.observeForever(loadingObserver)
            error.observeForever(errorObserver)
        }
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun `Fetch airport list success`() {
        every { flightRepositoryRx.getAirports() } returns getAirports(false)
        airportsViewModel.run {
            getAirportsRx()

            assertEquals(mockAirports, airports.getOrAwaitValue())

            val loadingCapture = slot<Boolean>()

            //verify loading was exactly called 3 times (initialization, loading = true then loading = false)
            verify(exactly = 3) {
                loadingObserver.onChanged(
                    capture(loadingCapture)
                )
            }

            assertEquals(false, loadingCapture.captured)

            verify {
                flightRepositoryRx.getAirports()
            }

            confirmVerified(flightRepositoryRx)
            confirmVerified(loadingObserver)
        }
    }

    @Test
    fun `Fetch airport list empty`() {
        every { flightRepositoryRx.getAirports() } returns getAirports(true)
        airportsViewModel.run {
            getAirportsRx()

            assertEquals(true, airports.getOrAwaitValue().isEmpty())

            val loadingCapture = slot<Boolean>()

            //verify loading was exactly called 3 times (initialization, loading = true then loading = false)
            verify(exactly = 3) {
                loadingObserver.onChanged(
                    capture(loadingCapture)
                )
            }

            assertEquals(false, loadingCapture.captured)

            verify {
                flightRepositoryRx.getAirports()
            }

            confirmVerified(flightRepositoryRx)
            confirmVerified(loadingObserver)
        }
    }

    @Test
    fun `Fetch airport list and received exception`() {
        every { flightRepositoryRx.getAirports() } returns getMockException()
        airportsViewModel.run {
            getAirportsRx()

            val loadingCapture = slot<Boolean>()
            val errorCapture = slot<String>()

            assertEquals(true, airports.value == null)

            //verify loading was exactly called 3 times (initialization, loading = true then loading = false)
            verify(exactly = 3) {
                loadingObserver.onChanged(
                    capture(loadingCapture)
                )
            }

            assertEquals(false, loadingCapture.captured)

            //verify error was only called once during the onError of the rxJava subscription call
            verify(exactly = 1) {
                errorObserver.onChanged(
                    capture(errorCapture)
                )
            }

            assertEquals(mockExceptionBodyRaw, errorCapture.captured)

            verify {
                flightRepositoryRx.getAirports()
            }

            confirmVerified(flightRepositoryRx)
            confirmVerified(loadingObserver)
            confirmVerified(errorObserver)
        }
    }
}