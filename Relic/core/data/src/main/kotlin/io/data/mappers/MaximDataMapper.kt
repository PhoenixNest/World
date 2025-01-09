package io.data.mappers

import io.data.dto.maxim.MaximDTO
import io.data.model.maxim.MaximModel

object MaximDataMapper {

    fun MaximDTO.toModel(): MaximModel {
        return MaximModel(
            hitokoto = hitokoto,
            from = from,
            fromWho = fromWho,
            creator = creator,
            createdAt = createdAt
        )
    }

}