package com.developer.javalogist.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developer.javalogist.R
import com.developer.javalogist.model.TopNewsArticle
import com.developer.javalogist.model.getAllArticleCategory
import com.developer.javalogist.network.NewsManager
import com.developer.javalogist.ui.MockData
import com.developer.javalogist.ui.MockData.getTimeAgo
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun Categories(newsManager: NewsManager, onFetchCategory: (String) -> Unit = {}) {
    val tabItems = getAllArticleCategory()
    Column {
        LazyRow {
            items(tabItems) {
                CategoryTab(
                    category = it.categoryName,
                    isSelected = newsManager.selectedCategory.value == it,
                    onFetchCategory = onFetchCategory
                )
            }
        }
        ArticleContent(articles = newsManager.getArticleByCategory.value.articles ?: listOf())
    }
}

@Composable
fun CategoryTab(category: String, isSelected: Boolean = false, onFetchCategory: (String) -> Unit) {
    val background =
        if (isSelected) colorResource(id = R.color.purple_200) else colorResource(id = R.color.purple_700)
    Surface(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 16.dp)
            .clickable {
                onFetchCategory(category)
            }, shape = MaterialTheme.shapes.small, color = background
    ) {
        Text(
            text = category, style = MaterialTheme.typography.body2, color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ArticleContent(articles: List<TopNewsArticle>, modifier: Modifier = Modifier) {
    LazyColumn {
        items(articles) {
            Card(
                modifier = modifier.padding(8.dp),
                border = BorderStroke(2.dp, color = colorResource(id = R.color.purple_500)),
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    CoilImage(
                        imageModel = it.urlToImage,
                        modifier = Modifier.size(100.dp)
                    )

                    Column(
                        modifier = modifier.padding(8.dp)
                    ) {
                        Text(
                            text = it.title ?: "Not Available",
                            fontWeight = FontWeight.Bold,
                            maxLines = 3, overflow = TextOverflow.Ellipsis,
                            modifier = modifier.padding(bottom = 4.dp)
                        )

                        Row(
                            modifier = modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = it.author ?: "Not Available")
                            Text(text = MockData.stringToDate(it.publishedAt)?.getTimeAgo() ?: "N/A")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ArticleContentPreview(){
    ArticleContent(articles = listOf(
        TopNewsArticle(
            author = "Namita Singh",
            title = "Sen. Murkowski slams Dems over 'show votes' on federal election bills - Fox News",
            description = "Sen. Lisa Murkowski, R-Alaska, slammed Senate Democrats for pursuing “show votes” on federal election bills on Wednesday as Republicans used the filibuster to block consideration the John Lewis Voting Rights Advancement Act.",
            publishedAt = "2021-11-04T01:57:36Z"
        )
    ))
}