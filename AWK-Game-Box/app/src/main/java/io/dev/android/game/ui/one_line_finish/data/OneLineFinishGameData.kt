package io.dev.android.game.ui.one_line_finish.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import io.dev.android.game.MyApplication
import io.dev.android.game.data.datastore.DataStoreRepository
import io.dev.android.game.data.db.one_line_finish.model.OneLineFinishRoadModel
import kotlinx.coroutines.flow.distinctUntilChanged

object OneLineFinishGameData {

    /**
     * Datastore repository for one line finish
     * */
    private val dataStoreRepository: DataStoreRepository = DataStoreRepository(MyApplication.getInstance().applicationContext)

    /**
     * Indicate current game level
     * */
    val currentLevelLiveData: LiveData<Int> = dataStoreRepository.readCurrentOneLineLevel().distinctUntilChanged().asLiveData()

    val roadValuesList: MutableList<OneLineFinishRoadModel> = mutableListOf(
        // Chapter 1
        OneLineFinishRoadModel(3, 3, listOf(6, 3, 0, 1, 4, 7, 8)),
        OneLineFinishRoadModel(3, 3, listOf(4, 7, 8, 5, 2, 1, 0)),
        OneLineFinishRoadModel(3, 3, listOf(0, 1, 4, 3, 6, 7, 8)),
        OneLineFinishRoadModel(3, 4, listOf(9, 5, 6, 7, 3, 2, 1, 0, 4, 8)),
        OneLineFinishRoadModel(3, 4, listOf(5, 1, 2, 6, 7, 11, 10, 9, 8, 4)),
        OneLineFinishRoadModel(3, 4, listOf(5, 4, 0, 1, 2, 3, 7, 6, 10, 9)),
        OneLineFinishRoadModel(3, 4, listOf(0, 1, 2, 3, 7, 11, 10, 9, 8)),
        OneLineFinishRoadModel(3, 4, listOf(6, 2, 3, 7, 11, 10, 9, 8, 4)),
        OneLineFinishRoadModel(3, 4, listOf(8, 4, 5, 1, 2, 3, 7, 11, 10)),
        OneLineFinishRoadModel(3, 4, listOf(1, 2, 3, 7, 6, 10, 9, 8)),
        // Chapter 2
        OneLineFinishRoadModel(3, 6, listOf(8, 2, 3, 4, 10, 9, 15, 14, 13, 7, 6)),
        OneLineFinishRoadModel(4, 3, listOf(4, 1, 2, 5, 8, 7, 10, 9, 6, 3)),
        OneLineFinishRoadModel(4, 3, listOf(9, 6, 7, 10, 11, 8, 5, 4, 1, 0)),
        OneLineFinishRoadModel(4, 3, listOf(7, 10, 9, 6, 3, 0, 1, 2, 5, 8)),
        OneLineFinishRoadModel(4, 3, listOf(10, 9, 6, 3, 0, 1, 2, 5, 8)),
        // Chapter 3
        OneLineFinishRoadModel(4, 5, listOf(5, 0, 1, 6, 11, 12, 7, 8, 13, 14, 19, 18, 17, 16, 15)),
        OneLineFinishRoadModel(4, 5, listOf(13, 12, 7, 2, 1, 6, 5, 10, 11, 16, 17, 18, 19, 14, 9)),
        OneLineFinishRoadModel(4, 5, listOf(4, 9, 14, 13, 12, 7, 2, 1, 0, 5, 6, 11, 16, 15)),
        OneLineFinishRoadModel(4, 5, listOf(9, 8, 3, 2, 7, 6, 11, 10, 15, 16, 17, 18, 19, 14)),
        OneLineFinishRoadModel(4, 5, listOf(8, 9, 14, 19, 18, 17, 16, 15, 10, 11, 12, 7, 6, 1)),
        // Chapter 4
        OneLineFinishRoadModel(5, 5, listOf(0, 5, 10, 15, 20, 21, 16, 11, 6, 1, 2, 3, 8, 9, 14, 13, 12, 17, 22, 23, 18, 19, 24)),
        OneLineFinishRoadModel(5, 5, listOf(17, 12, 13, 14, 9, 4, 3, 2, 7, 6, 1, 0, 5, 10, 11, 16, 15, 20, 21, 22, 23, 24, 19)),
        OneLineFinishRoadModel(5, 5, listOf(3, 4, 9, 8, 7, 2, 1, 0, 5, 6, 11, 10, 15, 20, 21, 16, 17, 12, 13, 14, 19, 24, 23)),
        OneLineFinishRoadModel(5, 5, listOf(17, 22, 23, 24, 19, 18, 13, 14, 9, 4, 3, 2, 7, 6, 1, 0, 5, 10, 11, 16, 15, 20)),
        OneLineFinishRoadModel(5, 5, listOf(10, 5, 0, 1, 6, 7, 2, 3, 4, 9, 14, 19, 24, 23, 18, 13, 12, 17, 16, 15, 20, 21)),
        OneLineFinishRoadModel(5, 5, listOf(13, 12, 7, 8, 9, 4, 3, 2, 1, 0, 5, 10, 11, 16, 15, 20, 21, 22, 17, 18, 19, 24)),
        OneLineFinishRoadModel(5, 5, listOf(11, 6, 1, 0, 5, 10, 15, 16, 21, 22, 23, 18, 13, 12, 7, 2, 3, 8, 9, 14, 19)),
        OneLineFinishRoadModel(5, 5, listOf(8, 9, 4, 3, 2, 1, 0, 5, 10, 11, 6, 7, 12, 13, 18, 19, 24, 23, 22, 21, 16)),
        OneLineFinishRoadModel(5, 5, listOf(6, 11, 10, 5, 0, 1, 2, 3, 4, 9, 8, 13, 14, 19, 24, 23, 18, 17, 22, 21, 20)),
        OneLineFinishRoadModel(5, 5, listOf(17, 12, 11, 10, 5, 0, 1, 2, 3, 4, 9, 14, 13, 18, 19, 24, 23, 22, 21, 16)),
        OneLineFinishRoadModel(5, 5, listOf(4, 3, 8, 9, 14, 19, 24, 23, 22, 17, 12, 7, 6, 1, 0, 5, 10, 15, 20, 21)),
        OneLineFinishRoadModel(5, 5, listOf(23, 24, 19, 18, 17, 22, 21, 16, 15, 10, 11, 6, 7, 8, 9, 4, 3, 2, 1, 0)),
        OneLineFinishRoadModel(5, 5, listOf(16, 11, 6, 1, 0, 5, 10, 15, 20, 21, 22, 17, 12, 7, 8, 13, 18, 23, 24)),
        OneLineFinishRoadModel(5, 5, listOf(14, 19, 24, 23, 22, 17, 16, 15, 10, 5, 0, 1, 6, 11, 12, 13, 8, 3, 4)),
        OneLineFinishRoadModel(5, 5, listOf(9, 14, 19, 24, 23, 18, 17, 22, 21, 20, 15, 10, 11, 6, 1, 2, 7, 8, 3)),
        OneLineFinishRoadModel(5, 5, listOf(5, 0, 1, 2, 3, 4, 9, 14, 13, 18, 23, 22, 17, 16, 11, 10, 15, 20)),
        OneLineFinishRoadModel(5, 5, listOf(10, 5, 6, 7, 12, 13, 8, 3, 4, 9, 14, 19, 18, 23, 22, 21, 20, 15)),
        OneLineFinishRoadModel(5, 5, listOf(5, 0, 1, 6, 11, 16, 21, 22, 17, 12, 7, 2, 3, 4, 9, 14, 19, 24)),
        OneLineFinishRoadModel(5, 5, listOf(13, 12, 7, 2, 1, 0, 5, 10, 15, 16, 21, 22, 23, 24, 19, 14, 9)),
        OneLineFinishRoadModel(5, 5, listOf(4, 3, 8, 7, 6, 11, 12, 13, 14, 19, 24, 23, 22, 17, 16, 15, 20)),
        OneLineFinishRoadModel(5, 5, listOf(14, 19, 18, 13, 12, 17, 22, 21, 20, 15, 16, 11, 6, 7, 8, 3, 4)),
        OneLineFinishRoadModel(5, 5, listOf(12, 17, 18, 13, 8, 3, 2, 7, 6, 1, 0, 5, 10, 15, 20, 21)),
        OneLineFinishRoadModel(5, 5, listOf(14, 19, 24, 23, 18, 17, 12, 7, 8, 3, 2, 1, 6, 5, 10, 11)),
        OneLineFinishRoadModel(5, 5, listOf(5, 6, 11, 10, 15, 20, 21, 22, 23, 18, 17, 12, 13, 8, 3, 4)),
        OneLineFinishRoadModel(5, 5, listOf(24, 19, 18, 17, 22, 21, 20, 15, 10, 11, 12, 7, 8, 3, 4)),
        OneLineFinishRoadModel(5, 5, listOf(8, 7, 2, 1, 0, 5, 10, 15, 16, 21, 22, 23, 18, 17, 12)),
        OneLineFinishRoadModel(5, 5, listOf(3, 8, 13, 14, 19, 24, 23, 18, 17, 22, 21, 16, 15, 10, 5)),
        OneLineFinishRoadModel(5, 6, listOf(20, 19, 13, 7, 8, 9, 15, 21, 22, 28, 29, 23, 17, 16, 10, 11, 5, 4, 3, 2, 1, 0, 6, 12, 18, 24, 25, 26)),
        OneLineFinishRoadModel(5, 6, listOf(17, 11, 5, 4, 3, 2, 1, 0, 6, 12, 18, 24, 25, 19, 20, 21, 27, 28, 29, 23, 22, 16, 10, 9, 15, 14, 13, 7)),
        OneLineFinishRoadModel(5, 6, listOf(17, 11, 5, 4, 10, 9, 3, 2, 1, 0, 6, 12, 18, 24, 25, 26, 20, 19, 13, 7, 8, 14, 15, 21, 22, 28, 29, 23)),
        // Chapter 5
        OneLineFinishRoadModel(5, 6, listOf(6, 0, 1, 7, 8, 2, 3, 4, 10, 16, 15, 21, 20, 19, 13, 12, 18, 24, 25, 26, 27, 28, 22, 23, 17, 11, 5)),
        OneLineFinishRoadModel(5, 6, listOf(23, 29, 28, 22, 21, 27, 26, 20, 14, 13, 19, 25, 24, 18, 12, 6, 7, 1, 2, 8, 9, 15, 16, 17, 11, 10, 4)),
        OneLineFinishRoadModel(5, 6, listOf(17, 11, 10, 4, 3, 2, 8, 9, 15, 16, 22, 23, 29, 28, 27, 26, 20, 19, 25, 24, 18, 12, 13, 7, 1, 0)),
        OneLineFinishRoadModel(5, 6, listOf(20, 14, 8, 2, 1, 0, 6, 12, 18, 19, 25, 26, 27, 21, 15, 9, 3, 4, 5, 11, 10, 16, 17, 23, 29, 28)),
        OneLineFinishRoadModel(5, 6, listOf(1, 0, 6, 7, 13, 14, 15, 9, 8, 2, 3, 4, 5, 11, 17, 23, 29, 28, 22, 21, 27, 26, 20, 19, 18, 24)),
        OneLineFinishRoadModel(5, 6, listOf(16, 22, 28, 29, 23, 17, 11, 5, 4, 10, 9, 3, 2, 8, 7, 13, 14, 20, 26, 25, 24, 18, 12, 6, 0)),
        OneLineFinishRoadModel(5, 6, listOf(22, 16, 15, 9, 3, 4, 5, 11, 17, 23, 29, 28, 27, 26, 20, 19, 25, 24, 18, 12, 6, 0, 1, 2, 8)),
        OneLineFinishRoadModel(5, 6, listOf(14, 13, 7, 6, 12, 18, 24, 25, 26, 27, 21, 15, 9, 10, 16, 22, 28, 29, 23, 17, 11, 5, 4, 3, 2)),
        OneLineFinishRoadModel(5, 6, listOf(18, 12, 6, 0, 1, 2, 8, 7, 13, 14, 20, 19, 25, 26, 27, 28, 22, 16, 10, 4, 5, 11, 17, 23)),
        OneLineFinishRoadModel(5, 6, listOf(20, 14, 8, 7, 13, 19, 18, 12, 6, 0, 1, 2, 3, 9, 10, 4, 5, 11, 17, 23, 22, 28, 27, 26)),
        OneLineFinishRoadModel(5, 6, listOf(27, 26, 20, 19, 25, 24, 18, 12, 6, 0, 1, 2, 8, 9, 3, 4, 5, 11, 10, 16, 17, 23, 29, 28)),
        OneLineFinishRoadModel(5, 6, listOf(23, 17, 11, 10, 9, 15, 16, 22, 21, 27, 26, 20, 19, 25, 24, 18, 12, 6, 0, 1, 7, 8, 2)),
        OneLineFinishRoadModel(5, 6, listOf(24, 18, 12, 6, 0, 1, 7, 13, 19, 20, 21, 27, 28, 29, 23, 22, 16, 17, 11, 10, 4, 3, 2)),
        OneLineFinishRoadModel(5, 6, listOf(12, 6, 7, 1, 2, 8, 14, 13, 19, 20, 21, 15, 9, 10, 16, 17, 23, 29, 28, 27, 26, 25, 24)),
        OneLineFinishRoadModel(5, 6, listOf(11, 17, 23, 29, 28, 22, 21, 20, 26, 25, 24, 18, 19, 13, 14, 15, 9, 3, 2, 1, 0, 6)),
        OneLineFinishRoadModel(5, 6, listOf(24, 18, 19, 20, 14, 13, 7, 1, 2, 8, 9, 15, 16, 10, 4, 5, 11, 17, 23, 29, 28, 27)),
        OneLineFinishRoadModel(5, 6, listOf(24, 18, 19, 25, 26, 20, 14, 8, 7, 1, 2, 3, 4, 10, 16, 22, 28, 29, 23, 17, 11, 5)),
        OneLineFinishRoadModel(5, 6, listOf(4, 5, 11, 10, 9, 3, 2, 1, 7, 6, 12, 18, 19, 25, 26, 27, 28, 22, 16, 15, 14)),
        OneLineFinishRoadModel(5, 6, listOf(22, 23, 17, 16, 10, 11, 5, 4, 3, 9, 8, 14, 20, 21, 27, 26, 25, 19, 13, 7, 6)),
        OneLineFinishRoadModel(5, 6, listOf(3, 4, 5, 11, 10, 9, 8, 2, 1, 0, 6, 7, 13, 12, 18, 19, 25, 26, 20, 14, 15)),
        OneLineFinishRoadModel(5, 6, listOf(4, 5, 11, 10, 9, 8, 14, 13, 7, 6, 12, 18, 19, 25, 26, 20, 21, 27, 28, 29)),
        OneLineFinishRoadModel(5, 6, listOf(0, 6, 12, 13, 7, 8, 14, 20, 26, 27, 28, 22, 21, 15, 9, 3, 4, 10, 11, 5)),
        OneLineFinishRoadModel(5, 6, listOf(26, 27, 28, 22, 21, 20, 19, 13, 12, 6, 0, 1, 7, 8, 9, 10, 16, 17, 11, 5)),
        OneLineFinishRoadModel(5, 6, listOf(15, 14, 8, 7, 1, 2, 3, 4, 10, 16, 17, 23, 29, 28, 27, 21, 20, 26, 25)),
        OneLineFinishRoadModel(5, 6, listOf(24, 25, 19, 13, 7, 1, 2, 8, 9, 3, 4, 5, 11, 17, 16, 15, 21, 27, 28)),
        OneLineFinishRoadModel(5, 6, listOf(17, 23, 22, 28, 27, 21, 15, 9, 3, 2, 1, 0, 6, 12, 13, 14, 20, 26, 25)),
        OneLineFinishRoadModel(5, 6, listOf(14, 8, 9, 15, 16, 17, 11, 5, 4, 3, 2, 1, 0, 6, 12, 13, 19, 20)),
        OneLineFinishRoadModel(5, 6, listOf(29, 23, 17, 16, 10, 4, 3, 9, 15, 21, 27, 26, 20, 14, 13, 7, 1, 0)),
        OneLineFinishRoadModel(5, 6, listOf(17, 16, 22, 23, 29, 28, 27, 21, 15, 14, 13, 12, 6, 0, 1, 2, 3, 4)),
        OneLineFinishRoadModel(5, 6, listOf(27, 26, 25, 24, 18, 12, 6, 0, 1, 7, 8, 9, 10, 11, 17, 23, 22)),
    )
}