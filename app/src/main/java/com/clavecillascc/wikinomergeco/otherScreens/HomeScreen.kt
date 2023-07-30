package com.clavecillascc.wikinomergeco.otherScreens
import android.util.Log
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import androidx.preference.PreferenceManager
import com.clavecillascc.wikinomergeco.ui.theme.appWhiteYellow
import com.clavecillascc.wikinomergeco.ui.theme.appYellow
import com.clavecillascc.wikinomergeco.ui.theme.textOtherTerms
import com.clavecillascc.wikinomergeco.ui.theme.textSentence
import com.clavecillascc.wikinomergeco.ui.theme.textTerm
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

@Composable
fun HomeScreen(navController: NavHostController) {
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
fun WordOfTheDay(color: Color = appWhiteYellow) {
    val word = rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val sharedPreferences = remember(context) {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    // Function to retrieve a random word from Firebase Storage
    suspend fun getRandomWord() {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("Words/")

        try {
            // Retrieve a list of all file names in the desired folder
            val fileNames = storageRef.listAll().await().items.map { it.name }

            val previousFileId = sharedPreferences.getString("previousFileId", null)
            val filteredFileNames = if (previousFileId != null) {
                fileNames.filter { it != previousFileId }
            } else {
                fileNames
            }

            if (filteredFileNames.isNotEmpty()) {
                // Shuffle the list to get a random file
                val shuffledFileNames = filteredFileNames.shuffled()
                val randomFileName = shuffledFileNames.first()

                // Download the content of the random file
                val ONE_MEGABYTE: Long = 1024 * 1024
                val bytes = storageRef.child(randomFileName).getBytes(ONE_MEGABYTE).await()

                word.value = String(bytes)

                // Store the random file ID and last retrieval time in SharedPreferences
                val currentTime = System.currentTimeMillis()
                sharedPreferences.edit {
                    putString("previousFileId", randomFileName)
                    putLong("lastRetrievalTime", currentTime)
                }
            }
        } catch (e: Exception) {
            Log.e("WordOfTheDay", "Error retrieving or downloading file: ${e.message}")
        }
    }

    // LaunchedEffect to call getRandomWord() when the composable is first displayed or when a day has passed
    LaunchedEffect(Unit) {
        val currentTime = System.currentTimeMillis()
        val lastRetrievalTime = sharedPreferences.getLong("lastRetrievalTime", 0L)
        val oneDayInMillis = TimeUnit.DAYS.toMillis(1)

        if (currentTime - lastRetrievalTime >= oneDayInMillis) {
            getRandomWord()
        } else {
            // Retrieve the previously stored word value
            val storedWord = sharedPreferences.getString("word", "")
            if (!storedWord.isNullOrEmpty()) {
                word.value = storedWord
            }
        }
    }

    // DisposableEffect to save the current word value when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            sharedPreferences.edit {
                putString("word", word.value)
            }
        }
    }

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
        val lines = word.value.lines()

        Text(
            text = "Word of the day",
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.size(5.dp))
        if (lines.size >= 6) {
            Text(
                text = lines[1],
                style = MaterialTheme.typography.titleMedium,
                color = textTerm
            )
            Text(
                text = lines[2],
                style = MaterialTheme.typography.titleMedium,
                color = appYellow
            )
            Text(
                text = lines[3],
                style = MaterialTheme.typography.titleMedium,
                color = textOtherTerms
            )
            Text(
                text = lines[4],
                style = MaterialTheme.typography.headlineSmall,
                color = textTerm
            )
            Text(
                text = lines[5],
                style = MaterialTheme.typography.headlineSmall,
                color = textSentence
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
