package io.dev.relic.feature.main.unit.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import io.dev.relic.R
import io.dev.relic.feature.main.unit.home.viewmodel.HomeUiState
import io.dev.relic.feature.main.unit.home.viewmodel.HomeViewModel

@Composable
fun HomePageRoute(
    onNavigate: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomePage(
        onNavigate = onNavigate,
        homeUiState = viewModel.homeUiState
    )
}

@Composable
private fun HomePage(
    onNavigate: () -> Unit,
    homeUiState: HomeUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.home_title))
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun HomePagePreview() {
    HomePage(
        onNavigate = {},
        homeUiState = HomeUiState()
    )
}