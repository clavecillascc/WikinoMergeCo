package com.clavecillascc.wikinomergeco.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clavecillascc.wikinomergeco.R
import com.clavecillascc.wikinomergeco.ui.theme.ErasDemiITC
import com.clavecillascc.wikinomergeco.ui.theme.TextWhite
import com.clavecillascc.wikinomergeco.ui.theme.appDarkBlue

@Composable
fun HomeScreen() {
    GreetingSection()
}

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
