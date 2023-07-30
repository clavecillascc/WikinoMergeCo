package com.clavecillascc.wikinomergeco.otherScreens

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.clavecillascc.wikinomergeco.R
import com.clavecillascc.wikinomergeco.components.AutocompleteViewModel
import com.clavecillascc.wikinomergeco.interfaces.DBResponseListener
import com.clavecillascc.wikinomergeco.models.CommentWord
import com.clavecillascc.wikinomergeco.models.DownVote
import com.clavecillascc.wikinomergeco.models.Upvote
import com.clavecillascc.wikinomergeco.services.CommentRequest
import com.clavecillascc.wikinomergeco.services.DownVoteRequest
import com.clavecillascc.wikinomergeco.services.UpvoteRequest
import com.clavecillascc.wikinomergeco.ui.theme.TextWhite
import com.clavecillascc.wikinomergeco.ui.theme.appWhite
import com.clavecillascc.wikinomergeco.ui.theme.appWhiteYellow
import com.clavecillascc.wikinomergeco.ui.theme.appYellow
import com.clavecillascc.wikinomergeco.ui.theme.colorIndicator
import com.clavecillascc.wikinomergeco.ui.theme.colorinactiveIndicator
import com.clavecillascc.wikinomergeco.ui.theme.normalBlack
import com.clavecillascc.wikinomergeco.ui.theme.textOtherTerms
import com.clavecillascc.wikinomergeco.ui.theme.textSentence
import com.clavecillascc.wikinomergeco.ui.theme.textTerm
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

