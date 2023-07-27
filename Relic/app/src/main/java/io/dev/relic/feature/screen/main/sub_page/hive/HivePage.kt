package io.dev.relic.feature.screen.main.sub_page.hive

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.dev.relic.core.data.database.entity.TodoEntity
import io.dev.relic.feature.screen.main.sub_page.hive.viewmodel.HiveViewModel
import io.dev.relic.feature.screen.main.sub_page.hive.widget.HivePageUserPanel

@Composable
fun HivePageRoute(
    onNavigateToMineScreen: () -> Unit,
    hiveViewModel: HiveViewModel = hiltViewModel()
) {
    val todoData: List<TodoEntity> by hiveViewModel.todoData.collectAsStateWithLifecycle()

    HivePage(
        onNavigateToMineScreen = onNavigateToMineScreen
    )
}

@Composable
private fun HivePage(
    onNavigateToMineScreen: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        HivePageUserPanel(onNavigateToMine = onNavigateToMineScreen)
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun HivePagePreview() {
    HivePage(onNavigateToMineScreen = {})
}