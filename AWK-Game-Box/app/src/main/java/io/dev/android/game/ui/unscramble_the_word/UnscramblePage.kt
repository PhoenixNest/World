package io.dev.android.game.ui.unscramble_the_word

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import io.dev.android.game.R
import io.dev.android.game.ui.unscramble_the_word.state.UnscrambleUiState
import io.dev.android.game.ui.unscramble_the_word.viewmodel.UnscrambleViewModel

object UnscramblePage {

    @Composable
    fun GamePage(
        modifier: Modifier,
        viewModel: UnscrambleViewModel = viewModel()
    ) {
        val gameUiState by viewModel.uiState.collectAsState()

        GameOverDialog(gameUiState, {
            viewModel.resetGame()
        })

        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GameStatusBar(
                wordCount = gameUiState.currentWordCount,
                currentScore = gameUiState.currentScore
            )

            GameLayout(
                currentScrambleWord = gameUiState.currentScrambledWord,
                isGuessWrong = gameUiState.isGuessedWordWrong,
                userInput = viewModel.userInputWord,
                onUserInputChanged = { newValue ->
                    viewModel.updateUserInput(newValue)
                },
                onUserInputDone = {
                    viewModel.checkUserInputResult()
                },
            )

            GameButtonBar(
                onSkip = {
                    viewModel.skipWord()
                },
                onSubmit = {
                    viewModel.checkUserInputResult()
                }
            )
        }
    }

    @Composable
    fun GameStatusBar(
        wordCount: Int,
        currentScore: Int,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .size(48.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.word_count, wordCount),
                fontSize = 18.sp
            )

            Text(
                text = stringResource(id = R.string.current_score, currentScore),
                fontSize = 18.sp
            )
        }
    }

    @Composable
    fun GameLayout(
        currentScrambleWord: String,
        isGuessWrong: Boolean,
        userInput: String,
        onUserInputChanged: (String) -> Unit,
        onUserInputDone: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = currentScrambleWord,
                fontSize = 36.sp,
                modifier = modifier.align(alignment = Alignment.CenterHorizontally)
            )

            Text(
                text = stringResource(id = R.string.unscramble_intro),
                fontSize = 16.sp,
                modifier = modifier.align(alignment = Alignment.CenterHorizontally)
            )

            OutlinedTextField(
                value = userInput,
                onValueChange = onUserInputChanged,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    if (isGuessWrong) {
                        Text(text = stringResource(id = R.string.wrong_guess))
                    } else {
                        Text(text = stringResource(id = R.string.enter_your_word))
                    }
                },
                isError = isGuessWrong,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onUserInputDone()
                    }
                )
            )
        }
    }

    @Composable
    fun GameButtonBar(
        modifier: Modifier = Modifier,
        onSkip: () -> Unit = {},
        onSubmit: () -> Unit = {}
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Skip button
            OutlinedButton(
                onClick = onSkip,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(text = stringResource(id = R.string.skip))
            }

            // Submit button
            Button(
                onClick = onSubmit,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(text = stringResource(id = R.string.submit))
            }
        }
    }

    @Composable
    private fun GameOverDialog(
        uiState: UnscrambleUiState,
        onPlayAgain: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        val navController = rememberNavController()
        if (uiState.isGameOver) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                },
                modifier = modifier,
                title = {
                    Text(text = stringResource(id = R.string.congratulations))
                },
                text = {
                    Text(text = stringResource(id = R.string.you_scored, uiState.currentScore))
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            // navController.navigate(R.id.action_unscrambleTheWordFragment_to_homeFragment)
                        }
                    ) {
                        Text(text = stringResource(id = R.string.exit))
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = onPlayAgain
                    ) {
                        Text(text = stringResource(id = R.string.play_again))
                    }
                }
            )
        }
    }
}