@Composable
fun TranslateScreen(navController: NavHostController) {
    /*TODO*/
    //Text(text = "Translate")
    var search by remember {
        mutableStateOf("")
    }

    var isShowCard by remember {
        mutableStateOf(false)
    }

    var resultsShow by remember {
        mutableStateOf(false)
    }

    var showProgressDialog by remember {
        mutableStateOf(false)
    }

    var showComment by remember {
        mutableStateOf(false)
    }

    var translated by remember {
        mutableStateOf("")
    }

    var resultData by remember {
        mutableStateOf("")
    }

    var commentList by remember {
        mutableStateOf<List<CommentWord>>(emptyList())
    }

    var showResult by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }

    val autocompleteViewModel: AutocompleteViewModel = viewModel()

    val autocompleteSuggestions by autocompleteViewModel.suggestions.collectAsState(emptyList())

    if (search.isEmpty()) {
        isShowCard = false
    }

    val ctx = LocalContext.current

    val commentRequest = CommentRequest()

    commentRequest.getComments(search, object : DBResponseListener {
        override fun <T> success(any: Any) {
            if (any is List<*>) {
                val list = any.filterIsInstance<CommentWord>()
                if (list.isNotEmpty()) {
                    commentList = list
                }
            }
        }

        override fun error(error: Error) {
            TODO("Not yet implemented")
        }
    })

    var selectedLanguage by remember { mutableStateOf("") }
    var isWordFound by remember { mutableStateOf(false) }

    LaunchedEffect(search, selectedLanguage) {
        if (search.isNotEmpty() && selectedLanguage.isNotEmpty()) {
            val documentId = "$selectedLanguage:$search"

            Log.d("MyApp", "Constructed documentId: $documentId")

            val translationDocument = FirebaseFirestore.getInstance()
                .collection("translation")
                .document(documentId)
                .get()
                .await()

            if (translationDocument.exists()) {
                val translatedWord = translationDocument.getString("translatedword")
                if (!translatedWord.isNullOrEmpty()) {
                    translated = translatedWord
                    isWordFound = true

                    val storageReference = FirebaseStorage.getInstance().reference
                    val filePath = "${selectedLanguage}/${translatedWord}.txt"

                    isLoading = true

                    storageReference.child(filePath).getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
                        val fileData = String(bytes)
                        resultData = fileData
                        isLoading = false
                    }.addOnFailureListener {
                        resultData = "Error fetching data."
                        isLoading = false
                    }
                }
            } else {
                isWordFound = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(
            horizontalAlignment = Alignment.Start

        ) {
            SearchAppBar(
                text = search,
                onTextChange = { text ->
                    search = text
                    if (text.isEmpty()) {
                        isShowCard = false
                        resultsShow = false
                        commentList = emptyList()
                    } else if (text.length >= 2) {
                        autocompleteViewModel.fetchAutocompleteSuggestions(text)
                    } else {
                        autocompleteViewModel.fetchAutocompleteSuggestions("")
                    }
                },
                onCloseClicked = {
                    isShowCard = false
                    resultsShow = false
                    search = ""
                    commentList = emptyList()
                },
                onSearchClicked = {
                    isShowCard = true
                },
                autocompleteSuggestions = autocompleteSuggestions
            )
            AvailableTranslations(
                text = search,
                visibility = isShowCard,
                buttonClick = { isShow, tl ->
                    resultsShow = isShow
                    translated = tl
                    selectedLanguage = tl
                }
            )

            TranslationResult(
                text = search,
                showResult = resultsShow,
                translated = translated,
                resultData = resultData,
                isLoading = isLoading
            )

            TranslateBottomMenu(
                context = ctx,
                showResult = resultsShow,
                upVoteListener = {
                    if (showProgressDialog) {
                        Toast.makeText(
                            ctx,
                            "Can't do upvote right now, There is still an ongoing request",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        showProgressDialog = true
                        val upvote = Upvote.UpvoteBuilder()
                            .setTranslation(translated)
                            .setWord(search)
                            .setCount(1)
                            .build()
                        val request = UpvoteRequest()
                        request.insertUpvote(upvote, object : DBResponseListener {
                            override fun <T> success(any: Any) {
                                showProgressDialog = false
                                Toast.makeText(
                                    ctx,
                                    "Successfully Added Upvote",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            override fun error(error: Error) {
                                showProgressDialog = false
                                Toast.makeText(
                                    ctx,
                                    "Something error happen, please try again later",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                    }

                },
                downVoteListener = {
                    if (showProgressDialog) {
                        Toast.makeText(
                            ctx,
                            "Can't do down vote right now, There is still ongoing request",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val downVote = DownVote.DownVoteBuilder()
                            .setTranslation(translated)
                            .setWord(search)
                            .setCount(1)
                            .build()
                        val request = DownVoteRequest()
                        showProgressDialog = true
                        request.insertDownVote(downVote, object : DBResponseListener {
                            override fun <T> success(any: Any) {
                                showProgressDialog = false
                                Toast.makeText(
                                    ctx,
                                    "Successfully Added Down Vote",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            override fun error(error: Error) {
                                showProgressDialog = false
                                Toast.makeText(
                                    ctx,
                                    "Something error happen, please try again later",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        })
                    }

                },
                openComment = {
                    if (showProgressDialog) {
                        Toast.makeText(
                            ctx,
                            "Can't open comment right now, There is still ongoing request",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        showComment = !showComment
                    }

                },
            )
            if (showProgressDialog) {
                ProgressDialog()
            }

            if (showResult && isWordFound) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Spacer(modifier = Modifier.size(15.dp))
                    TranslationResultCard(
                        textData = search,
                        showResult = showResult,
                        color = appWhiteYellow,
                        translated = translated,
                        result = resultData,
                        isLoading = false
                    )
                }
            } else if (showResult) {

            }

            CommentCard(
                color = appWhiteYellow,
                commentVisible = showComment,
                saveCommentListener = { comment ->
                    if (comment.isNotEmpty()) {
                        showComment = false
                        showProgressDialog = true

                        val commentWord = CommentWord.CommentWordBuilder()
                            .setWord(search)
                            .setComments(comment)
                            .build()
                        val request = CommentRequest()
                        request.insertComment(commentWord, object : DBResponseListener {
                            override fun <T> success(any: Any) {
                                showProgressDialog = false
                                Toast.makeText(
                                    ctx,
                                    "Successfully Saved Comment",
                                    Toast.LENGTH_SHORT
                                ).show()
                                commentList = emptyList()
                                request.getComments(search, object : DBResponseListener {
                                    override fun <T> success(any: Any) {
                                        if (any is List<*>) {
                                            val list = any.filterIsInstance<CommentWord>()
                                            if (list.isNotEmpty()) {
                                                commentList = list
                                            }
                                        }
                                    }

                                    override fun error(error: Error) {
                                        TODO("Not yet implemented")
                                    }
                                })
                            }

                            override fun error(error: Error) {
                                showProgressDialog = false
                                Toast.makeText(
                                    ctx,
                                    "Failed to save comment, please try again later",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })

                    } else {
                        Toast.makeText(
                            ctx,
                            "Please Don't Leave Comment Field Empty",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
            CommentListView(
                list = commentList,
                showCommentList = if (showComment) false else resultsShow,
            )

        }

    }

}


@Composable
fun SearchBar() {
    Box(
        modifier = Modifier
            .background(appYellow)
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Row {

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    autocompleteSuggestions: List<String>,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = appYellow
    ) {
        var isSuggestionsExpanded by remember { mutableStateOf(false) }
        val context = LocalContext.current

        val keyboardController = LocalSoftwareKeyboardController.current

        Box(modifier = Modifier.fillMaxSize()) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { newText ->
                    onTextChange(newText)
                    isSuggestionsExpanded = newText.isNotEmpty()
                },
                placeholder = {
                    Text(
                        modifier = Modifier.alpha(ContentAlpha.medium),
                        text = "Search here...",
                        color = Color.White
                    )
                },
                textStyle = TextStyle(fontSize = MaterialTheme.typography.bodySmall.fontSize),
                singleLine = true,
                leadingIcon = {
                    IconButton(
                        modifier = Modifier.alpha(ContentAlpha.medium),
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = Color.White
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = {
                        hideKeyboard(context)
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon",
                            tint = Color.White
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    hideKeyboard(context) // Close keyboard on search button click
                    onSearchClicked(text)
                }),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    cursorColor = Color.White.copy(alpha = ContentAlpha.medium),
                    textColor = Color.White
                )
            )

            // Suggestions Popup
            if (isSuggestionsExpanded && autocompleteSuggestions.isNotEmpty()) {
                val dropdownOffsetY = with(LocalDensity.current) { 56.dp } // Adjust the value as needed
                val offset = IntOffset(0, with(LocalDensity.current) { dropdownOffsetY.roundToPx() })

                // Show the suggestions popup
                Popup(
                    alignment = Alignment.TopStart,
                    offset = offset,
                    onDismissRequest = {
                        isSuggestionsExpanded = false
                    }
                ) {
                    Surface(
                        shape = RectangleShape,
                        elevation = 4.dp,
                        modifier = Modifier.fillMaxWidth().background(Color.White)
                    ) {
                        Column {
                            autocompleteSuggestions.forEach { suggestion ->
                                DropdownMenuItem(
                                    onClick = {
                                        hideKeyboard(context)
                                        onTextChange(suggestion)
                                        onSearchClicked(suggestion)
                                        isSuggestionsExpanded = false
                                    }
                                ) {
                                    Text(
                                        text = suggestion,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun hideKeyboard(context: Context) {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val currentFocus = (context as? Activity)?.currentFocus
    if (currentFocus != null) {
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}
@Composable
fun AvailableTranslations(
    text: String,
    visibility: Boolean,
    buttonClick: (Boolean, String) -> Unit,
) {
    var clickBicolano by remember {
        mutableStateOf(false)
    }
    var clickCebuano by remember {
        mutableStateOf(false)
    }
    var clickIlocano by remember {
        mutableStateOf(false)
    }
    if (visibility) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            color = appYellow
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Available Languages:",
                    color = Color.White
                )
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = if (clickBicolano) Color.Blue else Color.White),
                        onClick = {
                            clickBicolano = !clickBicolano
                            clickIlocano = false
                            clickCebuano = false
                            buttonClick(clickBicolano, "Bicolano")


                        }) {
                        Text(
                            text = "Bicolano",
                            color = if (clickBicolano) Color.White else Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = if (clickCebuano) Color.Blue else Color.White),
                        onClick = {
                            clickCebuano = !clickCebuano
                            clickBicolano = false
                            clickIlocano = false
                            buttonClick(clickCebuano, "Cebuano")
                        }) {
                        Text(
                            text = "Cebuano",
                            color = if (clickCebuano) Color.White else Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = if (clickIlocano) Color.Blue else Color.White),
                        onClick = {
                            clickIlocano = !clickIlocano
                            clickBicolano = false
                            clickCebuano = false
                            buttonClick(clickIlocano, "Ilocano")
                        }) {
                        Text(
                            text = "Ilocano",
                            color = if (clickIlocano) Color.White else Color.Black
                        )
                    }
                }
            }
        }
    } else {
        clickBicolano = false
        clickCebuano = false
        clickIlocano = false
    }

}

@Composable
fun TranslationResult(
    text: String,
    resultData: String,
    showResult: Boolean,
    translated: String,
    isLoading: Boolean
) {
    if (showResult) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.size(15.dp))
            Box {
                TranslationResultCard(
                    textData = text,
                    showResult = showResult,
                    color = appWhiteYellow,
                    translated = translated,
                    result = resultData,
                    isLoading = isLoading
                )
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun TranslationResultCard(
    textData: String,
    showResult: Boolean,
    result: String,
    color: Color,
    translated: String,
    isLoading: Boolean
) {
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
        //.height(200.dp)
    ) {
        Column {
            //Word of the Day
            // Line 1 - Header
            Text(
                text = textData,
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(modifier = Modifier.size(5.dp))

            if (result.isNotEmpty()) {
                TranslationData(
                    resultTl = result
                )
            }
        }
    }
}

@Composable
fun TranslateBottomMenu(
    context: Context,
    showResult: Boolean,
    upVoteListener: () -> Unit,
    downVoteListener: () -> Unit,
    openComment: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.Transparent)
            .alpha(if (showResult) 1f else 0f),
        horizontalAlignment = Alignment.Start,

        ) {

        Row(
            modifier = Modifier.background(Color.Transparent)
        ) {
            /* FOLLOW*/
            Image(
                painter = painterResource(id = R.drawable.ic_heart),
                contentDescription = "Follow"
            )
            Spacer(
                modifier = Modifier
                    .size(5.dp)
            )
            Text(
                text = "Follow",
                modifier = Modifier
                    .clickable {
                        Toast
                            .makeText(context, "Clicked", Toast.LENGTH_SHORT)
                            .show()
                    }
            )
            Spacer(
                modifier = Modifier
                    .size(15.dp)
            )

            /* COMMENT*/
            Image(
                painter = painterResource(id = R.drawable.ic_comment),
                contentDescription = "Comment"
            )
            Spacer(
                modifier = Modifier
                    .size(5.dp)
            )
            Text(
                text = "Comment",
                modifier = Modifier
                    .clickable {
                        openComment()
                    }
            )

            Spacer(
                modifier = Modifier
                    .size(15.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_upvote),
                contentDescription = "Up Vote",
                modifier = Modifier.clickable {
                    upVoteListener()
                }
            )
            Spacer(
                modifier = Modifier
                    .size(5.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_downvote),
                contentDescription = "Up Vote",
                modifier = Modifier.clickable {
                    downVoteListener()
                }
            )

        }
    }
}


@Composable
private fun TranslationData(resultTl: String) {
    // Split the resultTl into individual lines based on line breaks ('\n')
    val lines = resultTl.split("\n")

    // Render each line separately with its specific style
    if (lines.size >= 6) {
        Text(
            text = lines[1],
            style = MaterialTheme.typography.titleMedium,
            color = textTerm,
            modifier = Modifier.padding(5.dp)
        )
        Text(
            text = lines[2],
            style = MaterialTheme.typography.titleMedium,
            color = appYellow,
            modifier = Modifier.padding(5.dp)
        )
        Text(
            text = lines[3],
            style = MaterialTheme.typography.titleMedium,
            color = textOtherTerms,
            modifier = Modifier.padding(5.dp)
        )
        Text(
            text = lines[4],
            style = MaterialTheme.typography.headlineSmall,
            color = textTerm,
            modifier = Modifier.padding(5.dp)
        )
        Text(
            text = lines[5],
            style = MaterialTheme.typography.headlineSmall,
            color = textSentence,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
fun ProgressDialog() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun CommentCard(
    color: Color,
    commentVisible: Boolean,
    saveCommentListener: (String) -> Unit
) {
    var comment by remember {
        mutableStateOf("")
    }
    if (!commentVisible) {
        comment = ""
    }
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        if (commentVisible) {
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

                //.height(200.dp)
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    value = comment,
                    textStyle = TextStyle(
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    ),
                    onValueChange = { txt ->
                        comment = txt
                    },
                    placeholder = {
                        Text(
                            text = "Enter Comment"
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        cursorColor = Color.White.copy(alpha = ContentAlpha.medium),
                        textColor = Color.Black
                    ),
                    maxLines = 3,
                    minLines = 1
                )
                Spacer(
                    modifier = Modifier
                        .size(5.dp)
                )
                Button(
                    onClick = {
                        saveCommentListener(comment)
                    }
                ) {
                    Text(
                        text = "Save Comment",
                        color = Color.White
                    )
                }
            }
        }

    }


}

@Composable
private fun CommentListView(
    showCommentList: Boolean,
    list: List<CommentWord>
) {

    if (showCommentList) {
        if (list.isNotEmpty()) {
            Spacer(
                modifier = Modifier
                    .size(5.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "User Comments:")
                Spacer(
                    modifier = Modifier
                        .size(5.dp)
                )
                list.forEachIndexed { index, comment ->
                    Text(text = "${index + 1}. ${comment.getComments()!!}")
                }
            }

//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(100.dp)
//                        .padding(10.dp)
//                        .verticalScroll(rememberScrollState()),
//                    horizontalAlignment = Alignment.Start
//                ) {
//                    item {
//                        Text(text = "User Comments:")
//                    }
//                    itemsIndexed(list) { index, comment ->
//                        Spacer(
//                            modifier = Modifier
//                                .size(5.dp)
//                        )
//                        Text(text = "${index + 1}. ${comment.getComments()!!}")
//
//                    }
//                }
        }
    }

}