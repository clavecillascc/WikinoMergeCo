package com.clavecillascc.wikinomergeco

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.clavecillascc.wikinomergeco.screens.CollaboratorScreen
import com.clavecillascc.wikinomergeco.screens.DictionaryScreen
import com.clavecillascc.wikinomergeco.screens.HomeScreen
import com.clavecillascc.wikinomergeco.screens.TranslateScreen
import com.clavecillascc.wikinomergeco.ui.theme.appDarkBlue
import com.clavecillascc.wikinomergeco.ui.theme.appWhite
import com.clavecillascc.wikinomergeco.ui.theme.appYellow

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable(route = "home"){
            HomeScreen()
        }
        composable(route = "translate"){
            TranslateScreen()
        }
        composable(route = "dictionary"){
            DictionaryScreen()
        }
        composable(route = "collaborator"){
            CollaboratorScreen()
        }
    }
}

@Composable
fun BottomNavigationBar(
    items: List<BottomMenuContent>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomMenuContent) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(
        modifier = modifier,
        containerColor = appDarkBlue,
        tonalElevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = appYellow,
                    unselectedIconColor = appWhite
                ),
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        Icon(imageVector = item.icon, contentDescription = item.name)
                        if (selected) {
                            Text(text = item.name, textAlign = TextAlign.Center, fontSize = 10.sp)
                        }
                    }
                }
            )
        }
    }
}
