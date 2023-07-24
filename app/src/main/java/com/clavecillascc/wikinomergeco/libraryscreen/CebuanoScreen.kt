package com.clavecillascc.wikinomergeco.libraryscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.clavecillascc.wikinomergeco.ui.theme.appWhiteYellow
import com.clavecillascc.wikinomergeco.ui.theme.appYellow
import com.clavecillascc.wikinomergeco.ui.theme.textOtherTerms
import com.clavecillascc.wikinomergeco.ui.theme.textSentence
import com.clavecillascc.wikinomergeco.ui.theme.textTerm
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException

@Composable
fun CebuanoScreen(navController: NavHostController) {
    val wordsState = remember { mutableStateOf<List<WordItem>?>(null) }
    val selectedWordContent = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        if (wordsState.value == null) {
            wordsState.value = fetchWordsFromFirebase()
        }
    }

    val words = wordsState.value

    if (words == null) {
        // Loading indicator or skeleton UI while fetching
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(text = "Cebuano:")
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(words.size) { index ->
                    val word = words[index]
                    WordItem(word) {
                        // Navigate to the new screen when a word is clicked
                        navController.navigate("wordDetails/${word.content}")
                    }
                }
            }
        }

        // Show TextFileItemUI when selectedWordContent is not null
        if (selectedWordContent.value != null) {
            TextFileItemUI(selectedWordContent.value!!)
        }
    }
}

@Composable
fun WordItem(word: WordItem, onItemClick: () -> Unit) {
    val displayName = word.name.replace(".txt", "") // Remove ".txt" extension from the name
    Text(
        text = displayName,
        style = MaterialTheme.typography.titleMedium,
        color = textTerm,
        modifier = Modifier.clickable { onItemClick() }
    )
}

// Sample UI, same as Word of the Day. please update.
@Composable
fun TextFileItemUI(textContent: String) {
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
val textContentCache = mutableMapOf<String, String>()

suspend fun fetchTextContentForWordFromFirebase(wordName: String): String = coroutineScope {
    // Construct the storage reference for the specific word
    val storageReference = Firebase.storage.reference.child("Cebuano/$wordName")

    try {
        val downloadUrl = storageReference.downloadUrl.await()
        val cachedTextContent = textContentCache[downloadUrl.toString()]

        if (cachedTextContent != null) {
            return@coroutineScope cachedTextContent
        } else {
            // Fetch the content for the word
            val textContent = readTextFromUrl(downloadUrl.toString())
            textContentCache[downloadUrl.toString()] = textContent
            return@coroutineScope textContent
        }
    } catch (e: IOException) {
        // Handle the failure to fetch the text content for the word
        return@coroutineScope ""
    }
}


suspend fun fetchWordsFromFirebase(): List<WordItem> = coroutineScope {
    val storageReference = Firebase.storage.reference.child("Cebuano/")
    val words = mutableListOf<WordItem>()

    try {
        val result = storageReference.listAll().await()
        val items = result.items

        for (item in items) {
            val name = item.name
            val content = fetchTextContentForWordFromFirebase(name)
            words.add(WordItem(name, content))
        }
    } catch (e: IOException) {
        // Handle the failure to fetch the words
    }

    return@coroutineScope words
}
// Function to read text content from a given URL
private suspend fun readTextFromUrl(url: String): String {
    return withContext(Dispatchers.IO) {
        try {
            val connection = java.net.URL(url).openConnection()
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