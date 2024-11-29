package io.dev.relic.feature.pages.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.navOptions
import io.common.RelicConstants.Common.EMPTY_STRING
import io.common.util.LogUtil
import io.core.ui.utils.RelicUiUtil
import io.core.ui.utils.RelicUiUtil.DEFAULT_NAVIGATION_BAR_HEIGHT
import io.core.ui.utils.RelicUiUtil.DEFAULT_RAIL_BAR_WIDTH
import io.core.ui.utils.RelicUiUtil.DEFAULT_SHEET_DRAG_HANDLE_HEIGHT
import io.data.util.NewsCategory
import io.data.util.NewsConfig.DEFAULT_INIT_NEWS_PAGE_INDEX
import io.data.util.NewsConfig.DEFAULT_INIT_NEWS_PAGE_SIZE
import io.data.util.NewsConfig.TopHeadline.DEFAULT_NEWS_COUNTRY_TYPE
import io.dev.relic.feature.function.agent.gemini.vm.GeminiAgentViewModel
import io.dev.relic.feature.pages.agent.navigateToAgentChatPage
import io.dev.relic.feature.pages.detail.food_recipe.navigateToFoodRecipeDetailPage
import io.dev.relic.feature.pages.detail.news.navigateToNewsDetailPage
import io.dev.relic.feature.pages.home.ui.HomePageBottomSheet
import io.dev.relic.feature.pages.home.ui.HomePageContent
import io.dev.relic.feature.pages.home.ui.HomePageDrawer
import io.dev.relic.feature.pages.home.ui.widget.HomeFeaturePanelAction
import io.dev.relic.feature.pages.home.ui.widget.HomeQuickPromptsPanelAction
import io.dev.relic.feature.pages.home.ui.widget.HomeRecommendFoodsPanelAction
import io.dev.relic.feature.pages.home.ui.widget.HomeTopBarAction
import io.dev.relic.feature.pages.home.ui.widget.bottom_sheet.HomeNewsTabBarAction
import io.dev.relic.feature.pages.home.ui.widget.bottom_sheet.HomeTopHeadlineNewsColumnAction
import io.dev.relic.feature.pages.home.ui.widget.bottom_sheet.HomeTrendingNewsRowAction
import io.dev.relic.feature.pages.home.vm.HomeFoodRecipesViewModel
import io.dev.relic.feature.pages.home.vm.HomeNewsViewModel
import io.dev.relic.feature.pages.settings.navigateToSettingsPage
import io.dev.relic.feature.screens.main.MainScreenState
import kotlinx.coroutines.launch

