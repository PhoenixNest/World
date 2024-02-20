package io.dev.relic.feature.pages.explore.widget.bottom_sheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amap.api.navi.model.search.LatLonPoint
import com.amap.api.navi.model.search.PoiItem
import io.core.ui.CommonAsyncImage
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColor
import io.core.ui.utils.RelicUiUtil
import io.core.ui.utils.RelicUiUtil.getCurrentScreenHeightDp
import io.core.ui.utils.RelicUiUtil.getCurrentScreenWidthDp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExplorePoiCardItem(
    poiItem: PoiItem,
    onItemClick: () -> Unit
) {
    val screenWidth = getCurrentScreenWidthDp()
    val screenHeight = getCurrentScreenHeightDp()
    val containerWidth = screenWidth / 2
    val containerHeight = screenHeight / 3

    Card(
        modifier = Modifier
            .width(containerWidth)
            .height(containerHeight),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = mainThemeColor
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CommonAsyncImage(
                url = poiItem.photos.first().url,
                imageWidth = containerWidth,
                imageHeight = containerHeight / 2,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = poiItem.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(),
                style = TextStyle(
                    color = mainTextColor,
                    fontFamily = ubuntu,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@Composable
@Preview
private fun ExplorePoiCardItemPreview() {
    ExplorePoiCardItem(
        poiItem = PoiItem(
            /* p0 = */ "Poi Item",
            /* p1 = */ LatLonPoint(0.0, 0.0),
            /* p2 = */ "Title",
            /* p3 = */ "Desc Desc Desc"
        ),
        onItemClick = {}
    )
}