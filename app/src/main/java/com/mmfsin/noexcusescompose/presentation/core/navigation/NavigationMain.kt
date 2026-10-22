package com.mmfsin.noexcusescompose.presentation.core.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.presentation.calendar.CalendarScreen
import com.mmfsin.noexcusescompose.presentation.core.components.CustomMainToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.theme.BlueMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.RedHard
import com.mmfsin.noexcusescompose.presentation.menu.MenuScreen
import com.mmfsin.noexcusescompose.presentation.notes.NotesScreen
import com.mmfsin.noexcusescompose.presentation.rms.rms.RmsScreen
import com.mmfsin.noexcusescompose.presentation.stretching.StretchScreen
import com.mmfsin.noexcusescompose.util.BN_HOME_ID
import com.mmfsin.noexcusescompose.util.BN_RMS_ID
import com.mmfsin.noexcusescompose.util.BN_NOTES_ID
import com.mmfsin.noexcusescompose.util.BN_STRETCH_ID

@Preview
@Composable
fun NavigationMain() {
    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavItem(id = BN_HOME_ID, name = stringResource(R.string.bottom_nav_home), icon = painterResource(R.drawable.ic_home)),
        BottomNavItem(id = BN_STRETCH_ID, name = stringResource(R.string.bottom_nav_stretch), icon = painterResource(R.drawable.ic_stretching)),
        BottomNavItem(id = BN_RMS_ID, name = stringResource(R.string.bottom_nav_maximum), icon = painterResource(R.drawable.ic_maximum)),
        BottomNavItem(id = BN_NOTES_ID, name = stringResource(R.string.bottom_nav_notes), icon = painterResource(R.drawable.ic_notes)),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            CustomMainToolbar(
                onRightIconClick = {}
            )
        },
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
                        label = null,
                        alwaysShowLabel = false,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = BlueMedium
                        )
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
            composable(route = BN_STRETCH_ID) {
                StretchScreen()
            }
            composable(route = BN_RMS_ID) {
                RmsScreen()
            }
        }
    }
}

data class BottomNavItem(
    val id: String,
    val name: String,
    val icon: Painter,
)