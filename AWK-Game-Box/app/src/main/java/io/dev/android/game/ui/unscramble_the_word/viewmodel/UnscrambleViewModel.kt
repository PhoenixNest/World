package io.dev.android.game.ui.unscramble_the_word.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import io.dev.android.game.ui.unscramble_the_word.state.UnscrambleUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class UnscrambleViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    /**
     * Ui State of the Unscramble Game
     * */
    private val _uiState = MutableStateFlow(UnscrambleUiState())
    val uiState: StateFlow<UnscrambleUiState> = _uiState.asStateFlow()

    /**
     * Indicate the input word of user
     * */
    var userInputWord by mutableStateOf("")

    /**
     * Indicate the used word of game
     * */
    private var usedWords: MutableSet<String> = mutableSetOf()
    private lateinit var currentWord: String

    init {
        resetGame()
    }

    /**
     * Re-initializes the game data to restart the game.
     */
    fun resetGame() {
        usedWords.clear()
        _uiState.value = UnscrambleUiState(currentScrambledWord = pickWordAndShuffle())
    }

    /**
     * Update the user's guess
     */
    fun updateUserInput(word: String) {
        userInputWord = word
    }

    /**
     * Checks if the user's guess is correct.
     * Increases the score accordingly.
     */
    fun checkUserInputResult() {
        if (userInputWord.equals(currentWord, ignoreCase = true)) {
            // User's guess is correct, increase the score
            // and call updateGameState() to prepare the game for next round
            val updateScore = _uiState.value.currentScore.plus(UnscrambleUiState.SCORE_INCREASE)
            updateGameState(updateScore)
        } else {
            // User's guess is wrong, show an error
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        // Reset the user input value
        updateUserInput("")
    }

    fun skipWord() {
        updateGameState(_uiState.value.currentScore)
        // Reset the user input value
        updateUserInput("")
    }

    /**
     * Picks a new currentWord and currentScrambledWord and updates UiState according to
     * current game state.
     */
    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == UnscrambleUiState.MAX_NO_OF_WORDS) {
            _uiState.update { currentState ->
                // Last round in the game, update isGameOver to true, don't pick a new word
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentScore = updatedScore,
                    isGameOver = true
                )
            }
        } else {
            // Normal round in the game
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentScrambledWord = pickWordAndShuffle(),
                    currentWordCount = currentState.currentWordCount.inc(),
                    currentScore = updatedScore
                )
            }
        }
    }

    private fun pickWordAndShuffle(): String {
        // Continue picking up a new random word until you get one that hasn't been used before
        currentWord = UnscrambleUiState.allWords.random()
        return if (usedWords.contains(currentWord)) {
            pickWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            shuffleCurrentWord(currentWord)
        }
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        tempWord.shuffle()

        // If the current word is still the same as the original word after shuffling, shuffled again
        while (String(tempWord) == word) {
            tempWord.shuffle()
        }

        return String(tempWord)
    }
}