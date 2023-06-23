package com.clavecillascc.wikinomergeco.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.clavecillascc.wikinomergeco.BottomMenuContent
import com.clavecillascc.wikinomergeco.R
import com.clavecillascc.wikinomergeco.ui.theme.AquaBlue
import com.clavecillascc.wikinomergeco.ui.theme.ButtonBlue
import com.clavecillascc.wikinomergeco.ui.theme.ErasDemiITC
import com.clavecillascc.wikinomergeco.ui.theme.TextWhite
import com.clavecillascc.wikinomergeco.ui.theme.appDarkBlue
import com.clavecillascc.wikinomergeco.ui.theme.appNotSoWhite
import com.clavecillascc.wikinomergeco.ui.theme.appWhite
import com.clavecillascc.wikinomergeco.ui.theme.appWhiteYellow
import com.clavecillascc.wikinomergeco.ui.theme.appYellow

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .background(appNotSoWhite)
            .fillMaxSize()
    ) {
        Column {
            GreetingSection()
            SearchBar()
            //Filter(chips = listOf("", "", ""))
            WordOfTheDay()
            Feedbox2()
        }
    }
}

@Composable
fun BottomNavigationBar(
    items: List<BottomMenuContent>,
    navController: NavController,
    isSelected: Boolean = false,
    activeHighlightColor: Color = ButtonBlue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = AquaBlue,
    onItemClick: (BottomMenuContent) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(
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
                        Icon(
                            painter = painterResource(id = item.iconId),
                            contentDescription = item.title,
                            tint = if (isSelected) activeTextColor else inactiveTextColor,
                            modifier = Modifier.size(30.dp)
                        )
                        Text(
                            text = item.title,
                            color = if(isSelected) activeTextColor else inactiveTextColor,
                            fontSize = 10.sp)
                        }
                    })
                }
        }
    }

@Preview
@Composable
fun GreetingSection() {
    Box(modifier = Modifier
        .background(appDarkBlue)
        .height(50.dp)
        .fillMaxWidth())
    {
        Row(
            //horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Image(painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "app Icon")
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier

            ) {

                Text(
                    text = "  Wikino",
                    color = TextWhite,
                    fontFamily = ErasDemiITC,
                    fontSize = 20.sp
                )
            }

        }
    }
    }
@Composable
fun SearchBar(){
    Box(modifier = Modifier
        .background(appYellow)
        .fillMaxWidth()
        .height(75.dp)) {
        Row(){
            /*
            Box(
                contentAlignment = Alignment.Center){
                Text(text = "Searchbar")
            }
            Icon(painter = painterResource(id = R.drawable.collaboration), contentDescription = "user"
            , Modifier.size(50.dp))
            */

        }
    }
}

@Composable
fun Filter(
    chips: List<String>
) {
    var selectedChipIndex by remember {
        mutableStateOf(0)
    }
    LazyRow {
        items(chips.size) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                    .clickable {
                        selectedChipIndex = it
                    }
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (selectedChipIndex == it) appYellow
                        else appWhite
                    )
                    .padding(15.dp)
            ) {
                Text(text = chips[it], color = TextWhite)
            }
        }
    }
}

@Composable
fun WordOfTheDay(
    color: Color = appWhiteYellow,

) {
    Column(
        modifier = Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .fillMaxWidth()
            .height(200.dp)
    ) {
            //Header
            Text(
                text = "Word of the day",
                style = MaterialTheme.typography.headlineMedium

            )
            //Word of the Day
            Text(
                text = "Naglamis",
                style = MaterialTheme.typography.titleMedium

            )
            //Other terms
            Text(
            text = ""

            )
            //In sentence
            Text(
                text = ""

            )


    }

}

@Composable
fun Feedbox2(
    color: Color = appWhiteYellow
) {
    Column(
        modifier = Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .fillMaxWidth()
            .height(200.dp)
    ) {

        Text(
            text = "",

            )
        Text(
            text = "",

            )


    }

}