package io.data.dto.maxim

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MaximDTO(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "uuid")
    val uuid: String?,
    @Json(name = "hitokoto")
    val hitokoto: String?,
    @Json(name = "type")
    val type: String?,
    @Json(name = "from")
    val from: String?,
    @Json(name = "from_who")
    val fromWho: Any?,
    @Json(name = "creator")
    val creator: String?,
    @Json(name = "creator_uid")
    val creatorUid: Int?,
    @Json(name = "reviewer")
    val reviewer: Int?,
    @Json(name = "commit_from")
    val commitFrom: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "length")
    val length: Int?
)