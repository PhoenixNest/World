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
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import io.dev.android.composer.jetpack.model.home.FavouriteModel
import io.dev.android.composer.jetpack.model.home.TrendingModel
import io.dev.android.composer.jetpack.ui.home.HomePage
import io.dev.android.composer.jetpack.ui.home.MyBottomNavigationBar
import io.dev.android.composer.jetpack.ui.theme.AWKJetpackComposerTheme
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
            AWKJetpackComposerTheme {
                Scaffold(
                    bottomBar = { MyBottomNavigationBar() }
                ) { padding ->
                    // A surface container using the 'background' color from the theme
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                        // ChatPage(viewModel.messageData)

                        HomePage(
                            trendingListData = TrendingModel.testTrendingList,
                            favouriteListData = FavouriteModel.testFavouriteList,
                            modifier = Modifier.padding(padding)
                        )
                    }
                }
            }
        }
    }
}