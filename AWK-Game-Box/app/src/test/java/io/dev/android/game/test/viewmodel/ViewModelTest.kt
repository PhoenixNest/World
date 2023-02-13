package io.dev.android.game.test.viewmodel

import io.dev.android.game.MyApplication
import io.dev.android.game.test.viewmodel.data.getUnscrambledWord
import io.dev.android.game.ui.unscramble_the_word.state.UnscrambleUiState
import io.dev.android.game.ui.unscramble_the_word.viewmodel.UnscrambleViewModel
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Test

class ViewModelTest {

    private val viewModel = UnscrambleViewModel(application = MyApplication.getInstance())

    companion object {
        private const val SCORE_AFTER_FIRST_CORRECT_ANSWER = UnscrambleUiState.SCORE_INCREASE
    }

    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {
        /**
         *  Warning: This way to retrieve the uiState works because MutableStateFlow is used. In the
         *  upcoming units you will learn about advanced usages of StateFlow that creates a stream
         *  of data and you need to react to handle the stream. For those scenarios you will write
         *  unit tests using different methods/approaches. This applies to all the usages of
         *  viewModel.uiState.value in this class.
         **/
        val gameUiState = viewModel.uiState.value
        val unScrambledWord = getUnscrambledWord(gameUiState.currentScrambledWord)

        // Assert that current word is scrambled.
        Assert.assertNotEquals(unScrambledWord, gameUiState.currentScrambledWord)
        // Assert that current word count is set to 1.
        Assert.assertTrue(gameUiState.currentWordCount == 1)
        // Assert that initially the score is 0.
        Assert.assertTrue(gameUiState.currentScore == 0)
        // Assert that wrong word guessed is false.
        Assert.assertFalse(gameUiState.isGuessedWordWrong)
        // Assert that game is not over.
        Assert.assertFalse(gameUiState.isGameOver)
    }

    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        // Given an incorrect word as input
        val incorrectPlayerWord = "and"

        viewModel.updateUserInput(incorrectPlayerWord)
        viewModel.checkUserInputResult()

        val currentGameUiState = viewModel.uiState.value
        // Assert that score is unchanged
        Assert.assertEquals(0, currentGameUiState.currentScore)
        // Assert that checkUserGuess() method updates isGuessedWordWrong correctly
        Assert.assertTrue(currentGameUiState.isGuessedWordWrong)
    }

    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        var currentGameUiState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        viewModel.updateUserInput(correctPlayerWord)
        viewModel.checkUserInputResult()
        currentGameUiState = viewModel.uiState.value

        // Assert that checkUserGuess() method updates isGuessedWordWrong is updated correctly.
        Assert.assertFalse(currentGameUiState.isGuessedWordWrong)
        // Assert that score is updated correctly.
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.currentScore)
    }

    @Test
    fun gameViewModel_WordSkipped_ScoreUnchangedAndWordCountIncreased() {
        var currentGameUiState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        viewModel.updateUserInput(correctPlayerWord)
        viewModel.checkUserInputResult()
        currentGameUiState = viewModel.uiState.value
        val lastWordCount = currentGameUiState.currentWordCount

        viewModel.skipWord()
        currentGameUiState = viewModel.uiState.value
        // Assert that score remains unchanged after word is skipped.
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.currentScore)
        // Assert that word count is increased by 1 after word is skipped.
        assertEquals(lastWordCount + 1, currentGameUiState.currentWordCount)
    }

    @Test
    fun gameViewModel_AllWordsGuessed_UiStateUpdatedCorrectly() {
        var expectedScore = 0
        var currentGameUiState = viewModel.uiState.value
        var correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        repeat(UnscrambleUiState.MAX_NO_OF_WORDS) {
            expectedScore += UnscrambleUiState.SCORE_INCREASE
            viewModel.updateUserInput(correctPlayerWord)
            viewModel.checkUserInputResult()
            currentGameUiState = viewModel.uiState.value
            correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
            // Assert that after each correct answer, score is updated correctly.
            Assert.assertEquals(expectedScore, currentGameUiState.currentScore)
        }
        // Assert that after all questions are answered, the current word count is up-to-date.
        assertEquals(UnscrambleUiState.MAX_NO_OF_WORDS, currentGameUiState.currentWordCount)
        // Assert that after 10 questions are answered, the game is over.
        Assert.assertTrue(currentGameUiState.isGameOver)
    }

}