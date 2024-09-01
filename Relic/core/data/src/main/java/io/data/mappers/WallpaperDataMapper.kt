package io.data.mappers

import io.data.dto.wallpaper.WallpaperImagesDTO
import io.data.entity.wallpaper.WallpaperImagesEntity
import io.data.model.wallpaper.WallpaperImagesDataModel

object WallpaperDataMapper {

    fun WallpaperImagesDTO.toEntity(): WallpaperImagesEntity {
        return WallpaperImagesEntity(datasource = this)
    }

    fun WallpaperImagesDTO.toModelList(): List<WallpaperImagesDataModel?> {
        val tempList = mutableListOf<WallpaperImagesDataModel?>()
        this.results?.forEach {
            tempList.add(
                WallpaperImagesDataModel(
                    id = it?.id,
                    previewImageUrl = it?.previewURL,
                    previewImageWidth = it?.previewWidth,
                    previewImageHeight = it?.previewHeight,
                    largeImageUrl = it?.largeImageURL,
                    originalImageWidth = it?.imageWidth,
                    originalImageHeight = it?.imageHeight,
                    author = it?.user,
                    authorAvatarUrl = it?.userImageURL,
                    authorPageUrl = it?.pageURL,
                    likes = it?.likes
                )
            )
        }
        return tempList
    }
}