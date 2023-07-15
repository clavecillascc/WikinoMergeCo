package com.clavecillascc.wikinomergeco.signin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clavecillascc.wikinomergeco.MainActivity
import com.clavecillascc.wikinomergeco.R
import com.clavecillascc.wikinomergeco.navigation.NavigationItems
import com.clavecillascc.wikinomergeco.ui.theme.appDarkBlue
import com.clavecillascc.wikinomergeco.ui.theme.appWhite
import com.clavecillascc.wikinomergeco.ui.theme.appWhiteYellow
import com.clavecillascc.wikinomergeco.ui.theme.appYellow

@Composable
fun SignInScreen(
    state: SignInState,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(appDarkBlue)
        .padding(top = 80.dp),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        //Welcome message & logo
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Welcome to\n Wikino!",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayMedium
            )
            Spacer(modifier = Modifier.size(25.dp))

            Image(painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "Logo",
                Modifier.size(250.dp))

            Spacer(modifier = Modifier.size(30.dp))
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            //sign-in
            Button(modifier = Modifier
                .size(height = 40.dp, width = 300.dp)
                .defaultMinSize(), colors = ButtonDefaults.buttonColors(
                backgroundColor = appYellow),
                contentPadding = PaddingValues(0.dp),
                onClick = onSignInClick) {
                Text(text = "SIGN IN",
                    style = MaterialTheme.typography.displayMedium,
                    color = appWhite,
                    fontSize = 14.sp) }

            Spacer(modifier = Modifier.size(15.dp))
            //sign-up
            Button(modifier = Modifier
                .size(height = 40.dp, width = 300.dp)
                .defaultMinSize(), colors = ButtonDefaults.buttonColors(
                backgroundColor = appWhite),
                contentPadding = PaddingValues(0.dp),
                onClick = { /*TODO*/ }) {
                Row() {
                    Text(text = "NO ACCOUNT YET?",
                        style = MaterialTheme.typography.displayMedium,
                        color = appDarkBlue,
                        fontSize = 14.sp)

                    Text(text = " SIGN UP",
                        style = MaterialTheme.typography.displayMedium,
                        color = appYellow,
                        fontSize = 14.sp) } }
            //skip
            TextButton(modifier = Modifier
                .size(height = 40.dp, width = 300.dp)
                .defaultMinSize(),
                contentPadding = PaddingValues(0.dp),
                onClick = { /*TODO*/ }) {
                Text(text = "skip for now >",
                    style = MaterialTheme.typography.displayMedium,
                    color = appWhite,
                    fontSize = 14.sp)
            }
        }
    }
}