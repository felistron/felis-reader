package com.felisreader

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.felisreader.core.presentation.BottomBar
import com.felisreader.ui.theme.FelisReaderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    @OptIn(ExperimentalMaterial3Api::class)
    private lateinit var scrollBehavior: TopAppBarScrollBehavior

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FelisReaderTheme(
                dynamicColor = true
            ) {
                navController = rememberNavController()
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

                var topBarIcon by remember {
                    mutableStateOf<@Composable () -> Unit>( { AppIcon() } )
                }

                var topBarTitle by remember {
                    mutableStateOf<@Composable () -> Unit>( { AppTitle() } )
                }

                var topBarVisible by remember { mutableStateOf(true) }

                Box(
                    contentAlignment = Alignment.TopCenter
                ) {
                    Scaffold(
                        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                        topBar = {
                            AnimatedVisibility(topBarVisible) {
                                TopAppBar(
                                    scrollBehavior = scrollBehavior,
                                    title = { topBarTitle() },
                                    navigationIcon = { topBarIcon() },
                                    colors = TopAppBarDefaults.topAppBarColors(
                                        containerColor = MaterialTheme.colorScheme.background,
                                        scrolledContainerColor = MaterialTheme.colorScheme.background,
                                    ),
                                )
                            }
                        },
                        content = {
                            Surface(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(0.dp),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                AppNavigation(
                                    navController = navController,
                                    modifier = Modifier.padding(it),
                                    setTopBarIcon = { icon -> topBarIcon = { icon() }},
                                    setTopBarTitle = { title -> topBarTitle = { title() }},
                                    resetTopBar = {
                                        topBarIcon =  { AppIcon() }
                                        topBarTitle = { AppTitle() }
                                    },
                                    setTopBarVisible = { visible -> topBarVisible = visible }
                                )
                            }
                        },
                        bottomBar = {
                            BottomBar(
                                navController = navController,
                            )
                        }
                    )
                    if (BuildConfig.DEBUG) {
                        Text(
                            modifier = Modifier.padding(4.dp).alpha(0.5f),
                            text = "Build ${BuildConfig.VERSION_CODE} - v${BuildConfig.VERSION_NAME}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppIcon() {
    Icon(
        modifier = Modifier.height(40.dp).padding(horizontal = 8.dp),
        painter = painterResource(id = R.drawable.felis_reader_logo_foreground),
        tint = Color.Unspecified,
        contentDescription = null,
    )
}

@Composable
fun AppTitle() {
    Text(stringResource(id = R.string.app_name))
}

