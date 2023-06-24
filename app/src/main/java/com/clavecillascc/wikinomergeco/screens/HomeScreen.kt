package com.clavecillascc.wikinomergeco.screens
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.unit.dp
import com.clavecillascc.wikinomergeco.ui.theme.appWhiteYellow

@Composable
fun HomeScreen() {
    //TODO
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        ) {
        WordOfTheDay()
        Feedbox2()
        WordOfTheDay()
        WordOfTheDay()
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
            text = "Sample",
            style = MaterialTheme.typography.headlineMedium

        )
        //Word of the Day
        Text(
            text = "",
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
