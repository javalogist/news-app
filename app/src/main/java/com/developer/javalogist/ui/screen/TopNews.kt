package com.developer.javalogist.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.developer.javalogist.model.NewsData
import com.developer.javalogist.ui.MockData
import com.developer.javalogist.ui.MockData.topNewsList

@Composable
fun TopNews(navHostController: NavHostController?) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(MockData.topNewsList) {
                TopNewsItem(it) {
                    navHostController?.navigate("DetailScreen/${it.id}")
                }
            }
        }
    }
}

@Composable
fun TopNewsItem(newsData: NewsData = topNewsList[0], onNewsClicked: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .padding(8.dp)
            .clickable {
                onNewsClicked()
            }
    ) {
        Image(
            painter = painterResource(id = newsData.image),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = 16.dp, start = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = newsData.publishedAt,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = newsData.title,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopNewsPreview() {
    TopNews(null)
}

@Preview(showBackground = true)
@Composable
fun TopNewsItemPreview() {
    TopNewsItem()
}