package com.oneplatform.obeng.ui.theme

import android.widget.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.oneplatform.obeng.R

// Set of Material typography styles to start with
val typography = Typography(
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(R.font.helvetica_neue_bold))
    ),
    labelSmall = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.helvetica_neue_regular))
    ),
    titleLarge = TextStyle(
        fontSize = 12.sp,
        fontFamily = FontFamily(Font(R.font.helvetica_neue_regular))
    )
    /* Other default text styles to override
    button = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.W500,
    fontSize = 14.sp
    ),
    caption = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
    )
    */
)