package com.clavecillascc.wikinomergeco.mainscreen

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.BottomSheetDefaults.ContainerColor
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.clavecillascc.wikinomergeco.R
import com.clavecillascc.wikinomergeco.navigation.NavigationItems
import com.clavecillascc.wikinomergeco.otherScreens.CollaboratorScreen
import com.clavecillascc.wikinomergeco.signin.UserData
import com.clavecillascc.wikinomergeco.ui.theme.ErasDemiITC
import com.clavecillascc.wikinomergeco.ui.theme.TextWhite
import com.clavecillascc.wikinomergeco.ui.theme.appDarkBlue
import com.clavecillascc.wikinomergeco.ui.theme.appWhite
import com.clavecillascc.wikinomergeco.ui.theme.appYellow
import com.clavecillascc.wikinomergeco.ui.theme.selectedGray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    userData: UserData?,
    onSignOut: () -> Unit
) {

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
        drawerContent = { Drawer(coroutineScope, scaffoldState, userData, navController, onSignOut)}
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
fun Drawer(coroutineScope: CoroutineScope, scaffoldState: ScaffoldState, userData: UserData?, navController: NavController, onSignOut: () -> Unit) {
    Column(modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Header(userData = userData)
        val menuItem = listOf(
            NavigationItems.Collaborator,
            NavigationItems.About
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        ModalDrawerSheet(
            drawerShape = RectangleShape,
            drawerContainerColor = Color.White
        )
        {
            menuItem.forEach { menuItem ->
                Divider()
                    NavigationDrawerItem(
                        shape = RectangleShape,
                        icon = {
                            Icon(
                                painter = painterResource(id = menuItem.icon),
                                contentDescription = menuItem.title,
                                modifier = Modifier.size(27.dp)
                            )
                        },
                        label = { Text(text = menuItem.title) },
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
                                coroutineScope.launch {
                                    scaffoldState.drawerState.close()
                                }
                            }
                        }
                    )
            }
            Divider()
            NavigationDrawerItem(
                shape = RectangleShape,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.logout),
                        contentDescription = "logout",
                        modifier = Modifier.size(18.dp)
                    )
                },
                label = { Text(text = "Sign out") },
                selected = false,
                onClick = onSignOut
            )
            Divider()
        }
    }
}


@Composable
fun Header(userData: UserData?) {
    Row(modifier = Modifier.padding(20.dp))
    {
        if (userData?.profilePictureUrl != null) {
            AsyncImage(
                model = userData.profilePictureUrl,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(20.dp))
        }
        if (userData?.username != null) {
            Text(
                text = userData.username,
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun BackPressHandler(
    backPressedDispatcher: OnBackPressedDispatcher? = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit) {
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
