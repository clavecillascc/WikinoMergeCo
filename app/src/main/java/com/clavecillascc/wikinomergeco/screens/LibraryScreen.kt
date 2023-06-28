package com.clavecillascc.wikinomergeco.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.clavecillascc.wikinomergeco.ui.theme.appWhiteYellow

@Composable
fun LibraryScreen() {
    /*TODO*/
    //Text(text = "Library")
    Column() {
        Spacer(modifier = Modifier.size(15.dp))
        Languages()
    }
}

@Composable
fun Languages (color: Color = appWhiteYellow, ) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .shadow(
                shape = RoundedCornerShape(10.dp),
                elevation = 5.dp,
            )
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .padding(horizontal = 15.dp, vertical = 20.dp)

            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        Text(text = "Available Languages:")
        Spacer(modifier = Modifier.size(20.dp))

        Column(verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .height(150.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Tagalog")

                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Bisaya")
                }

                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Ilocano")
                }
            }

            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Waray")

                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Cebuano")
                }

                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Tausug")
                }
            }
        }
        }
}