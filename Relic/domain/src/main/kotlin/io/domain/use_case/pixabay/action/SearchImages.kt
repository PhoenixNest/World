package io.domain.use_case.pixabay.action

import io.core.network.NetworkParameters.Keys.PIXABAY_API_KEY
import io.data.dto.pixabay.PixabayImagesDTO
import io.data.model.NetworkResult
import io.domain.repository.IPixabayDataRepository
import javax.inject.Inject

class SearchImages @Inject constructor(
    private val pixabayDataRepository: IPixabayDataRepository
) {

    /**
     * [Search Images](https://pixabay.com/api/docs/#api_search_images)
     *
     * @param apiKey                Your API key
     * @param keyWords              A URL encoded search term. If omitted, all images are returned. This value may not exceed 100 characters. Example: "yellow+flower"
     * @param language              Language code of the language to be searched in. `Accepted values: cs, da, de, en, es, fr, id, it, hu, nl, no, pl, pt, ro, sk, fi, sv, tr, vi, th, bg, ru, el, ja, ko, zh`. Default: "en"
     * @param imageType             Filter results by image type. `Accepted values: "all", "photo", "illustration", "vector".` Default: "all"
     * @param orientation           Whether an image is wider than it is tall, or taller than it is wide. `Accepted values: "all", "horizontal", "vertical".` Default: "all"
     * @param category              Filter results by category. `Accepted values: backgrounds, fashion, nature, science, education, feelings, health, people, religion, places, animals, industry, computer, food, sports, transportation, travel, buildings, business, music`
     * @param isEditorsChoice       Select images that have received an Editor's Choice award. `Accepted values: "true", "false".` Default: "false"
     * @param isSafeSearch          A flag indicating that only images suitable for all ages should be returned. `Accepted values: "true", "false".` Default: "false"
     * @param orderBy               How the results should be ordered. `Accepted values: "popular", "latest".` Default: "popular"
     * @param page                  Returned search results are paginated. Use this parameter to select the page number. Default: 1
     * @param perPage               Determine the number of results per page. `Accepted values: 3 - 200.` Default: 20
     * */
    suspend operator fun invoke(
        apiKey: String = PIXABAY_API_KEY,
        keyWords: String,
        language: String,
        imageType: String,
        orientation: String,
        category: String,
        isEditorsChoice: Boolean,
        isSafeSearch: Boolean,
        orderBy: String,
        page: Int,
        perPage: Int
    ): NetworkResult<PixabayImagesDTO> {
        return pixabayDataRepository.searchImages(
            apiKey = apiKey,
            keyWords = keyWords,
            language = language,
            imageType = imageType,
            orientation = orientation,
            category = category,
            isEditorsChoice = isEditorsChoice,
            isSafeSearch = isSafeSearch,
            orderBy = orderBy,
            page = page,
            perPage = perPage
        )
    }

}