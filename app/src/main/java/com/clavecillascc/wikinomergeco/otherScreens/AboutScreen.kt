package com.clavecillascc.wikinomergeco.otherScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.clavecillascc.wikinomergeco.R
import com.clavecillascc.wikinomergeco.ui.theme.ErasDemiITC
import com.clavecillascc.wikinomergeco.ui.theme.appWhite
import com.clavecillascc.wikinomergeco.ui.theme.appYellow
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun AboutScreen(navController: NavController) {
    Column {
        AboutHeaderBox(navController = navController)
        AppLogo()
        Divider()
        Column () {
            Company()
            Divider()
            GroupMembers()
            Divider()
            Project()
            Divider()
        }
    }
}

@Composable
fun AboutHeaderBox(navController: NavController) {
    Box(
        modifier = Modifier
            .background(appYellow)
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Icon",
                    tint = Color.White
                )
            }
            Text(
                text = "About",
                style = MaterialTheme.typography.labelMedium,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = appWhite
            )
        }
    }
}

@Composable
fun AppLogo() {
    Image(painter = painterResource(id = R.drawable.app_logo),
        contentDescription = "Logo",
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp
            )
            .size(200.dp)
    )
}

@Composable
fun Company() {
    Column(Modifier.padding(horizontal = 20.dp)) {
        androidx.compose.material3.Text(
            text = "Company Name: Merge Co",
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}

@Composable
fun GroupMembers() {
    Column(Modifier.padding(horizontal = 20.dp)) {
        androidx.compose.material3.Text(
            text = "Group Member:\n" +
                    "    Bonnet, Jerus Patrick S.\n" +
                    "    Clavecillas, Christian C.\n" +
                    "    De Padua, Angelo Miguel N.\n" +
                    "    Roman, Alexis Nicole B.",
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}

@Composable
fun Project() {
    Column(Modifier.padding(horizontal = 20.dp)) {
        androidx.compose.material3.Text(
            text = "Project Name: Wikino",
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}
