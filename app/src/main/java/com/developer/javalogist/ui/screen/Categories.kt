package com.developer.javalogist.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.developer.javalogist.R
import com.developer.javalogist.model.getAllArticleCategory
import com.developer.javalogist.network.NewsManager

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