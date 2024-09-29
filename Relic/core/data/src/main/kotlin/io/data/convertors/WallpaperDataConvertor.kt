package io.data.convertors

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import io.data.dto.wallpaper.WallpaperImagesDTO

class WallpaperDataConvertor {

    @TypeConverter
    fun dataToSearchImageJson(sourceData: WallpaperImagesDTO): String {
        return Moshi.Builder()
            .build()
            .adapter(WallpaperImagesDTO::class.java)
            .toJson(sourceData)
    }

    @TypeConverter
    fun jsonToSearchImageData(json: String): WallpaperImagesDTO? {
        return Moshi.Builder()
            .build()
            .adapter(WallpaperImagesDTO::class.java)
            .fromJson(json)
    }

}