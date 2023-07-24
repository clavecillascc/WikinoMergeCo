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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.dp
import com.clavecillascc.wikinomergeco.ui.theme.appWhiteYellow
import com.clavecillascc.wikinomergeco.ui.theme.appYellow
import com.clavecillascc.wikinomergeco.ui.theme.dividerColor
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
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .shadow(
                shape = RoundedCornerShape(10.dp),
                elevation = 5.dp,
            )
            .clip(RoundedCornerShape(10.dp))
            .background(appWhiteYellow)
            .padding(horizontal = 15.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),) {
            Text(text = "Cebuano:")
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                //add here the column modifier

                items(textFiles.size) { index ->
                    CebuanoLibrary(textContent = textFiles[index])
                    Spacer(modifier = Modifier.size(5.dp))
                    Divider(color = dividerColor, thickness = 2.dp)
                }
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

@Composable
fun CebuanoLibrary (textContent: String,color: Color = appWhiteYellow){
    Column(verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .height(20.dp)
            .padding(horizontal = 10.dp)
    ) {
        Text(text = "text 1")
        Text(text = "text 2")
        Text(text = "text 3")
        Text(text = "text 4")
    }
}

// Cache for storing fetched text content
val textContentCache = mutableMapOf<String, String>()

// Function to fetch text files from Firebase Cloud Storage
suspend fun fetchTextFilesFromFirebase(): List<String> = coroutineScope {
    val storageReference = Firebase.storage.reference.child("Cebuano/")
    val textFiles = mutableListOf<String>()
    val deferredList = mutableListOf<Deferred<String>>()

    try {
        val result = storageReference.listAll().await()
        val items = result.items

        for (item in items) {
            val downloadUrl = item.downloadUrl.await()
            val cachedTextContent = textContentCache[downloadUrl.toString()]

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
