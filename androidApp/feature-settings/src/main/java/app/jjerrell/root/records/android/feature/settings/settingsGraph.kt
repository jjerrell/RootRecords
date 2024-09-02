package app.jjerrell.root.records.android.feature.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import app.jjerrell.root.records.android.feature.settings.list.SettingsListView
import app.jjerrell.root.records.android.ui.navigation.RootRecordsNavigation

fun NavGraphBuilder.settingsGraph(navController: NavController) {
    navigation(
        startDestination = RootRecordsNavigation.Settings.route,
        route = RootRecordsNavigation.Settings.name
    ) {
        composable(RootRecordsNavigation.Settings.route) {
            SettingsListView(
                modifier = Modifier.fillMaxSize(),
//                onBackClick = { navController.popBackStack() }
            )
        }
        composable(RootRecordsNavigation.Help.route) {
            Text("Help")
        }
        composable(RootRecordsNavigation.About.route) {
            Text("About")
        }
    }
}
