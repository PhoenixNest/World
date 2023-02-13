package io.dev.android.composer.jetpack.model

data class AnimTestModel(
    val id: Int,
    val title: String,
    val subTitle: String
) {
    companion object {
        fun testAnimData(): List<AnimTestModel> {
            val titleList: List<String> = listOf("Google", "Android", "Jetpack", "Compose", "Ui")
            val testList: MutableList<AnimTestModel> = mutableListOf()
            for (i in titleList.indices) {
                testList.add(AnimTestModel(i, titleList[i], "Show more"))
            }

            return testList
        }
    }
}