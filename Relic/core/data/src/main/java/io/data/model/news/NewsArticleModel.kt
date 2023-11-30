package io.data.model.news

data class NewsArticleModel(
    val title: String?,
    val subtitle: String?,
    val author: String?,
    val thumbnailImageUrl: String?,
    val publishDate: String?,
    val contentUrl: String?,
    val source: String?
) {
    companion object {
        fun testList(): List<NewsArticleModel> {
            return listOf(
                NewsArticleModel(
                    title = "Just this... and WoW will be perfect for me.",
                    subtitle = "(First of all, English is not my first language so forgive me if something is weird..) \\n \\nIt seems that the gypsy witch who stopped me one day while I was going to work and told me that you are all NPCs, this is just a simulation and the world conspires in my…",
                    author = "Fantazma",
                    thumbnailImageUrl = "null",
                    publishDate = "2023-11-28 T00:40:11Z",
                    contentUrl = "https://www.mmo-champion.com/threads/2644396-Just-this-and-WoW-will-be-perfect-for-me?p=54329863#post54329863",
                    source = "Mmo-champion.com"
                ),
                NewsArticleModel(
                    title = "House speaker suggest George Santos will quit rather than be expelled",
                    subtitle = "Speaker of the House Mike Johnson on Monday afternoon said he has spoken to U.S. Rep. George Santos, suggesting the embattled and indicted New York Republican might resign rather than face an impending expulsion vote he’s likely to lose.But over the holiday w…",
                    author = "David Badash, The New Civil Rights Movement",
                    thumbnailImageUrl = "https://www.rawstory.com/media-library/george-santos.jpg?id=32972710&width=1200&height=600&coordinates=0%2C25%2C0%2C25",
                    publishDate = "2023-11-27 T21:08:30Z",
                    contentUrl = "https://www.rawstory.com/house-speaker-suggest-george-santos-will-quit-rather-than-be-expelled/",
                    source = "Raw Story"
                )
            )
        }
    }
}