package io.data.model.wallpaper

data class WallpaperImagesDataModel(
    val id: Int?,
    /* For preview image usage */
    val previewImageUrl: String?,
    val previewImageWidth: Int?,
    val previewImageHeight: Int?,
    /* For preview image usage */
    val webFormatImageUrl: String?,
    val webFormatImageWidth: Int?,
    val webFormatImageHeight: Int?,
    /* For normal image usage */
    val originalImageUrl: String?,
    val originalImageWidth: Int?,
    val originalImageHeight: Int?,
    /* For author info usage */
    val author: String?,
    val authorAvatarUrl: String?,
    val authorPageUrl: String?,
    val likes: Int?
)