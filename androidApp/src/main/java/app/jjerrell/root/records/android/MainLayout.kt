/*
 * RootRecords
 * Copyright (C) 2024  Jacob Jerrell (@jjerrell)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.jjerrell.root.records.android.feature.category.categoryGraph
import app.jjerrell.root.records.android.feature.settings.settingsGraph
import app.jjerrell.root.records.android.ui.navigation.RootRecordsNavigation

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainLayout(
    modifier: Modifier = Modifier,
    landingScreen: RootRecordsNavigation = RootRecordsNavigation.Categories // .Tasks
) {
    val controller = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentDestination =
        RootRecordsNavigation.fromNavDestination(navBackStackEntry?.destination)
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    //                    val titleResource =
                    // currentDestination?.titleResourceId?.takeUnless {
                    //                        currentDestination == landingScreen
                    //                    } ?: R.string.app_name
                    val titleResource = currentDestination?.titleResourceId ?: R.string.app_name
                    Text(text = stringResource(id = titleResource))
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
                        IconButton(
                            onClick = { controller.navigate(RootRecordsNavigation.Settings.route) }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = stringResource(id = R.string.settings_title)
                            )
                        }
                    }
                },
                colors =
                    TopAppBarColors(
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
                        onClick = { controller.navigate(addRouteDestination.route) },
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
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            navController = controller,
            startDestination = landingScreen.name
        ) {
            //            taskGraph(controller)
            categoryGraph(controller)
            settingsGraph(controller)
        }
    }
}
