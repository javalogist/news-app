package com.developer.javalogist.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

@Composable
fun TopNews(navHostController: NavHostController?) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Top News",
            fontWeight = FontWeight.SemiBold
        )

        Button(onClick = {
            navHostController?.navigate("DetailScreen")
        }) {
            Text(text = "Go to Detail Screen")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopNewsPreview() {
    TopNews(null)
}