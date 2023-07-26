package com.clavecillascc.wikinomergeco.libraryscreen

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.clavecillascc.wikinomergeco.ui.theme.appWhite
import com.clavecillascc.wikinomergeco.ui.theme.appWhiteYellow
import com.clavecillascc.wikinomergeco.ui.theme.appYellow
import com.clavecillascc.wikinomergeco.ui.theme.colorIlocano
import com.clavecillascc.wikinomergeco.ui.theme.darkerdividerColor
import com.clavecillascc.wikinomergeco.ui.theme.normalBlack
import com.clavecillascc.wikinomergeco.ui.theme.textOtherTerms
import com.clavecillascc.wikinomergeco.ui.theme.textSentence
import com.clavecillascc.wikinomergeco.ui.theme.textTerm
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL

@Composable
fun IlocanoScreen(navController: NavHostController) {
    val wordsState = remember { mutableStateOf<List<WordItem>?>(null) }
    val selectedWordContent = remember { mutableStateOf<String?>(null) }


    LaunchedEffect(Unit) {
        if (wordsState.value == null) {
            wordsState.value = fetchWordsFromFirebaseWithContent1()
        }
    }
    val words = wordsState.value
    if (words == null) {
        // Loading indicator or skeleton UI while fetching
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        val wordsMap = words.groupBy { it.name.first().toString().uppercase() }

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            HeaderBox()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .shadow(
                        shape = RoundedCornerShape(10.dp),
                        elevation = 5.dp,
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .background(appWhiteYellow)
                    .padding(horizontal = 15.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp),
            ) {
                Text(
                    text = "Ilocano A - Z :",
                    style = MaterialTheme.typography.displayLarge,
                    color = colorIlocano
                )
                Divider(color = darkerdividerColor, thickness = 1.dp)

                // Iterate through the letters and their associated words
                for ((letter, words) in wordsMap) {
                    Text(
                        text = "   " + letter,
                        style = MaterialTheme.typography.displayLarge,
                        color = colorIlocano
                    )
                    Divider(color = darkerdividerColor, thickness = 1.dp)
                    Spacer(modifier = Modifier.size(3.dp))

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                        items(words.size) { index ->
                            val word = words[index]
                            WordItem1(word) {
                                // Navigate to the new screen when a word is clicked
                                navController.navigate("wordDetails/${word.content}")
                            }
                            Spacer(modifier = Modifier.size(3.dp))
                            Divider(color = darkerdividerColor, thickness = 1.dp)
                        }
                    }
                }
            }
        }

        // Show TextFileItemUI when selectedWordContent is not null
        if (selectedWordContent.value != null) {
            TextFileItemUI1(selectedWordContent.value!!)
        }
    }
}

@Composable
fun WordItem1(word: WordItem, onItemClick: () -> Unit) {
    val displayName = word.name.replace(".txt", "") // Remove ".txt" extension from the name
    Text(
        text = "   " + displayName,
        style = MaterialTheme.typography.labelMedium,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = normalBlack,
        modifier = Modifier.clickable { onItemClick() }
    )
}
@Composable
fun HeaderBox1() {
    Box(
        modifier = Modifier
            .background(colorIlocano)
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Row (verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()){
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Icon",
                    tint = Color.White)
            }

            Text(text = "Ilocano Language",
                style = MaterialTheme.typography.labelMedium,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = appWhite
            )
        }
    }
}
// Sample UI, same as Word of the Day. please update.
@Composable
fun TextFileItemUI1(textContent: String) {
    val lines = textContent.lines()

    Column(
        modifier = Modifier
            .padding(10.dp)
            .background(Color.LightGray)
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Spacer(modifier = Modifier.size(5.dp))
        if (lines.size >= 6) {
            Text(
                text = lines[1],
                style = MaterialTheme.typography.titleMedium, // Keep your custom typography
                color = textTerm // Keep your custom color
            )
            Text(
                text = lines[2],
                style = MaterialTheme.typography.titleMedium, // Keep your custom typography
                color = appYellow // Keep your custom color
            )
            Text(
                text = lines[3],
                style = MaterialTheme.typography.titleMedium, // Keep your custom typography
                color = textOtherTerms // Keep your custom color
            )
            Text(
                text = lines[4],
                style = MaterialTheme.typography.headlineSmall, // Keep your custom typography
                color = textTerm // Keep your custom color
            )
            Text(
                text = lines[5],
                style = MaterialTheme.typography.headlineSmall, // Keep your custom typography
                color = textSentence // Keep your custom color
            )
        } else {
            // Handle the case where there are less than 6 lines (optional)
        }
    }
}

// Cache for storing fetched text content
val textContentCache1 = mutableMapOf<String, String>()

suspend fun fetchTextContentForWordFromFirebase1(wordName: String): String = coroutineScope {
    val storageReference = Firebase.storage.reference.child("Ilocano/$wordName")

    try {
        val downloadUrl = storageReference.downloadUrl.await()
        val cachedTextContent = textContentCache1[downloadUrl.toString()]

        if (cachedTextContent != null) {
            return@coroutineScope cachedTextContent
        } else {
            // Fetch the content for the word
            val textContent = readTextFromUrl(downloadUrl.toString())
            textContentCache1[downloadUrl.toString()] = textContent
            return@coroutineScope textContent
        }
    } catch (e: IOException) {
        // Handle the failure to fetch the text content for the word
        return@coroutineScope ""
    }
}


suspend fun fetchWordsFromFirebaseWithContent1(): List<WordItem> = coroutineScope {
    val storageReference = Firebase.storage.reference.child("Ilocano/")
    val words = mutableListOf<WordItem>()

    try {
        val result = storageReference.listAll().await()
        val items = result.items

        val contentDeferreds = items.map { item ->
            async(Dispatchers.IO) {
                val name = item.name
                val content = fetchTextContentForWordFromFirebase1(name)
                WordItem(name, content)
            }
        }

        // Await all deferred results and populate the words list
        words.addAll(contentDeferreds.awaitAll())
    } catch (e: IOException) {
        // Handle the failure to fetch the words
    }

    return@coroutineScope words
}

private suspend fun readTextFromUrl(url: String): String {
    return withContext(Dispatchers.IO) {
        try {
            val connection = URL(url).openConnection()
            connection.connect()
            val stream = connection.getInputStream()
            val text = stream.bufferedReader().use { it.readText() }
            stream.close()
            text
        } catch (e: IOException) {
            // Handle the failure to read the text content
            ""
        }
    }
}