package com.clavecillascc.wikinomergeco

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.clavecillascc.wikinomergeco.data.home.HomeViewModel
import com.clavecillascc.wikinomergeco.navigation.Screen
import com.clavecillascc.wikinomergeco.navigation.WikinoMergeCoRouter
import com.clavecillascc.wikinomergeco.screens.CollaboratorScreen
import com.clavecillascc.wikinomergeco.screens.HomeScreen
import com.clavecillascc.wikinomergeco.screens.LibraryScreen
import com.clavecillascc.wikinomergeco.screens.LoginScreen
import com.clavecillascc.wikinomergeco.screens.SignUpScreen
import com.clavecillascc.wikinomergeco.screens.TranslateScreen
import com.clavecillascc.wikinomergeco.ui.theme.ErasDemiITC
import com.clavecillascc.wikinomergeco.ui.theme.WikinoMergeCoTheme
import com.clavecillascc.wikinomergeco.ui.theme.appDarkBlue
import com.clavecillascc.wikinomergeco.ui.theme.appWhite
import com.clavecillascc.wikinomergeco.ui.theme.appYellow
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initialize Firebase
        FirebaseApp.initializeApp(this)

        installSplashScreen()
        setContent {
            WikinoMergeCoTheme {
                    MainScreen()
                }
            }
        }
    }

@Composable
fun Login(homeViewModel: HomeViewModel = viewModel()) {

    homeViewModel.checkForActiveSession()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        if (homeViewModel.isUserLoggedIn.value == true) {
            WikinoMergeCoRouter.navigateTo(Screen.HomeScreen)
        }

        Crossfade(targetState = WikinoMergeCoRouter.currentScreen) { currentState ->
            when (currentState.value) {
                is Screen.SignUpScreen -> {
                    SignUpScreen()
                }

                is Screen.LoginScreen -> {
                    LoginScreen()
                }

                is Screen.HomeScreen -> {
                    HomeScreen()
                }
            }
        }

    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {

    var pressedTime: Long = 0
    val activity = (LocalContext.current as? Activity)
    val context = LocalContext.current
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(coroutineScope, scaffoldState) },
        content = { ContentArea(navController) },
        bottomBar = { BottomNavigationBar(navController) },
        drawerContent = { ModalNavigationDrawer(coroutineScope, scaffoldState) }
    )

    BackPressHandler{
        if (scaffoldState.drawerState.isOpen) {
            coroutineScope.launch {
                scaffoldState.drawerState.close()
            }
        } else {
            if (pressedTime + 2000 > System.currentTimeMillis()) { activity?.finish() }
            else {
                Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
            }
            pressedTime = System.currentTimeMillis()
        }
    }
}

@Composable
fun ContentArea(navController: NavHostController) {
    com.clavecillascc.wikinomergeco.navigation.Navigation(navController = navController)

}

@Composable
fun BottomNavigationBar(navController: NavController) {

    val menuItem = listOf(
        NavigationItems.Home,
        NavigationItems.Translate,
        NavigationItems.Library,
        NavigationItems.Collaborator
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isBottomBarItemDestination = menuItem.any { it.route == currentRoute }

    if (isBottomBarItemDestination) {
        BottomNavigation(backgroundColor = appDarkBlue, contentColor = Color.Black) {
            menuItem.forEach { menuItem ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = menuItem.icon),
                            contentDescription = menuItem.title,
                            modifier = Modifier.size(27.dp)
                        )
                    },
                    label = { Text(text = menuItem.title) },
                    selectedContentColor = appYellow,
                    unselectedContentColor = appWhite,
                    alwaysShowLabel = true,
                    selected = currentRoute == menuItem.route,
                    onClick = {
                        navController.navigate(menuItem.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route = route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun TopBar(coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.app_logo),
                    contentDescription = "Logo",
                Modifier.size(40.dp))
                Text(text = " Wikino", fontSize = 25.sp, fontFamily = ErasDemiITC)
            }
             },
        backgroundColor = appDarkBlue,
        contentColor = Color.White,
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        coroutineScope.launch {
                            scaffoldState.drawerState.open()
                        }
                    },
                tint = Color.White
            )
        }
    )
}

@Composable
fun Drawer(coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        val drawerMenu = listOf("Home", "Settings", "Sign Out")
        val listState = rememberLazyListState()
        LazyColumn(state = listState) {
            items(drawerMenu) { item ->
                Text(
                    text = item,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .clickable {
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                )
            }
        }
    }
}

@Composable
fun ModalNavigationDrawer(coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
        ModalDrawerSheet {

            Divider()
            NavigationDrawerItem(
                label = { Text(text = "Drawer Item") },
                selected = false,
                onClick = { /*TODO*/ }
            )

        }
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItems.Home.route) {

        composable(NavigationItems.Home.route) {
            HomeScreen()
        }

        composable(NavigationItems.Translate.route) {
            TranslateScreen()
        }

        composable(NavigationItems.Library.route) {
            LibraryScreen()
        }

        composable(NavigationItems.Collaborator.route) {
            CollaboratorScreen()
        }

    }
}

@Composable
fun BackPressHandler(
    backPressedDispatcher: OnBackPressedDispatcher? = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)
    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    DisposableEffect(key1 = backPressedDispatcher) {
        backPressedDispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}