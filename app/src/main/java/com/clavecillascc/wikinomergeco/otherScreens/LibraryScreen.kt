package com.clavecillascc.wikinomergeco.otherScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.clavecillascc.wikinomergeco.R
import com.clavecillascc.wikinomergeco.ui.theme.appWhiteYellow

@Composable
fun LibraryScreen() {
    /*TODO*/
    //Text(text = "Library")
    Column {
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
    ) {
        Text(text = "Available Languages:")
        Spacer(modifier = Modifier.size(20.dp))

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .height(220.dp)
                .padding(horizontal = 10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(modifier = Modifier
                    .size(height = 100.dp, width = 90.dp)
                    .defaultMinSize(), colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                ),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { /*TODO*/ }
                ) {
                    //Text(text = "Tagalog")
                    Image(
                        painter = painterResource(id = R.drawable.button_tagalog),
                        contentDescription = "Tagalog",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxSize()
                    )
                }
                Button(modifier = Modifier
                    .size(height = 100.dp, width = 90.dp)
                    .defaultMinSize(), colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                ),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { /*TODO*/ }
                ) {
                    //Text(text = "Tagalog")
                    Image(
                        painter = painterResource(id = R.drawable.button_cebuano),
                        contentDescription = "Tagalog",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxSize()
                    )
                }

                Button(modifier = Modifier
                    .size(height = 100.dp, width = 90.dp)
                    .defaultMinSize(), colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                ),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { /*TODO*/ }
                ) {
                    //Text(text = "Tagalog")
                    Image(
                        painter = painterResource(id = R.drawable.button_ilocano),
                        contentDescription = "Tagalog",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}
