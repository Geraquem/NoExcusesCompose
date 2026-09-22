package com.mmfsin.noexcusescompose.presentation.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.presentation.calendar.CalendarScreen
import com.mmfsin.noexcusescompose.presentation.core.components.CustomToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.maximums.MaximumsScreen
import com.mmfsin.noexcusescompose.presentation.menu.MenuScreen
import com.mmfsin.noexcusescompose.presentation.notes.NotesScreen
import com.mmfsin.noexcusescompose.util.BN_CALENDAR_ID
import com.mmfsin.noexcusescompose.util.BN_HOME_ID
import com.mmfsin.noexcusescompose.util.BN_MAXIMUMS_ID
import com.mmfsin.noexcusescompose.util.BN_NOTES_ID
import kotlinx.serialization.Serializable

@Composable
fun NavigationMain() {
    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavItem(id = BN_HOME_ID, name = stringResource(R.string.bottom_nav_home), icon = painterResource(R.drawable.ic_home)),
        BottomNavItem(id = BN_NOTES_ID, name = stringResource(R.string.bottom_nav_notes), icon = painterResource(R.drawable.ic_notes)),
        BottomNavItem(id = BN_CALENDAR_ID, name = stringResource(R.string.bottom_nav_calendar), icon = painterResource(R.drawable.ic_calendar)),
        BottomNavItem(id = BN_MAXIMUMS_ID, name = stringResource(R.string.bottom_nav_maximum), icon = painterResource(R.drawable.ic_maximum)),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = { CustomToolbar({}, false, R.string.app_name) },
        bottomBar = {
            NavigationBar(modifier = Modifier.fillMaxWidth()) {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentDestination == item.id,
                        onClick = {
                            navController.navigate(item.id) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(painter = item.icon, contentDescription = item.name) },
                        label = { MediumText(text = item.name) },
                        alwaysShowLabel = false,
                        colors = NavigationBarItemDefaults.colors()
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BN_HOME_ID,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = BN_HOME_ID) {
                MenuScreen()
            }
            composable(route = BN_NOTES_ID) {
                NotesScreen()
            }
            composable(route = BN_CALENDAR_ID) {
                CalendarScreen()
            }
            composable(route = BN_MAXIMUMS_ID) {
                MaximumsScreen()
            }
        }
    }
}

/** SCREENS */
@Serializable
object Menu

data class BottomNavItem(
    val id: String,
    val name: String,
    val icon: Painter,
)