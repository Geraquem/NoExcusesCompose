package com.mmfsin.noexcuses.presentation.core.navigation

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.presentation.core.components.CustomMainToolbar
import com.mmfsin.noexcuses.presentation.core.theme.BlueMedium
import com.mmfsin.noexcuses.presentation.menu.MenuScreen
import com.mmfsin.noexcuses.presentation.notes.NotesScreen
import com.mmfsin.noexcuses.presentation.rms.rms.RmsScreen
import com.mmfsin.noexcuses.presentation.stretching.StretchScreen
import com.mmfsin.noexcuses.util.BN_HOME_ID
import com.mmfsin.noexcuses.util.BN_NOTES_ID
import com.mmfsin.noexcuses.util.BN_RMS_ID
import com.mmfsin.noexcuses.util.BN_STRETCH_ID

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