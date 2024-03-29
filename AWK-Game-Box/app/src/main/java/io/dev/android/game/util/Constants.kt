package io.dev.android.game.util

import android.content.Context
import io.dev.android.game.R
import io.dev.android.game.ui.home.model.GameModel

object Constants {

    const val TABLE_ONE_LINE_FINISH = "table_one_line_finish"

    fun getHomeItemList(context: Context): List<GameModel> {
        val block2048 = GameModel(
            title = context.resources.getString(R.string.game_2048_title),
            iconResId = R.drawable.ic_score,
            backgroundResId = R.color.color_cube_8
        )

        val unscrambleTheWord = GameModel(
            title = context.resources.getString(R.string.unscramble_title),
            iconResId = R.drawable.ic_unscramble,
            backgroundResId = R.color.color_cube_128
        )

        val oneLineFinish = GameModel(
            title = context.resources.getString(R.string.game_one_line_finish_title),
            iconResId = R.drawable.ic_game_over,
            backgroundResId = R.color.color_cube_super
        )

        return listOf(
            block2048,
            unscrambleTheWord,
            oneLineFinish
        )
    }

}