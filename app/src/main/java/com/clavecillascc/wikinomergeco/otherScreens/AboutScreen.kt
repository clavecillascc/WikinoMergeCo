package com.clavecillascc.wikinomergeco.otherScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.clavecillascc.wikinomergeco.R
import com.clavecillascc.wikinomergeco.ui.theme.ErasDemiITC
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun AboutScreen(navController: NavController) {
    Surface() {
        Column(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Image(painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "Logo",
                Modifier.size(200.dp))
        }
    }
}