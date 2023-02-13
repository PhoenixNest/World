package io.dev.android.game.test.coroutines

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutinesTest {

    internal data class RaceParticipant(
        val name: String,
        val maxProgress: Int = 100,
        val progressDelayMillis: Long = 500L,
        private val progressIncrement: Int = 1,
        private val initialProgress: Int = 0
    ) {

        init {
            require(maxProgress > 0) { "maxProgress=$maxProgress; must be > 0" }
            require(progressIncrement > 0) { "progressIncrement=$progressIncrement; must be > 0" }
        }

        /**
         * Indicates the race participant's current progress
         */
        var currentProgress by mutableStateOf(initialProgress)
            private set

        /**
         * Updates the value of [currentProgress] by value [progressIncrement] until it reaches
         * [maxProgress]. There is a delay of [progressDelayMillis] between each update.
         */
        suspend fun run() {
            while (currentProgress < maxProgress) {
                delay(progressDelayMillis)
                currentProgress += progressIncrement
            }
        }

        /**
         * Regardless of the value of [initialProgress] the reset function will reset the
         * [currentProgress] to 0
         */
        fun reset() {
            currentProgress = 0
        }
    }

    private val raceParticipant = RaceParticipant(
        name = "Test",
        maxProgress = 100,
        progressDelayMillis = 500L,
        initialProgress = 0,
        progressIncrement = 1
    )

    @Test
    fun raceParticipant_RaceStarted_ProgressUpdated() = runTest {
        val expectedProgress = 1
        launch { raceParticipant.run() }
        advanceTimeBy(raceParticipant.progressDelayMillis)
        runCurrent()
        Assert.assertEquals(expectedProgress, raceParticipant.currentProgress)
    }

    @Test
    fun raceParticipant_RaceFinished_ProgressUpdated() = runTest {
        launch { raceParticipant.run() }

        advanceTimeBy(raceParticipant.maxProgress * raceParticipant.progressDelayMillis)
        runCurrent()
        Assert.assertEquals(100, raceParticipant.currentProgress)
    }

    @Test
    fun raceParticipant_RacePaused_ProgressUpdated() = runTest {
        val expectedProgress = 5
        val racerJob = launch { raceParticipant.run() }
        advanceTimeBy(expectedProgress * raceParticipant.progressDelayMillis)
        runCurrent()
        racerJob.cancelAndJoin()
        Assert.assertEquals(expectedProgress, raceParticipant.currentProgress)
    }

    @Test
    fun raceParticipant_RacePausedAndResumed_ProgressUpdated() = runTest {
        val expectedProgress = 5

        repeat(2) {
            val racerJob = launch { raceParticipant.run() }
            advanceTimeBy(expectedProgress * raceParticipant.progressDelayMillis)
            runCurrent()
            racerJob.cancelAndJoin()
        }

        Assert.assertEquals(expectedProgress * 2, raceParticipant.currentProgress)
    }

    @Test(expected = IllegalArgumentException::class)
    fun raceParticipant_ProgressIncrementZero_ExceptionThrown() = runTest {
        RaceParticipant(name = "Progress Test", progressIncrement = 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun raceParticipant_MaxProgressZero_ExceptionThrown() {
        RaceParticipant(name = "Progress Test", maxProgress = 0)
    }
}