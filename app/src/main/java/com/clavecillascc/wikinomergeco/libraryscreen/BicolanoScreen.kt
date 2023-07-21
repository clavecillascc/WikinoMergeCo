package com.clavecillascc.wikinomergeco.libraryscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL

@Composable
fun BicolanoScreen() {
    val textFilesState = remember { mutableStateOf<List<String>?>(null) }

    LaunchedEffect(Unit) {
        if (textFilesState.value == null) {
            textFilesState.value = fetchTextFilesFromFirebase2()
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
                TextFile2ItemUI(textContent = textFiles[index])
            }
        }
    }
}

//UI for every text box (please update)
@Composable
fun TextFile2ItemUI(textContent: String) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .background(Color.LightGray)
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Text(textContent)
    }
}

// Cache for storing fetched text content
val textContentCache1 = mutableMapOf<String, String>()

// Function to fetch text files from Firebase Cloud Storage
suspend fun fetchTextFilesFromFirebase2(): List<String> = coroutineScope {
    val storageReference = Firebase.storage.reference.child("Bicolano/")
    val textFiles = mutableListOf<String>()
    val deferredList = mutableListOf<Deferred<String>>()

    try {
        val result = storageReference.listAll().await()
        val items = result.items

        for (item in items) {
            val downloadUrl = item.downloadUrl.await()
            val cachedTextContent = textContentCache1[downloadUrl.toString()]

            if (cachedTextContent != null) {
                textFiles.add(cachedTextContent)
            } else {
                // Fetch in parallel
                val deferredContent = async {
                    readTextFromUrl(downloadUrl.toString())
                }
                deferredList.add(deferredContent)
            }
        }

        // Wait for all the deferred tasks to complete
        val newContents = deferredList.awaitAll()

        for (newContent in newContents) {
            if (newContent.isNotEmpty()) {
                textFiles.add(newContent)
            }
        }
    } catch (e: IOException) {
        // Handle the failure to fetch the text files
    }

    return@coroutineScope textFiles
}
// Function to read text content from a given URL
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