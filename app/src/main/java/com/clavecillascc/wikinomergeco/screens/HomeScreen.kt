package com.clavecillascc.wikinomergeco.screens

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.clavecillascc.wikinomergeco.ui.theme.appWhiteYellow
import com.clavecillascc.wikinomergeco.ui.theme.appYellow
import com.clavecillascc.wikinomergeco.ui.theme.textOtherTerms
import com.clavecillascc.wikinomergeco.ui.theme.textSentence
import com.clavecillascc.wikinomergeco.ui.theme.textTerm
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen() {
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.size(15.dp))
        WordOfTheDay()
        WordOfTheDayUI()
        FAQ()
        HomeForum()

    }
}
@Composable
fun WordOfTheDay(
    color: Color = appWhiteYellow,
) {
    val word = remember { mutableStateOf("") }

    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference
    val ONE_MEGABYTE: Long = 1024 * 1024

    LaunchedEffect(Unit) {
        val currentTime = System.currentTimeMillis()
        val oneDayInMillis = TimeUnit.DAYS.toMillis(1)

        if (currentTime - lastRetrievalTime >= oneDayInMillis || previousFileKey == null) {
            // Retrieve a list of all files in the desired folder
            val files = withContext(Dispatchers.IO) {
                storageRef.child("Words/").listAll().await().items
            }

        // Choose a random file from the list
        val randomFile = files.random()

        // Download the content of the random file
        val bytes = withContext(Dispatchers.IO) {
            randomFile.getBytes(ONE_MEGABYTE).await()
        }

        val text = String(bytes)
        word.value = text
    }
    DisposableEffect(word.value) {
        onDispose {
            // Save the current word value when the composable is disposed
            sharedPreferences.edit {
                putString("word", word.value)
            }
        }
    }
    // Define the header, body, and other text styles
    val headerStyle = TextStyle(
        fontSize = 16.sp,
        color = Color.Blue
    )
    val bodyStyle = TextStyle(
        fontSize = 12.sp,
        color = Color.Yellow
    )
    val otherTextStyle = TextStyle(
        fontSize = 11.sp,
        color = Color.Blue
    )
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
            .height(200.dp)
    ) {
        // Extract the header text
        val headerText = word.value.substringBefore("\n")
        // Apply headerStyle to the header text
        Text(
            text = headerText,
            style = headerStyle
        )
        // Extract the body text
        val bodyText = word.value.substringAfter("\n").substringBeforeLast("\n")
        // Apply bodyStyle to the body text
        Text(
            text = bodyText,
            style = bodyStyle
        )
        // Extract the intermediate lines
        val intermediateLines = word.value
            .substringAfter("\n")
            .substringAfterLast("\n")
            .substringBeforeLast("\n\t")
            .trimIndent()
            .split("\n")

        // Display intermediate lines
        intermediateLines.forEachIndexed { index, line ->
            if (index < intermediateLines.size - 1) {
                Text(
                    text = line,
                    style = otherTextStyle,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
        // Extract the other text
        val otherText = word.value.substringAfterLast("\n\t")
        // Apply otherTextStyle to the other text
        Text(
            text = otherText,
            style = otherTextStyle,
            modifier = Modifier.padding(start = 16.dp)
        )
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
        Column {
            //Header
            Text(
                text = "FAQ",
                style = MaterialTheme.typography.headlineMedium,
            )
            //Word of the Day
            Text(
                text = "   "+"• "+"sample 1",
                style = MaterialTheme.typography.labelMedium
            )
            //Other terms
            Text(
                text = "   "+"• "+"sample 2",
                style = MaterialTheme.typography.labelMedium
            )
            //In sentence
            Text(
                text = "   "+"• "+"sample 3",
                style = MaterialTheme.typography.labelMedium
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
        Column {
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

@Composable
fun Translation(color: Color = appWhiteYellow){
    Column {
        //Line 2 -Translated Word
        Row {
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
}

@Composable
fun WordOfTheDayUI(
    color: Color = appWhiteYellow,
) {
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
        //.height(200.dp)
    ) {
        Column {
            //Word of the Day
            // Line 1 - Header
            Text(
                text = "Word of the day",
                style = MaterialTheme.typography.headlineMedium,

                )
            Spacer(modifier = Modifier.size(5.dp))
            Translation()
            }
        }
    }
