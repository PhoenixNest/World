package io.dev.android.composer.jetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import io.dev.android.composer.jetpack.ui.home.MyBottomNavigationBar
import io.dev.android.composer.jetpack.ui.home.anim_list.AnimList
import io.dev.android.composer.jetpack.ui.theme.AWKJetpackComposerTheme
import io.dev.android.composer.jetpack.ui.welcome.WelcomePage
import io.dev.android.composer.jetpack.viewmodel.MainViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

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
        var shouldShowWelcomePage by rememberSaveable {
            mutableStateOf(true)
        }

        AWKJetpackComposerTheme {
            if (shouldShowWelcomePage) {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    WelcomePage(onClick = {
                        shouldShowWelcomePage = false
                    })
                }
            } else {
                Scaffold(
                    bottomBar = { MyBottomNavigationBar() }
                ) { padding ->
                    // A surface container using the 'background' color from the theme
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                        // ChatPage(viewModel.messageData)

                        /*HomePage(
                            trendingListData = TrendingModel.testTrendingList,
                            favouriteListData = FavouriteModel.testFavouriteList,
                            animListData = AnimTestModel.testAnimData(),
                            modifier = Modifier.padding(padding)
                        )*/

                        AnimList(
                            listData = AnimTestModel.testAnimData(),
                            modifier = Modifier.padding(padding)
                        )
                    }
                }
            }
        }
    }
}