package io.dev.relic.feature.pages.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.navOptions
import io.common.RelicConstants.Common.EMPTY_STRING
import io.common.util.LogUtil
import io.core.ui.RelicUiUtil
import io.core.ui.RelicUiUtil.DEFAULT_BOTTOM_NAVIGATION_BAR_HEIGHT
import io.core.ui.RelicUiUtil.DEFAULT_RAIL_BAR_WIDTH
import io.core.ui.RelicUiUtil.DEFAULT_SHEET_DRAG_HANDLE_HEIGHT
import io.data.util.NewsCategory
import io.data.util.NewsConfig.DEFAULT_INIT_NEWS_PAGE_INDEX
import io.data.util.NewsConfig.DEFAULT_INIT_NEWS_PAGE_SIZE
import io.data.util.NewsConfig.TopHeadline.DEFAULT_NEWS_COUNTRY_TYPE
import io.dev.relic.feature.function.agent.AgentQuestionModel
import io.dev.relic.feature.function.agent.gemini.vm.GeminiAgentViewModel
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.function.news.TopHeadlineNewsDataState
import io.dev.relic.feature.function.news.TrendingNewsDataState
import io.dev.relic.feature.pages.agent.navigateToAgentChatPage
import io.dev.relic.feature.pages.detail.food_recipe.navigateToFoodRecipeDetailPage
import io.dev.relic.feature.pages.detail.news.navigateToNewsDetailPage
import io.dev.relic.feature.pages.home.ui.HomeExpendSidePanel
import io.dev.relic.feature.pages.home.ui.HomePageBottomSheet
import io.dev.relic.feature.pages.home.ui.HomePageContent
import io.dev.relic.feature.pages.home.ui.HomePageDrawer
import io.dev.relic.feature.pages.home.ui.widget.HomeFeaturePanelAction
import io.dev.relic.feature.pages.home.ui.widget.HomeNewsTabBarAction
import io.dev.relic.feature.pages.home.ui.widget.HomeQuickPromptsPanelAction
import io.dev.relic.feature.pages.home.ui.widget.HomeRecommendFoodsPanelAction
import io.dev.relic.feature.pages.home.ui.widget.HomeTopBar
import io.dev.relic.feature.pages.home.ui.widget.HomeTopBarAction
import io.dev.relic.feature.pages.home.ui.widget.HomeTopHeadlineNewsColumnAction
import io.dev.relic.feature.pages.home.ui.widget.HomeTrendingNewsRowAction
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
                    val isCompactMode = (mainScreenState.windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact)
                    val scrollItemIndex = if (isCompactMode) 2 else 1
                    topHeadlineNewsListState.animateScrollToItem(scrollItemIndex)
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
        onRetryClick = {
            newsViewModel.getTopHeadlineNewsData()
        },
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
    val isCompactMode = (windowsSizeClass.widthSizeClass == WindowWidthSizeClass.Compact)

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.fillMaxWidth(),
        gesturesEnabled = true,
        drawerContent = {
            HomePageDrawer()
        },
        scrimColor = MaterialTheme.colorScheme.scrim
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            HomeTopBar(action = topBarAction)
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isCompactMode) {
                    HomePageCompatModeContent(
                        featurePanelAction = featurePanelAction,
                        quickPromptsPanelAction = quickPromptsPanelAction,
                        recommendFoodsPanelAction = recommendFoodsPanelAction,
                        trendingNewsRowAction = trendingNewsRowAction,
                        newsTabBarAction = newsTabBarAction,
                        topHeadlineNewsColumnAction = topHeadlineNewsColumnAction
                    )
                } else {
                    HomePageExpendModeContent(
                        featurePanelAction = featurePanelAction,
                        quickPromptsPanelAction = quickPromptsPanelAction,
                        recommendFoodsPanelAction = recommendFoodsPanelAction,
                        trendingNewsRowAction = trendingNewsRowAction,
                        newsTabBarAction = newsTabBarAction,
                        topHeadlineNewsColumnAction = topHeadlineNewsColumnAction
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomePageCompatModeContent(
    featurePanelAction: HomeFeaturePanelAction,
    quickPromptsPanelAction: HomeQuickPromptsPanelAction,
    recommendFoodsPanelAction: HomeRecommendFoodsPanelAction,
    trendingNewsRowAction: HomeTrendingNewsRowAction,
    newsTabBarAction: HomeNewsTabBarAction,
    topHeadlineNewsColumnAction: HomeTopHeadlineNewsColumnAction
) {
    val gestureBarHeight = NavigationBarDefaults.windowInsets.asPaddingValues().calculateBottomPadding()
    val sheetPeakHeight = DEFAULT_SHEET_DRAG_HANDLE_HEIGHT + DEFAULT_BOTTOM_NAVIGATION_BAR_HEIGHT + gestureBarHeight
    val sheetContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
    val sheetContentColor = MaterialTheme.colorScheme.onSurface

    BottomSheetScaffold(
        sheetContent = {
            HomePageBottomSheet(
                trendingNewsRowAction = trendingNewsRowAction,
                newsTabBarAction = newsTabBarAction,
                topHeadlineNewsColumnAction = topHeadlineNewsColumnAction
            )
        },
        modifier = Modifier,
        scaffoldState = rememberBottomSheetScaffoldState(),
        sheetPeekHeight = sheetPeakHeight,
        sheetShape = RectangleShape,
        sheetContainerColor = sheetContainerColor,
        sheetContentColor = sheetContentColor
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface
        ) {
            HomePageContent(
                featurePanelAction = featurePanelAction,
                quickPromptsPanelAction = quickPromptsPanelAction,
                recommendFoodsPanelAction = recommendFoodsPanelAction
            )
        }
    }
}

@Composable
private fun HomePageExpendModeContent(
    featurePanelAction: HomeFeaturePanelAction,
    quickPromptsPanelAction: HomeQuickPromptsPanelAction,
    recommendFoodsPanelAction: HomeRecommendFoodsPanelAction,
    trendingNewsRowAction: HomeTrendingNewsRowAction,
    newsTabBarAction: HomeNewsTabBarAction,
    topHeadlineNewsColumnAction: HomeTopHeadlineNewsColumnAction
) {
    val currentScreenWidthDp = RelicUiUtil.getCurrentScreenWidthDp()
    val pageContentMaxWidth = (currentScreenWidthDp - DEFAULT_RAIL_BAR_WIDTH) / 2

    // Left Panel
    Surface(
        modifier = Modifier.width(pageContentMaxWidth),
        color = MaterialTheme.colorScheme.surface
    ) {
        HomePageContent(
            featurePanelAction = featurePanelAction,
            quickPromptsPanelAction = quickPromptsPanelAction,
            recommendFoodsPanelAction = recommendFoodsPanelAction
        )
    }
    // Right Panel
    Surface(
        modifier = Modifier.width(pageContentMaxWidth),
        color = MaterialTheme.colorScheme.surface
    ) {
        HomeExpendSidePanel(
            trendingNewsRowAction = trendingNewsRowAction,
            newsTabBarAction = newsTabBarAction,
            topHeadlineNewsColumnAction = topHeadlineNewsColumnAction
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun HomePageCompatModeContentPreview() {
    HomePageCompatModeContent(
        featurePanelAction = HomeFeaturePanelAction(
            onAgentClick = {},
            onFoodRecipesClick = {},
            onTodoClick = {}
        ),
        quickPromptsPanelAction = HomeQuickPromptsPanelAction(
            questions = AgentQuestionModel.getRandomQuestions(),
            onRefreshClick = {},
            onPromptClick = {}
        ),
        recommendFoodsPanelAction = HomeRecommendFoodsPanelAction(
            state = FoodRecipesDataState.Init,
            onRefreshClick = {},
            onItemClick = {}
        ),
        trendingNewsRowAction = HomeTrendingNewsRowAction(
            state = TrendingNewsDataState.Fetching,
            lazyListState = rememberLazyListState(),
            onRetryClick = {},
            onItemClick = {}
        ),
        newsTabBarAction = HomeNewsTabBarAction(
            currentSelectedTab = 0,
            lazyListState = rememberLazyListState(),
            onTabItemClick = { _, _ -> }
        ),
        topHeadlineNewsColumnAction = HomeTopHeadlineNewsColumnAction(
            state = TopHeadlineNewsDataState.Fetching,
            lazyListState = rememberLazyListState(),
            onItemClick = {},
            onRetryClick = {},
            onScrollToTopClick = {}
        )
    )
}

@Composable
@Preview(showBackground = true, device = "id:pixel_fold")
private fun HomePageExpendModeContentPreview() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HomePageExpendModeContent(
            featurePanelAction = HomeFeaturePanelAction(
                onAgentClick = {},
                onFoodRecipesClick = {},
                onTodoClick = {}
            ),
            quickPromptsPanelAction = HomeQuickPromptsPanelAction(
                questions = AgentQuestionModel.getRandomQuestions(),
                onRefreshClick = {},
                onPromptClick = {}
            ),
            recommendFoodsPanelAction = HomeRecommendFoodsPanelAction(
                state = FoodRecipesDataState.Init,
                onRefreshClick = {},
                onItemClick = {}
            ),
            trendingNewsRowAction = HomeTrendingNewsRowAction(
                state = TrendingNewsDataState.Fetching,
                lazyListState = rememberLazyListState(),
                onRetryClick = {},
                onItemClick = {}
            ),
            newsTabBarAction = HomeNewsTabBarAction(
                currentSelectedTab = 0,
                lazyListState = rememberLazyListState(),
                onTabItemClick = { _, _ -> }
            ),
            topHeadlineNewsColumnAction = HomeTopHeadlineNewsColumnAction(
                state = TopHeadlineNewsDataState.Fetching,
                lazyListState = rememberLazyListState(),
                onItemClick = {},
                onRetryClick = {},
                onScrollToTopClick = {}
            )
        )
    }
}