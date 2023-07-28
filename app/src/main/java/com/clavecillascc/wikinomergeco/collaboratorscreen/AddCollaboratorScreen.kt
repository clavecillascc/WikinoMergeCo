package com.clavecillascc.wikinomergeco.collaboratorscreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.clavecillascc.wikinomergeco.R
import com.clavecillascc.wikinomergeco.interfaces.FirebaseStorageListener
import com.clavecillascc.wikinomergeco.ui.theme.appDarkBlue
import com.clavecillascc.wikinomergeco.ui.theme.appNotSoWhite
import com.clavecillascc.wikinomergeco.ui.theme.appWhite
import com.clavecillascc.wikinomergeco.ui.theme.appWhiteYellow
import com.clavecillascc.wikinomergeco.ui.theme.appYellow
import com.clavecillascc.wikinomergeco.ui.theme.buttonCancel
import com.clavecillascc.wikinomergeco.ui.theme.dividerColor
import com.clavecillascc.wikinomergeco.ui.theme.normalBlack
import com.clavecillascc.wikinomergeco.ui.theme.textOtherTerms
import com.clavecillascc.wikinomergeco.ui.theme.textSentence
import com.clavecillascc.wikinomergeco.ui.theme.textTerm

@Composable
fun AddCollaboratorScreen(navController: NavController) {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),

        ) {
        AddCollaboratorHeaderBox(navController)
        AddNewTranslation(navController = navController, ctx = context)
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
    color: Color = appWhiteYellow,
    navController: NavController,
    ctx: Context
) {
    var term by remember { mutableStateOf("") }
    var language by remember { mutableStateOf("") }
    var translationterm by remember { mutableStateOf("") }
    var terminsentence by remember { mutableStateOf("") }
    var translationsentence by remember { mutableStateOf("") }
    if (language.isEmpty()) {
        language = "Bicolano"
    }


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
        Row(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
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
        Spacer(modifier = Modifier.size(15.dp))
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
                    onClick = { navController.navigate("collaborator") }) {
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
                        UploadData.uploadToFirebase(
                            term,
                            language,
                            translationterm,
                            terminsentence,
                            translationsentence,
                            object : FirebaseStorageListener {
                                override fun <T> success(any: Any) {
                                    Toast.makeText(
                                        ctx,
                                        "File Uploaded Successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                override fun error(error: Error) {
                                    Toast.makeText(
                                        ctx,
                                        "File Upload Unsuccessful",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
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
            textStyle = TextStyle(fontSize = 12.sp),
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


        CustomSpinner(
            textStyle = TextStyle(fontSize = 12.sp),
            items = listOf("Bicolano", "Cebuano", "Ilocano"),
            selectedItem = language,
            onItemSelected = onLanguageChange,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = appWhite,
                focusedContainerColor = appWhite,
                unfocusedLabelColor = appYellow,
                focusedLabelColor = appYellow,
                unfocusedTextColor = appYellow,
                focusedTextColor = normalBlack,
                disabledContainerColor = appWhite,
                disabledTextColor = normalBlack,
                disabledPlaceholderColor = normalBlack
            )
        )

        //3-Translation of Term in tagalog/english?
        TextField(
            textStyle = TextStyle(fontSize = 12.sp),
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
            textStyle = TextStyle(fontSize = 12.sp),
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
            textStyle = TextStyle(fontSize = 12.sp),
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

@Composable
fun CustomSpinner(
    textStyle: TextStyle,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    colors: TextFieldColors
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .wrapContentSize()
            .clickable { expanded = true }
    ) {
        TextField(
            textStyle = textStyle,
            label = { Text("Language of term") },
            value = selectedItem,
            onValueChange = onItemSelected,
            colors = colors,
            enabled = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { expanded = false }
            ),
        )


        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(onClick = {
                    onItemSelected(item)
                    expanded = false
                }) {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}