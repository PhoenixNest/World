package io.dev.android.game

import io.dev.android.game.test.coroutines.CoroutinesTest
import io.dev.android.game.test.viewmodel.ViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testWithViewModel() {
        ViewModelTest().run {
            gameViewModel_Initialization_FirstWordLoaded()
            gameViewModel_IncorrectGuess_ErrorFlagSet()
            gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset()
            gameViewModel_WordSkipped_ScoreUnchangedAndWordCountIncreased()
            gameViewModel_AllWordsGuessed_UiStateUpdatedCorrectly()
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun testWithCoroutine() = runTest {
        CoroutinesTest().run {
            raceParticipant_RaceStarted_ProgressUpdated()
            raceParticipant_RaceFinished_ProgressUpdated()
            raceParticipant_RacePaused_ProgressUpdated()
            raceParticipant_RacePausedAndResumed_ProgressUpdated()
            raceParticipant_ProgressIncrementZero_ExceptionThrown()
            raceParticipant_MaxProgressZero_ExceptionThrown()
        }
    }
}