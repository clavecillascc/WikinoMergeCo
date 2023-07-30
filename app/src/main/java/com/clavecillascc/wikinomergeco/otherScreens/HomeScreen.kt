package com.clavecillascc.wikinomergeco.otherScreens
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import androidx.preference.PreferenceManager
import com.clavecillascc.wikinomergeco.ui.theme.appWhiteYellow
import com.clavecillascc.wikinomergeco.ui.theme.appYellow
import com.clavecillascc.wikinomergeco.ui.theme.logoGray
import com.clavecillascc.wikinomergeco.ui.theme.normalBlack
import com.clavecillascc.wikinomergeco.ui.theme.notSelectedGray
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
        Spacer(modifier = Modifier.size(70.dp))
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
            .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 10.dp)
            .shadow(
                shape = RoundedCornerShape(10.dp),
                elevation = 4.dp,
            )
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .fillMaxWidth()
            .height(200.dp), horizontalAlignment = Alignment.Start
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
                fontSize = 25.sp,
                color = textTerm
            )
            Text(
                text = lines[2],
                style = MaterialTheme.typography.titleMedium,
                fontSize = 22.sp,
                color = appYellow
            )
            Text(
                text = lines[3],
                style = MaterialTheme.typography.titleMedium,
                fontSize = 18.sp,
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
            .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 10.dp)
            .shadow(
                shape = RoundedCornerShape(10.dp),
                elevation = 4.dp,
            )
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Text(
                text = "Frequenty Asked Questions",
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 20.sp)
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                ExpandableBox1()
                ExpandableBox2()
            }

        }
    }
}

@Composable
fun ExpandableBox1(color: Color = appWhiteYellow) {
    var isExpanded by remember { mutableStateOf(false) }
    val height by animateDpAsState(if (isExpanded) 180.dp else 50.dp)
    val width by animateDpAsState(if (isExpanded) 300.dp else 300.dp)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .animateContentSize()
            .background(notSelectedGray)
            .clickable {
                isExpanded = !isExpanded
            }
    ) {
        Box(
            modifier = Modifier
                .padding(start = 15.dp, top = 2.dp, end = 15.dp, bottom = 2.dp)
                .background(notSelectedGray)
                .padding(horizontal = 5.dp, vertical = 5.dp)
                .height(height)
                .width(width)
                .background(notSelectedGray)
        ) {
            Column(modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.Center) {
                Text(
                    text =  "• " + "How do I navigate to different screens in the app?",
                    style = MaterialTheme.typography.labelMedium

                )
                Text(
                    text =  "- " + "You can navigate to different screens using the bottom navigation bar at the bottom of the screen. Each icon represents a different screen. Simply tap on the icon to go to the corresponding screen.",
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 14.sp,
                    color = normalBlack
                )
            }
        }
    }
}

@Composable
fun ExpandableBox2(color: Color = appWhiteYellow) {
    var isExpanded by remember { mutableStateOf(false) }
    val height by animateDpAsState(if (isExpanded) 210.dp else 50.dp)
    val width by animateDpAsState(if (isExpanded) 300.dp else 300.dp)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .animateContentSize()
            .background(notSelectedGray)
            .clickable {
                isExpanded = !isExpanded
            }
    ) {
        Box(
            modifier = Modifier
                .padding(start = 15.dp, top = 2.dp, end = 15.dp, bottom = 2.dp)
                .background(notSelectedGray)
                .padding(horizontal = 5.dp, vertical = 5.dp)
                .height(height)
                .width(width)
                .background(notSelectedGray)
        ) {
            Column(modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.Center) {
                Text(
                    text =  "• " + "How do I add a new word?",
                    style = MaterialTheme.typography.labelMedium
                )

                Text(
                    text =  "- " + "To add a new collaborator, navigate to the 'Collaborator' screen using the drawer menu found in the top bar. Click on the floating action button (the plus icon) in the bottom right corner. Fill in the required information for the new word and click 'Upload' to add them.",
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 14.sp,
                    color = normalBlack
                )
            }
        }
    }
}