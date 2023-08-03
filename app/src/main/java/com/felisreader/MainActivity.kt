package com.felisreader

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
            FelisReaderTheme {
                navController = rememberNavController()
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

                Box(
                    contentAlignment = Alignment.TopCenter
                ) {
                    Scaffold(
                        content = {
                            Surface(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(0.dp),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                AppNavigation(
                                    navController = navController,
                                    modifier = Modifier.padding(it)
                                )
                            }
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
