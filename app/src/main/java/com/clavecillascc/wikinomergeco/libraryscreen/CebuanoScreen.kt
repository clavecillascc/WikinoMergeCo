package com.clavecillascc.wikinomergeco.libraryscreen

import androidx.compose.foundation.background
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.clavecillascc.wikinomergeco.ui.theme.appYellow
import com.clavecillascc.wikinomergeco.ui.theme.textOtherTerms
import com.clavecillascc.wikinomergeco.ui.theme.textSentence
import com.clavecillascc.wikinomergeco.ui.theme.textTerm
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException

@Composable
fun CebuanoScreen() {
    val textFilesState = remember { mutableStateOf<List<String>?>(null) }

    LaunchedEffect(Unit) {
        if (textFilesState.value == null) {
            textFilesState.value = fetchTextFilesFromFirebase()
        }
    }

    val textFiles = textFilesState.value

    if (textFiles == null) {
        // Loading indicator or skeleton UI while fetching
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(textFiles.size) { index ->
                TextFileItemUI(textContent = textFiles[index])
            }
        }
    }
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
        } else {
            // Handle the case where there are less than 6 lines (optional)
        }
    }
}

// Cache for storing fetched text content
val textContentCache = mutableMapOf<String, String>()

// Function to fetch text files from Firebase Cloud Storage
suspend fun fetchTextFilesFromFirebase(): List<String> {
    val storageReference = Firebase.storage.reference.child("Cebuano/")
    val textFiles = mutableListOf<String>()

    try {
        val result = storageReference.listAll().await()
        val items = result.items

        for (item in items) {
            val downloadUrl = item.downloadUrl.await()
            val cachedTextContent = textContentCache[downloadUrl.toString()]
            val textContent = if (cachedTextContent != null) {
                cachedTextContent
            } else {
                val newContent = readTextFromUrl(downloadUrl.toString())
                textContentCache[downloadUrl.toString()] = newContent
                newContent
            }
            textFiles.add(textContent)
        }
    } catch (e: IOException) {
        // Handle the failure to fetch the text files
    }

    return textFiles
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
