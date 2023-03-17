package com.developer.javalogist.ui.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developer.javalogist.R
import com.developer.javalogist.model.TopNewsArticle
import com.developer.javalogist.ui.MockData
import com.developer.javalogist.ui.MockData.getTimeAgo
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun DetailScreen(
    navigationController: NavController? = null,
    article: TopNewsArticle,
    scrollState: ScrollState
) {
    Scaffold(topBar = {
        DetailTopAppBar {
            navigationController?.navigateUp()
        }
    }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            CoilImage(
                imageModel = article.urlToImage,
                contentScale = ContentScale.Crop,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoWithIcon(
                    icon = Icons.Default.Edit, info = article.author ?: "N/A"
                )

                InfoWithIcon(
                    icon = Icons.Default.DateRange,
                    info = MockData.stringToDate(article.publishedAt)?.getTimeAgo() ?: ""
                )
            }
            Text(text = article.title!!, fontWeight = FontWeight.Bold)
            Text(text = article.description!!, modifier = Modifier.padding(top = 16.dp))
        }
    }
}

@Composable
fun InfoWithIcon(icon: ImageVector, info: String) {
    Row {
        Icon(
            icon,
            contentDescription = "Author",
            modifier = Modifier.padding(end = 8.dp),
            colorResource(id = R.color.purple_500)
        )
        Text(text = info)
    }
}

@Composable
fun DetailTopAppBar(onBackPressed: () -> Unit) {
    TopAppBar(title = { Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold) },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        })
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(article = TopNewsArticle(), scrollState = rememberScrollState())
}