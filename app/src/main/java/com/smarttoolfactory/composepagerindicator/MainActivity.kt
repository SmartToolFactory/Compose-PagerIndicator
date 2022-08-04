package com.smarttoolfactory.composepagerindicator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.smarttoolfactory.composepagerindicator.ui.theme.ComposePagerIndicatorTheme
import com.smarttoolfactory.indicator.IndicatorOrientation
import com.smarttoolfactory.indicator.PagerIndicator
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePagerIndicatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PagerIndicatorSample()
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun PagerIndicatorSample() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(40.dp))
        val pagerState1 = rememberPagerState(initialPage = 0)
        val coroutineScope = rememberCoroutineScope()

        PagerIndicator(pagerState = pagerState1) {
            coroutineScope.launch {
                pagerState1.scrollToPage(it)
            }
        }

        HorizontalPager(
            count = 10,
            state = pagerState1,
        ) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .shadow(1.dp, RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Text $it",
                    fontSize = 40.sp,
                    color = Color.Gray
                )
            }
        }

        val pagerState2 = rememberPagerState(initialPage = 0)

        PagerIndicator(
            pagerState = pagerState2,
            indicatorSize = 24.dp,
            indicatorCount = 7,
            activeColor = Color(0xffFFC107),
            inActiveColor = Color(0xffFFECB3),
            indicatorShape = CutCornerShape(10.dp)
        )
        HorizontalPager(
            count = 10,
            state = pagerState2,
        ) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .shadow(1.dp, RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Text $it",
                    fontSize = 40.sp,
                    color = Color.Gray
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val pagerState3 = rememberPagerState(initialPage = 0)

            Spacer(modifier = Modifier.width(10.dp))

            PagerIndicator(
                pagerState = pagerState3,
                orientation = IndicatorOrientation.Vertical
            )

            Spacer(modifier = Modifier.width(20.dp))
            VerticalPager(
                count = 10,
                state = pagerState3,
            ) {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .shadow(1.dp, RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Text $it",
                        fontSize = 40.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}