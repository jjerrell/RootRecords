package app.jjerrell.root.records.android

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.jjerrell.root.records.android.feature.category.categoryGraph
import app.jjerrell.root.records.android.ui.navigation.RootRecordsNavigation

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainLayout(
    modifier: Modifier = Modifier,
    landingScreen: RootRecordsNavigation = RootRecordsNavigation.Categories //.Tasks
) {
    val controller = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentDestination = RootRecordsNavigation.fromNavDestination(navBackStackEntry?.destination)
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
//                    val titleResource = currentDestination?.titleResourceId?.takeUnless {
//                        currentDestination == landingScreen
//                    } ?: R.string.app_name
                    val titleResource = currentDestination?.titleResourceId ?: R.string.app_name
                    Text(
                        text = stringResource(id = titleResource)
                    )
                },
                navigationIcon = {
                    if (currentDestination != null && currentDestination != landingScreen) {
                        IconButton(onClick = { controller.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.back)
                            )
                        }
                    }
                },
                actions = {
                    if (currentDestination == landingScreen) {
                        IconButton(onClick = { controller.navigate(RootRecordsNavigation.Settings.route) }) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = stringResource(id = R.string.back)
                            )
                        }
                    }
                },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            when (val addRouteDestination = currentDestination?.addRoute) {
                null -> {}
                else -> {
                    FloatingActionButton(
                        onClick = {
                            controller.navigate(addRouteDestination.route)
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(addRouteDestination.titleResourceId)
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            navController = controller,
            startDestination = landingScreen.name
        ) {
//            taskGraph(controller)
            categoryGraph(controller)
//            settingsGraph(controller)
            composable(RootRecordsNavigation.About.route) {
                Text("About")
            }
            composable(RootRecordsNavigation.Help.route) {
                Text("Help")
            }
        }
    }
}
