package com.clavecillascc.wikinomergeco.otherScreens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.clavecillascc.wikinomergeco.R
import com.clavecillascc.wikinomergeco.collaboratorscreen.UploadData
import com.clavecillascc.wikinomergeco.mainscreen.Header
import com.clavecillascc.wikinomergeco.signin.UserData
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CollaboratorScreen(navController: NavController, userData: UserData?) {

    val ctx = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navController.navigate("addCollaborator")},
                backgroundColor = appDarkBlue,
                modifier = Modifier
                    .padding(top = 15.dp, start = 15.dp, bottom = 15.dp, end = 5.dp)
                    .padding(bottom = 35.dp)
            ){
                Icon(Icons.Filled.Add,"AddButton",
                    modifier = Modifier.size(30.dp), tint = appYellow)
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.size(15.dp))
                RecentlyAdded(userData = userData)
                Spacer(modifier = Modifier.size(75.dp))
            }
        }
    )

}

@Composable
fun RecentlyAdded(
    color: Color = appWhiteYellow, userData: UserData?
) {
    Text(
        text = "Recently Added:",
        style = MaterialTheme.typography.titleSmall,
        fontSize = 18.sp,
        color = textSeeMore,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 20.dp)
    )
    Column {
        // Iterate over the recently added contributions and display them
        UploadData.recentlyAddedContributions.forEachIndexed { index, contribution ->
            UserContribution(contribution, index % 2 == 0, userData = userData) // Pass the contribution and a boolean to alternate background color
        }
        Spacer(modifier = Modifier.size(70.dp))
    }
}

@Composable
fun UserContribution(
    contribution: String,
    isEven: Boolean,
    userData: UserData?
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 18.dp, vertical = 10.dp)
            .shadow(
                shape = RoundedCornerShape(10.dp),
                elevation = 5.dp,
            )
            .clip(RoundedCornerShape(10.dp))
            .background(if (isEven) appWhiteYellow else appWhite)
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        HeaderCollab(userData = userData)
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
            Text(
                text = contribution,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun HeaderCollab(userData: UserData?) {
    Row(modifier = Modifier.padding(20.dp))
    {
        if (userData?.profilePictureUrl != null) {
            AsyncImage(
                model = userData.profilePictureUrl,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(20.dp))
        }
        if (userData?.username != null) {
            androidx.compose.material.Text(
                text = userData.username,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}