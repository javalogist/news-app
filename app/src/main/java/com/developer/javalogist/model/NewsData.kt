package com.developer.javalogist.model

import com.developer.javalogist.R

data class NewsData(
    val id: Int,
    val image: Int = R.drawable.ic_launcher_background,
    val author: String,
    val title: String,
    val description: String,
    val publishedAt: String
)