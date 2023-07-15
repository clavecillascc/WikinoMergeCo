package com.clavecillascc.wikinomergeco.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.clavecillascc.wikinomergeco.R
// initializing fonts
val ErasDemiITC = FontFamily(Font(R.font.eras_demi_itc))
val Poppins = FontFamily(Font(R.font.poppins_regular))

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    // Line 1 - Header
    headlineMedium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        color = textHeaderBlack
    ),
    displaySmall = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = textSeeMore
    ),
    labelSmall = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = textHeaderBlack
    ),
    //Line 2 -Translated Word
    titleMedium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        color = textTerm
    ),
    //Line 3 - Other terms
    titleSmall = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = textOtherTerms
    ),
    //Line 4 - Sentence in same language
    bodySmall = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = textTerm
    ),
    //Line 5 & 6
    headlineSmall = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = textSentence,
        fontStyle = FontStyle.Italic
    ),
    //normalFont
    labelMedium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = textSeeMore
    )
/* Other default text styles to override
titleLarge = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 22.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp
),
labelSmall = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Medium,
    fontSize = 11.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp
)
*/
)