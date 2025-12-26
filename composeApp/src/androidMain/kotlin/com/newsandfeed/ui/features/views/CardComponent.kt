package com.newsandfeed.ui.features.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.newsandfeed.domain.entity.ContentEntity

@Composable
fun CardComponent(article: ContentEntity) {
    Row(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 15.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        AsyncImage(
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(5.dp)),
            model = ImageRequest.Builder(LocalContext.current).data(article.urlToImage)
                .crossfade(true).build(),
            contentDescription = "Image Load",
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            if (article.title.isNotEmpty()) {
                Text(article.title, style = MaterialTheme.typography.titleLarge)
            }
            Text(
                article.description,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

