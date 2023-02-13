package io.dev.android.game.ui.unscramble_the_word

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.dev.android.game.R

@Preview(showBackground = true)
@Composable
fun UnscrambleGameStatusBarPreview() {
    UnscramblePage.GameStatusBar(
        wordCount = 10,
        currentScore = 100
    )
}

@Preview(showBackground = true)
@Composable
fun UnscrambleGameLayoutPreview() {
    UnscramblePage.GameLayout(
        currentScrambleWord = "Hello",
        isGuessWrong = false,
        userInput = "",
        onUserInputChanged = {
            //
        },
        onUserInputDone = {
            //
        },
    )
}

@Preview(showBackground = true)
@Composable
fun UnscrambleGameButtonBarPreview() {
    UnscramblePage.GameButtonBar(
        onSkip = {
            //
        },
        onSubmit = {
            //
        }
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun UnscramblePagePreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        UnscrambleGameStatusBarPreview()
        UnscrambleGameLayoutPreview()
        UnscrambleGameButtonBarPreview()
    }
}

@Preview(showBackground = true)
@Composable
fun UnscrambleGameOverDialogPreview() {
    AlertDialog(
        onDismissRequest = {

        },
        modifier = Modifier,
        title = {
            Text(text = stringResource(id = R.string.congratulations))
        },
        text = {
            Text(text = stringResource(id = R.string.you_scored, 100))
        },
        dismissButton = {
            TextButton(
                onClick = {

                }
            ) {
                Text(text = stringResource(id = R.string.exit))
            }
        },
        confirmButton = {
            TextButton(
                onClick = {

                }
            ) {
                Text(text = stringResource(id = R.string.play_again))
            }
        }
    )
}