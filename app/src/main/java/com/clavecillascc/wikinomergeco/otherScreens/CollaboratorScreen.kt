package com.clavecillascc.wikinomergeco.otherScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clavecillascc.wikinomergeco.R
import com.clavecillascc.wikinomergeco.ui.theme.appDarkBlue
import com.clavecillascc.wikinomergeco.ui.theme.appWhite
import com.clavecillascc.wikinomergeco.ui.theme.appWhiteYellow
import com.clavecillascc.wikinomergeco.ui.theme.appYellow
import com.clavecillascc.wikinomergeco.ui.theme.dividerColor
import com.clavecillascc.wikinomergeco.ui.theme.normalBlack
import com.clavecillascc.wikinomergeco.ui.theme.notSelectedGray
import com.clavecillascc.wikinomergeco.ui.theme.selectedGray
import com.clavecillascc.wikinomergeco.ui.theme.textHeaderBlack
import com.clavecillascc.wikinomergeco.ui.theme.textSeeMore

@Composable
fun CollaboratorScreen() {
    Column (modifier = Modifier
        .verticalScroll(rememberScrollState())){
        CollaboratorFilter()
        Spacer(modifier = Modifier.size(15.dp))
        RecentlyAdded()
        Spacer(modifier = Modifier.size(75.dp))

    }
    Column(modifier = Modifier.fillMaxWidth().height(650.dp).
        padding(15.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End) {
        AddTranslation()
    }
}

@Composable
fun CollaboratorFilter(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .background(appYellow))
    {
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
            verticalArrangement = Arrangement.Center) {
            Spacer(Modifier.size(5.dp))
            Text(text = "    Available Laungages:", color = appWhite)
            Filter(chips = listOf("Tagalog", "Cebuano", "Bicolano"))
        }
    }
}

@Composable
fun Filter(
    chips: List<String>,

    ) {
    var selectedChipIndex by remember {
        mutableStateOf(0)
    }
    LazyRow {
        items(chips.size) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 15.dp, top = 10.dp, bottom = 10.dp)
                    .clickable {
                        selectedChipIndex = it
                    }
                    .clip(RoundedCornerShape(5.dp))
                    .background(
                        if (selectedChipIndex == it) selectedGray
                        else notSelectedGray
                    )
                    .padding(start = 10.dp, top = 5.dp, bottom = 5.dp, end = 10.dp)
            ) {
                Text(text = chips[it], color = textHeaderBlack, fontSize = 12.sp)
            }
        }
    }
}
@Preview
@Composable
fun RecentlyAdded(
    color: Color = appWhiteYellow
) {
    Text(
        text = "Recently Added:",
        style = MaterialTheme.typography.titleSmall,
        fontSize = 18.sp,
        color = textSeeMore,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 20.dp)

        )
    Column(

    ) {
        UserContribution()
        UserContribution()
        Spacer(modifier = Modifier.size(70.dp))
    }
}
@Composable
fun UserContribution (
    color: Color = appWhiteYellow){
    Column(modifier = Modifier
        .padding(horizontal = 18.dp, vertical = 10.dp)
        .shadow(
            shape = RoundedCornerShape(10.dp),
            elevation = 5.dp,
        )
        .clip(RoundedCornerShape(10.dp))
        .background(color)
        .padding(horizontal = 15.dp, vertical = 15.dp)
        .fillMaxWidth()
        //.height(200.dp),
        ,verticalArrangement = Arrangement.SpaceBetween) {
        Row() {
            Image(
                painter = painterResource(id = R.drawable.profilepic_sample),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(width = 1.dp, color = normalBlack, CircleShape))
            Column() {
                Text(text = "Username", Modifier.padding(horizontal = 20.dp),
                    style = MaterialTheme.typography.labelMedium )
                Text(text = "user details", Modifier.padding(horizontal = 20.dp),
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 12.sp
                )
            }
        }
        //collaborator added word
        Column(
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 20.dp)
                .shadow(
                    shape = RoundedCornerShape(10.dp),
                    elevation = 5.dp,
                )
                .clip(RoundedCornerShape(10.dp))
                .background(color = appWhite)
                .padding(horizontal = 15.dp, vertical = 15.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Translation()
        }

        Column {
            Divider(color = dividerColor, thickness = 2.dp)
            Spacer(modifier = Modifier.size(5.dp))
            //See more clickable
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                Row(modifier = Modifier.height(20.dp)
                ) {
                    IconButton(onClick = { /*TODO*/ }, Modifier.size(30.dp)) {
                        Icon(painter = painterResource(id = R.drawable.follow_icon),
                            contentDescription = "follow", Modifier.size(15.dp))
                    }
                    Text(
                        text = "Follow",
                        style = MaterialTheme.typography.labelSmall)
                }

                Row(modifier = Modifier.height(20.dp)
                ) {
                    IconButton(onClick = { /*TODO*/ }, Modifier.size(30.dp)) {
                        Icon(painter = painterResource(id =R.drawable.chat_light),
                            contentDescription = "comment", Modifier.size(20.dp))
                    }
                    Text(
                        text = "Comment",
                        style = MaterialTheme.typography.labelSmall)
                }

                Row(modifier = Modifier.height(20.dp)
                ) {
                    IconButton(onClick = { /*TODO*/ }, Modifier.size(20.dp)) {
                        Icon(painter = painterResource(R.drawable.upvote_icon),
                            contentDescription = "upvote", Modifier.size(10.dp))}

                    IconButton(onClick = { /*TODO*/ }, Modifier.size(20.dp)) {
                        Icon(painter = painterResource(R.drawable.downvote_icon),
                            contentDescription = "downvote", Modifier.size(10.dp))}


                }

            }
        }


    }
}

@Composable
fun sdasda (){
    Button(modifier = Modifier
        .size(height = 110.dp, width = 92.dp)
        .defaultMinSize(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues(0.dp),
        elevation = null,
        onClick = { /*TODO*/ }) {
        Image(
            painter = painterResource(id = R.drawable.add_button),
            contentDescription = "Ilocano",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
        )
    }
}

@Composable
fun AddTranslation (){

    FloatingActionButton(
        onClick = {/*TODO*/},
        backgroundColor = appDarkBlue,
        contentColor = appYellow
    ){
        Icon(Icons.Filled.Add,"AddButton",
            modifier = Modifier.size(30.dp))
    }
}

