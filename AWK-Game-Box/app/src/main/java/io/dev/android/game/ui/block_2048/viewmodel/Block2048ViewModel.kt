package io.dev.android.game.ui.block_2048.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev.android.game.data.datastore.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Block2048ViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    var currentScore = 0
    var finalScore = 0
    val highestScore: LiveData<Int> = dataStoreRepository.readHighest2048Score().asLiveData()

    companion object {
        private const val TAG = "Block2048ViewModel"
    }

    fun saveHighestScore(score: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveHighest2048Score(score)
        }
    }

}