package io.dev.relic.feature.activities.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import dagger.hilt.android.AndroidEntryPoint
import io.common.RelicConstants.IntentAction.INTENT_ACTION_VIEW
import io.core.ui.theme.RelicAppTheme
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.agent.gemini.viewmodel.GeminiAgentViewModel
import io.dev.relic.feature.function.food_recipes.viewmodel.FoodRecipesViewModel
import io.dev.relic.feature.function.gallery.viewmodel.GalleryViewModel
import io.dev.relic.feature.function.news.viewmodel.NewsViewModel
import io.dev.relic.feature.function.todo.viewmodel.TodoViewModel
import io.dev.relic.feature.screens.main.MainScreen
import io.domain.app.AbsBaseActivity

@AndroidEntryPoint
class MainActivity : AbsBaseActivity() {

    /**
     * ViewModel - Main
     * */
    private val mainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    /**
     * ViewModel - Agent
     * */
    private val geminiAgentViewModel by lazy {
        ViewModelProvider(this)[GeminiAgentViewModel::class.java]
    }

    /**
     * ViewModel - Food Recipes
     * */
    private val foodRecipesViewModel by lazy {
        ViewModelProvider(this)[FoodRecipesViewModel::class.java]
    }

    /**
     * ViewModel - Todo
     * */
    private val todoViewModel by lazy {
        ViewModelProvider(this)[TodoViewModel::class.java]
    }

    /**
     * ViewModel - News
     * */
    private val newsViewModel by lazy {
        ViewModelProvider(this)[NewsViewModel::class.java]
    }

    /**
     * ViewModel - Gallery
     * */
    private val galleryViewModel by lazy {
        ViewModelProvider(this)[GalleryViewModel::class.java]
    }

    companion object {
        private const val TAG = "MainActivity"

        fun start(context: Context) {
            context.startActivity(
                Intent(
                    /* packageContext = */ context,
                    /* cls = */ MainActivity::class.java
                ).apply {
                    action = INTENT_ACTION_VIEW
                }
            )
        }
    }

    /* ======================== Logical ======================== */

    override fun initialization(savedInstanceState: Bundle?) {
        setupCoilImageLoader()
    }

    private fun setupCoilImageLoader() {
        val imageLoader = ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(this.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.2)
                    .build()
            }
            .build()
        Coil.setImageLoader(imageLoader)
    }

    /* ======================== Ui ======================== */

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun initUi(savedInstanceState: Bundle?) {
        setContent {

            val isDarkTheme = isSystemInDarkTheme()

            // Update the edge to edge configuration to match the theme
            // This is the same parameters as the default enableEdgeToEdge call, but we manually
            // resolve whether or not to show dark theme using uiState, since it can be different
            // than the configuration's dark theme value based on the user preference.
            DisposableEffect(isDarkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT
                    ) { isDarkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT
                    ) { isDarkTheme },
                )
                onDispose {}
            }

            // A surface container using the 'background' color from the theme
            RelicAppTheme {
                Surface(
                    tonalElevation = 5.dp,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Unspecified)
                        .imePadding()
                        .navigationBarsPadding()
                ) {
                    MainScreen(
                        savedInstanceState = savedInstanceState,
                        windowSizeClass = calculateWindowSizeClass(activity = this),
                        networkMonitor = networkMonitor,
                        mainViewModel = mainViewModel,
                        geminiAgentViewModel = geminiAgentViewModel,
                        foodRecipesViewModel = foodRecipesViewModel,
                        todoViewModel = todoViewModel,
                        newsViewModel = newsViewModel,
                        galleryViewModel = galleryViewModel
                    )
                }
            }
        }
    }
}