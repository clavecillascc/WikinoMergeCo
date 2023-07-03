package com.clavecillascc.wikinomergeco.screens
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.OffsetEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.clavecillascc.wikinomergeco.ui.theme.ErasDemiITC
import com.clavecillascc.wikinomergeco.ui.theme.appWhiteYellow
import com.clavecillascc.wikinomergeco.ui.theme.appYellow

@Composable
fun HomeScreen() {
    /*TODO*/
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        ) {
        Spacer(modifier = Modifier.size(15.dp))
        WordOfTheDay()
        FAQ()
        HomeForum()
    }
}

@Composable
fun WordOfTheDay(
    color: Color = appWhiteYellow,

    ) {
    Column(
        modifier = Modifier
            .padding(horizontal = 18.dp, vertical = 10.dp)
            .shadow(
                shape = RoundedCornerShape(10.dp),
                elevation = 5.dp,
            )
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .fillMaxWidth()
            ,verticalArrangement = Arrangement.SpaceBetween



    ) {
        Column() {
            //Word of the Day
            // Line 1 - Header
            Text(
                text = "Word of the day",
                style = MaterialTheme.typography.headlineMedium,

                )
            Spacer(modifier = Modifier.size(5.dp))
            //Line 2 -Translated Word
            Row() {
                Text(
                    text = "     " + "Naglamis",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "(Ilocano)",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            //Line 3 - Other terms
            Text(
                text = "     "  + "• " + "ang lamig, malamig",
                style = MaterialTheme.typography.titleSmall

            )
            Spacer(modifier = Modifier.size(10.dp))
            //Line 4 - Sentence in same language
            Text(
                text ="       "  + "Naglamiis ti angin!",
                style = MaterialTheme.typography.bodySmall
            )
            //Line 5 - Sentence in chosen language
            Text(
                text = "          "  + "Ang lamig ng hangin!",
                style = MaterialTheme.typography.headlineSmall
            )
            //Line 6 - Sentence in english language

            Text(
                text = "          "  + "The wind is so cold!",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        //See more clickable
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "See more",
                modifier = Modifier.align(alignment = Alignment.End),
                style = MaterialTheme.typography.displaySmall
            )
        }

    }

}

@Composable
fun FAQ(
    color: Color = appWhiteYellow
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 18.dp, vertical = 10.dp)
            .shadow(
                shape = RoundedCornerShape(10.dp),
                elevation = 5.dp,
            )
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .fillMaxWidth()
            .height(200.dp),
            verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column() {
            //Header
            Text(
                text = "FAQ",
                style = MaterialTheme.typography.headlineMedium,

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
        //See more clickable
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "See more",
                modifier = Modifier.align(alignment = Alignment.End),
                style = MaterialTheme.typography.displaySmall,

            )
        }
    }
}

@Composable
fun HomeForum ( color: Color = appWhiteYellow){
    Column(
        modifier = Modifier
            .padding(start = 18.dp, top = 10.dp, end = 18.dp, bottom = 80.dp)
            .shadow(
                shape = RoundedCornerShape(10.dp),
                elevation = 5.dp,
            )
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .fillMaxWidth()
            .height(200.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column() {
            //Header
            Text(
                text = "Forum",
                style = MaterialTheme.typography.headlineMedium,

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
        //See more clickable
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "See more",
                modifier = Modifier.align(alignment = Alignment.End),
                style = MaterialTheme.typography.displaySmall,

                )
        }
    }
}

