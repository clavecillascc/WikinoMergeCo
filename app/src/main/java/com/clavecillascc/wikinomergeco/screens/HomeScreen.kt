package com.clavecillascc.wikinomergeco.screens
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.OffsetEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.clavecillascc.wikinomergeco.ui.theme.appWhiteYellow
import com.clavecillascc.wikinomergeco.ui.theme.appYellow
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

@Composable
fun HomeScreen() {
    /*TODO*/
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        ) {
        Spacer(modifier = Modifier.size(15.dp))
        WordOfTheDay()
        Feedbox2()
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

    val context = LocalContext.current
    val sharedPreferences = remember(context) {
        PreferenceManager.getDefaultSharedPreferences(context)
    }
    val lastRetrievalTime = sharedPreferences.getLong("lastRetrievalTime", 0L)
    val previousFileKey = sharedPreferences.getString("previousFileKey", null)

    LaunchedEffect(Unit) {
        val currentTime = System.currentTimeMillis()
        val oneDayInMillis = TimeUnit.DAYS.toMillis(1)

        if (currentTime - lastRetrievalTime >= oneDayInMillis || previousFileKey == null) {
            // Retrieve a list of all files in the desired folder
            val files = withContext(Dispatchers.IO) {
                storageRef.child("Words/").listAll().await().items
            }

            if (files.isNotEmpty()) {
                // Exclude the previously selected file, if any
                val filteredFiles = if (previousFileKey != null) {
                    files.filter { it.name != previousFileKey }
                } else {
                    files
                }

                if (filteredFiles.isNotEmpty()) {
                    // Choose a random file from the filtered list
                    val randomFile = filteredFiles.random()

                    try {
                        // Download the content of the random file
                        val bytes = withContext(Dispatchers.IO) {
                            randomFile.getBytes(ONE_MEGABYTE).await()
                        }

                        val text = String(bytes)
                        word.value = text
                        Log.d("WordOfTheDay", "Word retrieved: $text")

                        // Store the random file key, previous file key, and last retrieval time in SharedPreferences
                        sharedPreferences.edit {
                            putString("randomFileKey", randomFile.name)
                            putString("previousFileKey", randomFile.name)
                            putLong("lastRetrievalTime", currentTime)
                        }
                    } catch (e: Exception) {
                        Log.e("WordOfTheDay", "Error downloading file: ${e.message}")
                    }
                }
            }
        } else {
            // Retrieve the previously stored word value
            val storedWord = sharedPreferences.getString("word", "")
            if (!storedWord.isNullOrEmpty()) {
                word.value = storedWord
                Log.d("WordOfTheDay", "Word retrieved from SharedPreferences: $storedWord")
            }
        }
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
fun Feedbox2(
    color: Color = appWhiteYellow
) {
    Column(
        modifier = Modifier
            .padding(15.dp)
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

        Text(
            text = "",

            )
        Text(
            text = "",

            )


    }

}
