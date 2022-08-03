package com.smarttoolfactory.indicator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
 fun PagerIndicator() {

    val pagerState: PagerState = rememberPagerState(initialPage = 0)
    val listState = rememberLazyListState()

    val items: List<LazyListItemInfo> by remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo
//                .filter { it.offset >= 0 }
        }
    }

    val page by remember {
        derivedStateOf {
            pagerState.currentPage
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val text = if (items.isEmpty()) {
            "Page page: $page, offset: ${pagerState.currentPageOffset}"
        } else {
            var tempText =
                "PAGE: $page, offset: ${pagerState.currentPageOffset}, ITEMS: ${items.size}\n"
            items.forEach {
                tempText += "index: ${it.index}, offset: ${it.offset}\n"
            }
            tempText
        }

        BoxWithConstraints(Modifier.width(160.dp)) {

            val maxWidth = constraints.maxWidth

            val widthInPx = LocalDensity.current.run { 20.dp.toPx() }
            val coroutineScope = rememberCoroutineScope()

            LaunchedEffect(key1 = page) {

                val viewportSize = listState.layoutInfo.viewportSize
                println("🔥 SCROLL TO $page, maxWidth:$maxWidth, viewportSize: $viewportSize")


                items.forEach {
                    println("Item ${it.index}, offset: ${it.offset}, size: ${it.size}")
                }
                listState.animateScrollToItem(page, (widthInPx - viewportSize.width / 2).toInt())
                val otherItems = listState.layoutInfo.visibleItemsInfo
                println("---------------------")
                otherItems.forEach {
                    println("Item ${it.index}, offset: ${it.offset}, size: ${it.size}")
                }
            }

            LazyRow(
                modifier = Modifier.border(3.dp, Color.Green),
                contentPadding = PaddingValues(10.dp),
                state = listState,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                val pageCount = pagerState.pageCount
                val currentPageOffset = pagerState.currentPageOffset

                items(pageCount) {

                    val isSelected = (it == page)

                    val right = it >= page + 2 && page > 2 && page < 5
                    val left = it <= page - 2 && page > 2 && it < pageCount - 4

                    val neighbor =
                        (page > 0 && it == page - 1) || (it == page + 1 && page < pageCount - 2)

                    Box(
                        modifier = Modifier
                            .graphicsLayer {
                                println("⚾️ Modifier index: $it, neighbor: $neighbor, currentPageOffset: $currentPageOffset")
//                                val scale = 1f
                                val scale = if (isSelected) {
                                    1f
                                } else if (left || right) {
                                    .5f
                                } else {
                                    .8f
                                }
                                scaleX = scale
                                scaleY = scale

                            }

                            .clip(CircleShape)
                            .size(20.dp)
                            .background(
                                if (isSelected) Color.Red else Color.LightGray,
                                CircleShape
                            )
                            .clickable {
                                coroutineScope.launch {
                                    pagerState.scrollToPage(it)
                                }
                            }
                    )

                }
            }

//            Divider(
//                modifier = Modifier
//                    .padding(top = 7.dp)
//                    .width(80.dp)
//                    .height(3.dp)
//            )

        }
        Text(text)

        HorizontalPager(
            modifier = Modifier.fillMaxHeight(),
            count = 8,
            state = pagerState,
        ) {
            Text("Text $it", fontSize =60.sp, fontWeight = FontWeight.Bold)
        }
    }

}