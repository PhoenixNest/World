package io.dev.android.composer.notes.feature.note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import io.dev.android.composer.notes.feature.note.presentation.add_edit_note.AddEditNoteScreen
import io.dev.android.composer.notes.feature.note.presentation.add_edit_note.viewmodel.AddEditNoteViewModel
import io.dev.android.composer.notes.feature.note.presentation.notes.NoteScreen
import io.dev.android.composer.notes.feature.note.presentation.notes.viewmodel.NotesViewModel
import io.dev.android.composer.notes.feature.note.presentation.util.Screen
import io.dev.android.composer.notes.ui.theme.AWKNotesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val noteViewModel: NotesViewModel by viewModels()
        val noteAddEditViewModel: AddEditNoteViewModel by viewModels()

        setContent {
            AWKNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NoteScreen.route
                    ) {
                        composable(
                            route = Screen.NoteScreen.route
                        ) {
                            NoteScreen(
                                navController = navController,
                                viewModel = noteViewModel
                            )
                        }

                        composable(
                            route = "${Screen.AddEditNoteScreen.route}?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(name = "noteId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(name = "noteColor") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            val color = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = color,
                                viewModel = noteAddEditViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}