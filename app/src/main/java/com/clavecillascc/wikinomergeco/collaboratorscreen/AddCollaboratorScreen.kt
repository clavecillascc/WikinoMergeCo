package com.clavecillascc.wikinomergeco.collaboratorscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.clavecillascc.wikinomergeco.R
import com.clavecillascc.wikinomergeco.ui.theme.appDarkBlue
import com.clavecillascc.wikinomergeco.ui.theme.appNotSoWhite
import com.clavecillascc.wikinomergeco.ui.theme.appWhite
import com.clavecillascc.wikinomergeco.ui.theme.appWhiteYellow
import com.clavecillascc.wikinomergeco.ui.theme.appYellow
import com.clavecillascc.wikinomergeco.ui.theme.buttonCancel
import com.clavecillascc.wikinomergeco.ui.theme.colorCebuano
import com.clavecillascc.wikinomergeco.ui.theme.dividerColor
import com.clavecillascc.wikinomergeco.ui.theme.logoBlue
import com.clavecillascc.wikinomergeco.ui.theme.logoGray
import com.clavecillascc.wikinomergeco.ui.theme.normalBlack
import com.clavecillascc.wikinomergeco.ui.theme.notSelectedGray
import com.clavecillascc.wikinomergeco.ui.theme.selectedGray
import com.clavecillascc.wikinomergeco.ui.theme.textOtherTerms
import com.clavecillascc.wikinomergeco.ui.theme.textSeeMore
import com.clavecillascc.wikinomergeco.ui.theme.textSentence
import com.clavecillascc.wikinomergeco.ui.theme.textTerm

@Composable
fun AddCollaboratorScreen(navController: NavController) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        AddCollaboratorHeaderBox(navController)
        AddNewTranslation()
    }
}

@Composable
fun AddCollaboratorHeaderBox(navController: NavController) {
    Box(
        modifier = Modifier
            .background(appYellow)
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(onClick = { navController.navigate("collaborator") }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Icon",
                    tint = Color.White
                )
            }

            androidx.compose.material.Text(
                text = "Add new translation ",
                style = MaterialTheme.typography.labelMedium,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = appWhite
            )
        }
    }
}

@Composable
fun AddNewTranslation(
    color: Color = appWhiteYellow
) {
    var term by remember { mutableStateOf("") }
    var language by remember { mutableStateOf("") }
    var translationterm by remember { mutableStateOf("") }
    var terminsentence by remember { mutableStateOf("") }
    var translationsentence by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(horizontal = 18.dp, vertical = 10.dp)
            .shadow(
                shape = RoundedCornerShape(10.dp),
                elevation = 5.dp,
            )
            .clip(RoundedCornerShape(10.dp))
            .background(appWhiteYellow)
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row() {
            Image(
                painter = painterResource(id = R.drawable.profilepic_sample),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(width = 1.dp, color = normalBlack, CircleShape)
            )
            Column() {
                Text(
                    text = "Username", Modifier.padding(horizontal = 20.dp),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = "user details", Modifier.padding(horizontal = 20.dp),
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 12.sp
                )
            }
        }
        //collaborator added word
        Column(
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 20.dp)
                .shadow(
                    shape = RoundedCornerShape(10.dp),
                    elevation = 5.dp,
                )
                .clip(RoundedCornerShape(10.dp))
                .background(color = appWhite)
                .padding(horizontal = 15.dp, vertical = 15.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            TextFields(
                term,
                language,
                translationterm,
                terminsentence,
                translationsentence,
                { term = it },
                { language = it },
                { translationterm = it },
                { terminsentence = it },
                { translationsentence = it }
            )
        }

        Column(verticalArrangement = Arrangement.Center) {
            Divider(color = dividerColor, thickness = 2.dp)
            Spacer(modifier = Modifier.size(5.dp))
            //See more clickable
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                //Cancel Button
                Button(modifier = Modifier
                    .size(height = 35.dp, width = 100.dp)
                    .defaultMinSize()
                    .padding(top = 5.dp, end = 13.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = buttonCancel
                    ),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }) {
                    Row {
                        Text(
                            text = "Cancel",
                            style = MaterialTheme.typography.labelMedium,
                            color = appWhite,
                            fontSize = 14.sp
                        )
                    }
                }

                // Upload Button
                Button(
                    modifier = Modifier
                        .size(height = 35.dp, width = 100.dp)
                        .defaultMinSize()
                        .padding(top = 5.dp, end = 13.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = appDarkBlue
                    ),
                    contentPadding = PaddingValues(0.dp),
                    onClick = {
                        val userInput = buildString {
                            append("Term: ${term}\n")
                            append("Language: ${language}\n")
                            append("Translation of Term: ${translationterm}\n")
                            append("Term used in a sentence: ${terminsentence}\n")
                            append("Translation of Sentence: ${translationsentence}\n")
                        }

                        UploadData.uploadToFirebase(term, language, translationterm, terminsentence,translationsentence)
                    }
                ) {
                    Row {
                        Text(
                            text = "Upload",
                            style = MaterialTheme.typography.labelMedium,
                            color = appWhite,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TextFields(
    term: String,
    language: String,
    translationterm: String,
    terminsentence: String,
    translationsentence: String,
    onTermChange: (String) -> Unit,
    onLanguageChange: (String) -> Unit,
    onTranslationTermChange: (String) -> Unit,
    onTermInSentenceChange: (String) -> Unit,
    onTranslationSentenceChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
        //1-Term
        TextField(
            label = { Text("Term") },
            value = term,
            onValueChange = onTermChange,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = appWhite,
                focusedContainerColor = appNotSoWhite,
                unfocusedLabelColor = textTerm,
                focusedLabelColor = appYellow,
                unfocusedTextColor = textTerm,
                focusedTextColor = normalBlack
            )
        )

        //2-Language of Term
        TextField(
            label = { Text("Language") },
            value = language,
            onValueChange = onLanguageChange,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = appWhite,
                focusedContainerColor = appNotSoWhite,
                unfocusedLabelColor = appYellow,
                focusedLabelColor = appYellow,
                unfocusedTextColor = appYellow,
                focusedTextColor = normalBlack
            )
        )

        //3-Translation of Term in tagalog/english?
        TextField(
            label = { Text("Translation of term") },
            value = translationterm,
            onValueChange = onTranslationTermChange,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = appWhite,
                focusedContainerColor = appNotSoWhite,
                unfocusedLabelColor = textOtherTerms,
                focusedLabelColor = textOtherTerms,
                unfocusedTextColor = textOtherTerms,
                focusedTextColor = normalBlack
            )
        )

        //4-Term used in a sentence
        TextField(
            label = { Text("Term used in a sentence") },
            value = terminsentence,
            onValueChange = onTermInSentenceChange,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = appWhite,
                focusedContainerColor = appNotSoWhite,
                unfocusedLabelColor = textTerm,
                focusedLabelColor = textTerm,
                unfocusedTextColor = textTerm,
                focusedTextColor = normalBlack
            )
        )

        //5-Term in tagalog/english?
        TextField(
            label = { Text("Translation of sentence") },
            value = translationsentence,
            onValueChange = onTranslationSentenceChange,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = appWhite,
                focusedContainerColor = appNotSoWhite,
                unfocusedLabelColor = textSentence,
                focusedLabelColor = textSentence,
                unfocusedTextColor = textSentence,
                focusedTextColor = normalBlack
            )
        )
    }
}