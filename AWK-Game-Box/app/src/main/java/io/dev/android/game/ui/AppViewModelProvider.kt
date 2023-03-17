package io.dev.android.game.ui

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.dev.android.game.MyApplication
import io.dev.android.game.data.datastore.DataStoreRepository
import io.dev.android.game.ui.block_2048.viewmodel.Block2048ViewModel
import io.dev.android.game.ui.home.viewmodel.HomeViewModel
import io.dev.android.game.ui.one_line_finish.viewmodel.OneLineFinishViewModel
import io.dev.android.game.ui.unscramble_the_word.viewmodel.UnscrambleViewModel

object AppViewModelProvider {

    private val myApplication = MyApplication.getInstance()

    val Factory = viewModelFactory {
        // Home ViewModel
        initializer {
            HomeViewModel(myApplication)
        }

        // Block 2048 ViewModel
        initializer {
            Block2048ViewModel(
                myApplication,
                DataStoreRepository(myApplication.applicationContext)
            )
        }

        // Unscramble the word ViewModel
        initializer {
            UnscrambleViewModel(myApplication)
        }

        // One line finish ViewModel
        initializer {
            OneLineFinishViewModel(myApplication)
        }

    }

}