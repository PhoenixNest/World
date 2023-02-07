package io.dev.android.composer.jetpack.ui.home.anim_list

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import io.dev.android.composer.jetpack.model.AnimTestModel

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun AnimListItemPreview() {
    val context = LocalContext.current
    val model = AnimTestModel.testAnimData().first()
    AnimListItem(
        model = model,
        itemOnClick = {
            Toast.makeText(context, "You have clicked ${model.id}-${model.title}", Toast.LENGTH_SHORT).show()
        })
}

@Preview(showBackground = true)
@Composable
fun AnimListPreview() {
    AnimList(listData = AnimTestModel.testAnimData())
}