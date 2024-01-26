package io.data.model.wallpaper

data class WallpaperImagesDataModel(
    val previewImageUrl: String?,
    val largeImageUrl: String?,
    val author: String?,
    val authorAvatarUrl: String?,
    val authorPageUrl: String?,
    val likes: Int?
)