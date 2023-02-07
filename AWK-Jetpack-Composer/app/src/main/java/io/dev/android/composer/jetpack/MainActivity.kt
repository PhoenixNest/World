package io.dev.android.composer.jetpack

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import io.dev.android.composer.jetpack.model.AnimTestModel
import io.dev.android.composer.jetpack.model.home.FavouriteModel
import io.dev.android.composer.jetpack.model.home.TrendingModel
import io.dev.android.composer.jetpack.ui.chat.ChatPage
import io.dev.android.composer.jetpack.ui.home.HomePage
import io.dev.android.composer.jetpack.ui.home.MyBottomNavigationBar
import io.dev.android.composer.jetpack.ui.home.anim_list.AnimList
import io.dev.android.composer.jetpack.ui.theme.AWKJetpackComposerTheme
import io.dev.android.composer.jetpack.ui.welcome.WelcomePage
import io.dev.android.composer.jetpack.viewmodel.MainViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialization()
    }

    private fun initialization() {
        prepareData()
        setupUi()
    }

    private fun prepareData() {
        getMessageData()
    }

    private fun getMessageData() {
        viewModel.fetchMessageData()
    }

    private fun setupUi() {
        setContent {
            MyApp()
        }
    }

    @Composable
    private fun MyApp() {
        AWKJetpackComposerTheme {
            Chapter3BasicLayoutsInCompose()
        }
    }

    @Composable
    private fun Chapter1ComposeTutorial() {
        // Reference Link: [Compose Tutorial](https://developer.android.google.cn/jetpack/compose/tutorial)
        ChatPage(viewModel.messageData)
    }

    @Composable
    private fun Chapter2JetpackComposeBasics() {
        // Reference Link: [Jetpack Compose basics](https://developer.android.com/codelabs/jetpack-compose-basics)
        var shouldShowWelcomePage by rememberSaveable {
            mutableStateOf(true)
        }

        if (shouldShowWelcomePage) {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                WelcomePage(onClick = {
                    shouldShowWelcomePage = false
                })
            }
        } else {
            AnimList(listData = AnimTestModel.testAnimData())
        }
    }

    @Composable
    private fun Chapter3BasicLayoutsInCompose() {
        // Reference Link: [Basic layouts in Compose](https://developer.android.google.cn/codelabs/jetpack-compose-layouts)
        Scaffold(
            bottomBar = { MyBottomNavigationBar() }
        ) { padding: PaddingValues ->
            // A surface container using the 'background' color from the theme
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                // Avoid IDE warning
                Log.d(TAG, "MyApp paddingValue: $padding")
                HomePage(
                    trendingListData = TrendingModel.testTrendingList,
                    favouriteListData = FavouriteModel.testFavouriteList,
                    animListData = AnimTestModel.testAnimData(),
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}