@Composable
fun HomePageRoute(
    mainScreenState: MainScreenState,
    agentViewModel: GeminiAgentViewModel = hiltViewModel(),
    foodRecipesViewModel: HomeFoodRecipesViewModel = hiltViewModel(),
    newsViewModel: HomeNewsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val navHostController = mainScreenState.navHostController
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed,
        confirmStateChange = {
            LogUtil.d("HomePage", "[Drawer State] $it")
            true
        }
    )

    val questionsList by agentViewModel.getBuiltInQuestionsFlow()
        .collectAsStateWithLifecycle()

    val topHeadlineNewsListState = rememberLazyListState()

    val recommendFoodsDataState by foodRecipesViewModel.getRecommendDataStateFlow()
        .collectAsStateWithLifecycle()

    val trendingNewsDataState by newsViewModel.getTrendingNewsStateFlow()
        .collectAsStateWithLifecycle()

    val topHeadlineNewsDataState by newsViewModel.getTopHeadlineNewsStateFlow()
        .collectAsStateWithLifecycle()

    var lastSelectedTab by remember {
        mutableIntStateOf(0)
    }

    val topBarAction = HomeTopBarAction(
        onOpenDrawerClick = { coroutineScope.launch { drawerState.open() } },
        onSettingClick = { navHostController.navigateToSettingsPage() }
    )

    val featurePanelAction = HomeFeaturePanelAction(
        onAgentClick = { navHostController.navigateToAgentChatPage(null) },
        onFoodRecipesClick = {},
        onTodoClick = {}
    )

    val quickPromptsPanelAction = HomeQuickPromptsPanelAction(
        questions = questionsList,
        onRefreshClick = { agentViewModel.shuffleRandomQuestions() },
        onPromptClick = { prompt ->
            navHostController.navigateToAgentChatPage(prompt)
        }
    )

    val recommendFoodPanelAction = HomeRecommendFoodsPanelAction(
        state = recommendFoodsDataState,
        onRefreshClick = { foodRecipesViewModel.refreshRecommendFoodRecipes() },
        onItemClick = { model ->
            navHostController.navigateToFoodRecipeDetailPage(
                recipeId = model.id ?: -1,
                recipeTitle = model.title,
                navOptions = navOptions {
                    launchSingleTop = true
                    restoreState = true
                }
            )
        }
    )

    val trendingNewsRowAction = HomeTrendingNewsRowAction(
        state = trendingNewsDataState,
        lazyListState = rememberLazyListState(),
        onRetryClick = { newsViewModel.getTrendingNewsData() },
        onItemClick = { model ->
            navHostController.navigateToNewsDetailPage(
                title = model.title,
                contUrl = model.contentUrl
            )
        }
    )

    val newsTabBarAction = HomeNewsTabBarAction(
        currentSelectedTab = newsViewModel.getSelectedNewsTabIndex(),
        lazyListState = rememberLazyListState(),
        onTabItemClick = { currentSelectedTab, _ ->
            newsViewModel.apply {
                if (currentSelectedTab == lastSelectedTab) {
                    return@apply
                }

                lastSelectedTab = currentSelectedTab
                updateSelectedTopHeadlineCategoriesTab(currentSelectedTab)
                getTopHeadlineNewsData(
                    keyWords = EMPTY_STRING,
                    country = DEFAULT_NEWS_COUNTRY_TYPE,
                    category = NewsCategory.entries[currentSelectedTab],
                    pageSize = DEFAULT_INIT_NEWS_PAGE_SIZE,
                    page = DEFAULT_INIT_NEWS_PAGE_INDEX
                )
                coroutineScope.launch {
                    topHeadlineNewsListState.animateScrollToItem(3)
                }
            }
        }
    )

    val topHeadlineNewsColumnAction = HomeTopHeadlineNewsColumnAction(
        state = topHeadlineNewsDataState,
        lazyListState = topHeadlineNewsListState,
        onItemClick = { model ->
            navHostController.navigateToNewsDetailPage(
                title = model.title,
                contUrl = model.contentUrl
            )
        },
        onRetryClick = { newsViewModel.getTopHeadlineNewsData() },
        onScrollToTopClick = {
            coroutineScope.launch {
                topHeadlineNewsListState.animateScrollToItem(0)
            }
        }
    )

    HomePage(
        windowsSizeClass = mainScreenState.windowSizeClass,
        drawerState = drawerState,
        topBarAction = topBarAction,
        featurePanelAction = featurePanelAction,
        quickPromptsPanelAction = quickPromptsPanelAction,
        recommendFoodsPanelAction = recommendFoodPanelAction,
        trendingNewsRowAction = trendingNewsRowAction,
        newsTabBarAction = newsTabBarAction,
        topHeadlineNewsColumnAction = topHeadlineNewsColumnAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomePage(
    windowsSizeClass: WindowSizeClass,
    drawerState: DrawerState,
    topBarAction: HomeTopBarAction,
    featurePanelAction: HomeFeaturePanelAction,
    quickPromptsPanelAction: HomeQuickPromptsPanelAction,
    recommendFoodsPanelAction: HomeRecommendFoodsPanelAction,
    trendingNewsRowAction: HomeTrendingNewsRowAction,
    newsTabBarAction: HomeNewsTabBarAction,
    topHeadlineNewsColumnAction: HomeTopHeadlineNewsColumnAction
) {
    val currentScreenWidthDp = RelicUiUtil.getCurrentScreenWidthDp()
    val sheetMaxWidth = if (windowsSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
        currentScreenWidthDp
    } else {
        currentScreenWidthDp - DEFAULT_RAIL_BAR_WIDTH
    }

    val sheetPeakHeight = DEFAULT_SHEET_DRAG_HANDLE_HEIGHT + DEFAULT_NAVIGATION_BAR_HEIGHT
    val sheetContainerColor = MaterialTheme.colorScheme.surfaceContainer
    val sheetContentColor = MaterialTheme.colorScheme.contentColorFor(sheetContainerColor)

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            HomePageDrawer()
        },
        scrimColor = MaterialTheme.colorScheme.scrim
    ) {
        BottomSheetScaffold(
            sheetContent = {
                HomePageBottomSheet(
                    trendingNewsRowAction = trendingNewsRowAction,
                    newsTabBarAction = newsTabBarAction,
                    topHeadlineNewsColumnAction = topHeadlineNewsColumnAction
                )
            },
            scaffoldState = rememberBottomSheetScaffoldState(),
            sheetPeekHeight = sheetPeakHeight,
            sheetMaxWidth = sheetMaxWidth,
            sheetShape = RectangleShape,
            sheetContainerColor = sheetContainerColor,
            sheetContentColor = sheetContentColor
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.surface
            ) {
                HomePageContent(
                    topBarAction = topBarAction,
                    featurePanelAction = featurePanelAction,
                    quickPromptsPanelAction = quickPromptsPanelAction,
                    recommendFoodsPanelAction = recommendFoodsPanelAction
                )
            }
        }
    }
}