package com.developer.javalogist.ui.screen

import com.developer.javalogist.R
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.developer.javalogist.model.NewsData
import com.developer.javalogist.model.TopNewsArticle
import com.developer.javalogist.ui.MockData
import com.developer.javalogist.ui.MockData.getTimeAgo
import com.developer.javalogist.ui.MockData.topNewsList
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun TopNews(navHostController: NavHostController?, newsArticles: List<TopNewsArticle>? = null) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        newsArticles?.let {
            LazyColumn {
                items(it.size) {
                    TopNewsItem(newsArticles[it]) {
                        navHostController?.navigate("DetailScreen/${it}")
                    }
                }
            }
        }
    }
}

@Composable
fun TopNewsItem(article: TopNewsArticle, onNewsClicked: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .padding(8.dp)
            .clickable {
                onNewsClicked()
            }
    ) {
        CoilImage(
            imageModel = article.urlToImage,
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = 16.dp, start = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = MockData.stringToDate(article.publishedAt)?.getTimeAgo() ?: "",
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = article.title ?: "",
                color = Color.White,
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
    TopNewsItem(TopNewsArticle())
}