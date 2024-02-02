package io.dev.relic.feature.pages.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.core.database.repository.RelicDatabaseRepository
import io.domain.use_case.maxim.MaximUseCase
import io.domain.use_case.todo.TodoUseCase
import io.domain.use_case.wallpaper.WallpaperUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val databaseRepository: RelicDatabaseRepository,
    private val todoUseCase: TodoUseCase,
    private val wallpaperUseCase: WallpaperUseCase,
    private val maximUseCase: MaximUseCase
) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "HomeViewModel"
    }

}