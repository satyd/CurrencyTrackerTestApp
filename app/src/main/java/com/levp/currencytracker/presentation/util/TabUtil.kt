package com.levp.currencytracker.presentation.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.levp.currencytracker.ui.theme.clMainBackground
import com.levp.currencytracker.ui.theme.clSelectedItem
import com.levp.currencytracker.ui.theme.clText
import com.levp.currencytracker.ui.theme.clTextUnselected

data class TabBarItem(
    val title: String,
    val destination: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@Composable
fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    title: String,
) {
    Box() {
        Icon(
            imageVector = if (isSelected) {
                selectedIcon
            } else {
                unselectedIcon
            },
            contentDescription = title,
            tint = Color.Unspecified
        )
    }
}

@Composable
fun TabView(
    tabBarItems: List<TabBarItem>,
    navController: NavController,
) {
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar(
        modifier = Modifier
            .background(color = clMainBackground)
            .fillMaxWidth(),
        containerColor = clMainBackground
    ) {
        tabBarItems.forEachIndexed { index, tabBarItem ->
            val isSelected = selectedTabIndex == index
            val textColor = if (isSelected) clText else clTextUnselected

            NavigationBarItem(
                modifier = Modifier.background(color = clMainBackground),
                selected = isSelected,
                onClick = {
                    selectedTabIndex = index
                    navController.navigate(tabBarItem.destination) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    TabBarIconView(
                        isSelected = isSelected,
                        selectedIcon = tabBarItem.selectedIcon,
                        unselectedIcon = tabBarItem.unselectedIcon,
                        title = tabBarItem.title,
                    )
                },
                label = {
                    Text(
                        text = tabBarItem.title,
                        style = TextStyle(
                            color = textColor
                        )
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = clSelectedItem,
                    unselectedIconColor = clMainBackground
                )
            )
        }
    }
}