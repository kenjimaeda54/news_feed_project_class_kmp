package com.newsandfeed.ui.features.screens.article

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.newsandfeed.domain.entity.ContentEntity
import com.newsandfeed.ui.features.article.ArticleViewModel
import com.newsandfeed.ui.features.home.mvi.ArticleIntent
import com.newsandfeed.ui.features.views.CardComponent
import com.newsandfeed.ui.features.views.ErrorComponent
import com.newsandfeed.ui.features.views.LoadingComponent


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArticleScreen() {
    val articleViewModel = viewModel<ArticleViewModel>()
    val snackBarHostState = remember { SnackbarHostState() }
    val state by articleViewModel.state.collectAsState()
    var articleSearch by rememberSaveable {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        articleViewModel.handleIntent(ArticleIntent.LoadIAllArticles)
    }

    LaunchedEffect(state.showToastErrorIfNotConnectionInternet) {
        if (state.showToastErrorIfNotConnectionInternet) {
            val result = snackBarHostState.showSnackbar(
                message = "Conecta com a internet e tenta novamente para visualizar conteúdo recente",
                actionLabel = "Tentar novamente",
                duration = SnackbarDuration.Long
            )

            if (result == SnackbarResult.ActionPerformed) {
                articleViewModel.handleIntent(ArticleIntent.LoadIAllArticles)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            if (state.isLoading) {
                LoadingComponent()
            } else if (state.error != null && !state.showToastErrorIfNotConnectionInternet) {
                ErrorComponent()
            } else {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = articleSearch,
                    onValueChange = { value ->
                        articleSearch = value
                        articleViewModel.handleIntent(ArticleIntent.SearchArticle(value))
                    },
                    label = {
                        Text(
                            "Clica e digite para pesquisar artigos relacionado a um assunto",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent, // Fundo transparente ao focar
                        unfocusedContainerColor = Color.Transparent, // Fundo transparente sem foco
                        disabledContainerColor = Color.Transparent, // Fundo transparente desabilitado
                        // Remove a borda/linha inferior (Underline)
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            "Ex: Apple",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                )
                LazyColumn(content = {
                    items(state.articles.content, { it -> it.url }) { content ->
                        CardComponent(content)
                    }
                })
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    ArticleScreen()
}