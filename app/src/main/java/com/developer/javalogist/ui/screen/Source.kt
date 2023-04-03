package com.developer.javalogist.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developer.javalogist.R
import com.developer.javalogist.data.model.TopNewsArticle
import com.developer.javalogist.data.model.TopNewsResponse
import com.developer.javalogist.network.NewsManager

@Composable
fun Sources(newsManager: NewsManager) {
    val items = listOf(
        "TechCrunch" to "techcrunch",
        "TalkSport" to "talksport",
        "Business Insider" to "business-insider",
        "Reuters" to "reuters",
        "Politico" to "politico",
        "TheVerge" to "the-verge"
    )
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = newsManager.sourceName.value) },
            actions = {
                var menuExpanded by remember {
                    mutableStateOf(false)
                }
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = null)
                }
                MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp)))
                {
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }) {
                        items.forEach {
                            DropdownMenuItem(onClick = {
                                newsManager.sourceName.value = it.second
                                menuExpanded = false
                            }) {
                                Text(text = it.first)
                            }
                        }
                    }
                }
            }
        )
    }) {
        newsManager.getArticleBySource()
        SourceContent(
            articles = newsManager.getArticleBySource.value.articles ?: listOf(),
            paddingValues = it
        )
    }
}

@Composable
fun SourceContent(articles: List<TopNewsArticle>, paddingValues: PaddingValues? = null) {
    val uriHandler = LocalUriHandler.current
    LazyColumn {
        items(articles) {
            val annotatedString = buildAnnotatedString {
                pushStringAnnotation(
                    tag = "URL",
                    annotation = it.url ?: "newsapi.org"
                )
                withStyle(
                    style = SpanStyle(
                        color = colorResource(id = R.color.purple_500),
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Read Full Article Here")
                }
            }
            Card(
                backgroundColor = colorResource(id = R.color.purple_700),
                elevation = 6.dp,
                modifier = Modifier.padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .height(200.dp)
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = it.title ?: "Not Available",
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = it.description ?: "Not Available",
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )

                    Card(
                        backgroundColor = Color.White,
                        elevation = 6.dp
                    ) {
                        ClickableText(text = annotatedString,
                            modifier = Modifier.padding(8.dp),
                            onClick = {
                                annotatedString.getStringAnnotations(it, it).firstOrNull()
                                    ?.let { result ->
                                        if (result.tag == "URL") {
                                            uriHandler.openUri(result.item)
                                        }
                                    }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SourceContentPreview() {
    SourceContent(
        articles = listOf(
            TopNewsArticle(
                author = "Namita Singh",
                title = "Sen. Murkowski slams Dems over 'show votes' on federal election bills - Fox News",
                description = "Sen. Lisa Murkowski, R-Alaska, slammed Senate Democrats for pursuing “show votes” on federal election bills on Wednesday as Republicans used the filibuster to block consideration the John Lewis Voting Rights Advancement Act.",
                publishedAt = "2021-11-04T01:57:36Z"
            )
        )
    )
}


