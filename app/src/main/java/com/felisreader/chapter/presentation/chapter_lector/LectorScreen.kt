package com.felisreader.chapter.presentation.chapter_lector

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.felisreader.R
import com.felisreader.chapter.presentation.chapter_lector.components.NavigationField
import com.felisreader.chapter.presentation.chapter_lector.components.PageImage
import com.felisreader.core.presentation.Loading
import kotlinx.coroutines.launch

@Composable
fun LectorScreen(
    viewModel: LectorViewModel = hiltViewModel(),
    chapterId: String,
) {
    LaunchedEffect(true) {
        this.launch {
            viewModel.onEvent(LectorEvent.LoadLector(chapterId))
        }
    }

    LectorContent(
        state = viewModel.state.value,
        navigateToChapter = { id ->
            viewModel.onEvent(LectorEvent.LoadLector(id))
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LectorContent(
    modifier: Modifier = Modifier,
    state: LectorState,
    navigateToChapter: (chapterId: String) -> Unit
) {
    val progress by animateFloatAsState(
        targetValue = (state.lazyListState.firstVisibleItemIndex + 1) / (state.images.size.toFloat() + 1),
        animationSpec = tween(1000, 0)
    )

    val coroutineScope = rememberCoroutineScope()

    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var size by remember { mutableStateOf(0f) }

    if (state.loading) {
        Loading(
            modifier = Modifier.fillMaxSize(),
            size = 64
        )
    } else {
        if (state.chapter != null) {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        size = coordinates.size.width.toFloat()
                    }
                    .graphicsLayer(
                        scaleX = maxOf(1f, minOf(3f, scale)),
                        scaleY = maxOf(1f, minOf(3f, scale)),
                        translationX = offsetX
                    )
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            scale = maxOf(1f, minOf(scale * zoom, 3f))

                            // big brain moment
                            offsetX = minOf(
                                size * (scale - 1) / 2,         // left bound
                                maxOf(
                                    size * (1 - scale) / 2,     // right bound
                                    offsetX + (pan.x * scale)   // offset
                                )
                            )

                            coroutineScope.launch {
                                state.lazyListState.scrollBy(-pan.y)
                            }
                        }
                    },
                verticalArrangement = Arrangement.spacedBy(10.dp),
                state = state.lazyListState
            ) {
                stickyHeader {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        progress = progress
                    )
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = if (state.chapter.attributes.title.isNullOrBlank()) "${stringResource(id = R.string.chapter)} ${state.chapter.attributes.chapter}"
                                    else state.chapter.attributes.title,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                        NavigationField(
                            prevChapter = state.prevChapter,
                            nextChapter = state.nextChapter,
                            navigateToChapter = navigateToChapter
                        )
                    }
                }

                items(items = state.images, key = { it }) {imageUrl ->
                    PageImage(
                        imageUrl = imageUrl
                    )
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                    ) {
                        NavigationField(
                            prevChapter = state.prevChapter,
                            nextChapter = state.nextChapter,
                            navigateToChapter = navigateToChapter
                        )
                    }
                }
            }
        } else {
            Text(text = "Something went wrong...")
        }
    }
}
