package com.clavecillascc.wikinomergeco.screens
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
import androidx.compose.ui.unit.dp
import com.clavecillascc.wikinomergeco.ui.theme.appWhiteYellow
import com.clavecillascc.wikinomergeco.ui.theme.appYellow
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

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

    LaunchedEffect(Unit) {
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
        // Header
        Text(
            text = "Sample",
            style = MaterialTheme.typography.headlineMedium
        )
        // Word of the Day
        Text(
            text = word.value,
            style = MaterialTheme.typography.titleMedium
        )
        // Other terms
        Text(
            text = ""
        )
        // In sentence
        Text(
            text = ""
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
