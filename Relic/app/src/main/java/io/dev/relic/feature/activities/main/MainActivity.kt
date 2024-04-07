package io.dev.relic.feature.activities.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import io.common.RelicConstants.IntentAction.INTENT_ACTION_VIEW
import io.core.ui.ext.SystemUiControllerExt.enableImmersiveMode
import io.core.ui.theme.RelicAppTheme
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.agent.gemini.viewmodel.GeminiAgentViewModel
import io.dev.relic.feature.function.food_recipes.viewmodel.FoodRecipesViewModel
import io.dev.relic.feature.function.news.viewmodel.NewsViewModel
import io.dev.relic.feature.function.todo.viewmodel.TodoViewModel
import io.dev.relic.feature.screens.main.MainScreen
import io.domain.app.AbsBaseActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
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
        //
    }

    /* ======================== Ui ======================== */

    override fun initUi(savedInstanceState: Bundle?) {
        setContent {
            // Setup immersive mode.
            rememberSystemUiController().enableImmersiveMode()

            // A surface container using the 'background' color from the theme
            RelicAppTheme {
                Surface(
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
                        newsViewModel = newsViewModel
                    )
                }
            }
        }
    }
}