package com.smarttoolfactory.indicator

import android.view.animation.DecelerateInterpolator
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    indicatorCount: Int = 7,
    indicatorSize: Dp = 20.dp,
    indicatorShape: Shape = CircleShape,
    space: Dp = 10.dp,
    activeColor: Color = Color.Red,
    inActiveColor: Color = Color.LightGray,
    onClick: (CoroutineScope.(Int) -> Unit)? = null
) {

    val listState = rememberLazyListState()


    var text by remember { mutableStateOf("") }

    Canvas(modifier = Modifier.size(100.dp) ){
        size
    }

    val totalWidth: Dp = indicatorSize * indicatorCount + space * (indicatorCount - 1)
    val widthInPx = LocalDensity.current.run { indicatorSize.toPx() }
    val totalWidthInPx = LocalDensity.current.run { totalWidth.toPx() }

    val items by remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo
        }
    }

    val currentItem by remember {
        derivedStateOf {
            pagerState.currentPage
        }
    }

    val itemCount = pagerState.pageCount
    val currentPageOffset = pagerState.currentPageOffset

    if (items.isNotEmpty()) {
        var tempText =
            "totalWidth: $totalWidthInPx\n" +
                    "currentPageOffset: $currentPageOffset\n" +
                    " Item count ${items.size}, currentItem: $currentItem\n"

        items.forEach { lazyListItemInfo ->
            tempText += "index: ${lazyListItemInfo.index}, " +
                    "offset: ${lazyListItemInfo.offset}, " +
                    "size: ${lazyListItemInfo.size}\n"
        }

        text = tempText
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = currentItem) {
        val viewportSize = listState.layoutInfo.viewportSize
        listState.animateScrollToItem(
            currentItem,
            (widthInPx / 2 - viewportSize.width / 2).toInt()
        )
    }

    BoxWithConstraints(Modifier.width(totalWidth)) {

        LazyRow(
            modifier = Modifier.border(1.dp, Color.Green),
            contentPadding = PaddingValues(vertical = space),

            state = listState,
            horizontalArrangement = Arrangement.spacedBy(space),
        ) {

            items(itemCount) { index ->

                val isSelected = (index == currentItem)

                // Index of item in center when odd number of indicators are set
                // for 5 indicators this is 2nd indicator place
                val centerItemIndex = indicatorCount / 2

                val right1 =
                    (currentItem <= centerItemIndex && itemCount > indicatorCount &&
                            index == indicatorCount - 1)

                val right2 =
                    (index >= currentItem + centerItemIndex &&
                            currentItem > centerItemIndex &&
                            currentItem < indicatorCount - 1)

                val isRightEdgeItem = right1 || right2

                // Check if this item's distance to center item is smaller than half size of
                // the indicator count when current indicator at the center or
                // when we reach the end of list. End of the list only one item is on edge
                // with 10 items and 7 indicators
                // 7-3= 4th item can be the first valid left edge item and
                val isLeftEdgeItem =
                    index <= currentItem - centerItemIndex &&
                            currentItem > centerItemIndex &&
                            index < itemCount - indicatorCount + 1


                val neighbor =
                    (currentItem > 0 && index == currentItem - 1) ||
                            (index == currentItem + 1 && currentItem < itemCount - 2)


                println(
                    "⚾️ LazyRow index: $index, right1: $right1, right2: $right2, " +
                            "currentPageOffset: $currentPageOffset"
                )

//                text =
//                    "index: $index, right1: $right1, right2: $right2, neighbor: $neighbor, " +
//                            "currentPageOffset: $currentPageOffset"

                Box(
                    modifier = Modifier
                        .graphicsLayer {

//                                val scale = 1f
                            val scale = if (isSelected) {
                                1f
                            } else if (isLeftEdgeItem || isRightEdgeItem) {
                                .5f
                            } else {
                                .8f
                            }
                            scaleX = scale
                            scaleY = scale

                        }

                        .clip(indicatorShape)
                        .size(indicatorSize)
                        .background(
                            if (isSelected) activeColor else inActiveColor,
                            indicatorShape
                        )
                        .clickable {
                            coroutineScope.launch {
                                onClick?.invoke(this, index)
                            }
                        }
                )
            }
        }
    }

    Text(text)
}

private fun getScale(index: Int, currentPage: Int, indicatorCount: Int, itemCount: Int): Float {
    return 1f
}
