package io.data.mappers

import io.data.dto.pixabay.PixabayImagesDTO
import io.data.entity.pixabay.PixabayImagesEntity
import io.data.model.pixabay.PixabayDataModel

object PixabayDataMapper {

    fun PixabayImagesDTO.toEntity(): PixabayImagesEntity {
        return PixabayImagesEntity(datasource = this)
    }

    fun PixabayImagesDTO.toModelList(): List<PixabayDataModel?> {
        val tempList = mutableListOf<PixabayDataModel?>()
        this.results?.forEach {
            tempList.add(
                PixabayDataModel(
                    id = it?.id,
                    previewImageUrl = it?.previewURL,
                    previewImageWidth = it?.previewWidth,
                    previewImageHeight = it?.previewHeight,
                    webFormatImageUrl = it?.webformatURL,
                    webFormatImageWidth = it?.webformatWidth,
                    webFormatImageHeight = it?.webformatHeight,
                    originalImageUrl = it?.largeImageURL,
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