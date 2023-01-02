package io.dev.android.game.ui.one_line_finish.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev.android.game.data.db.AWKDatabase
import io.dev.android.game.data.db.one_line_finish.OneLineFinishRepository
import io.dev.android.game.data.db.one_line_finish.model.OneLineFinishRoadModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OneLineFinishViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val oneLineFinishDao = AWKDatabase.getDatabase(application).oneLineFinishDao()

    private val repository: OneLineFinishRepository = OneLineFinishRepository(oneLineFinishDao)

    fun getAllData(): LiveData<List<OneLineFinishRoadModel>> {
        return repository.getAllData()
    }

    fun insertData(roadModel: OneLineFinishRoadModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(roadModel)
        }
    }

    fun updateData(roadModel: OneLineFinishRoadModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(roadModel)
        }
    }

    fun deleteItem(roadModel: OneLineFinishRoadModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(roadModel)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}