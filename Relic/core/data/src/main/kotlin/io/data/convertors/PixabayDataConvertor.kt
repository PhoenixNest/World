package io.data.convertors

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import io.data.dto.pixabay.PixabayImagesDTO

class PixabayDataConvertor {

    @TypeConverter
    fun dataToSearchImageJson(sourceData: PixabayImagesDTO): String {
        return Moshi.Builder()
            .build()
            .adapter(PixabayImagesDTO::class.java)
            .toJson(sourceData)
    }

    @TypeConverter
    fun jsonToSearchImageData(json: String): PixabayImagesDTO? {
        return Moshi.Builder()
            .build()
            .adapter(PixabayImagesDTO::class.java)
            .fromJson(json)
    }

}