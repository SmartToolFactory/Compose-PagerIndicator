package com.smarttoolfactory.composepagerindicator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.smarttoolfactory.composepagerindicator.ui.theme.ComposePagerIndicatorTheme
import com.smarttoolfactory.indicator.PagerIndicator

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePagerIndicatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        val pagerState = rememberPagerState(initialPage = 0)

                        Spacer(modifier = Modifier.height(20.dp))
                        PagerIndicator(pagerState = pagerState)



                        HorizontalPager(
                            modifier = Modifier.fillMaxHeight(),
                            count = 10,
                            state = pagerState,
                        ) {
                            Text("Text $it", fontSize = 60.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

