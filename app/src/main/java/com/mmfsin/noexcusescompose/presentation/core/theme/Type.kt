package com.mmfsin.noexcusescompose.presentation.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mmfsin.noexcusescompose.R

val alphazet = FontFamily(Font(R.font.alphazet, weight = FontWeight.Normal))
val august_shining = FontFamily(Font(R.font.august_shining, weight = FontWeight.Normal))
val barlow = FontFamily(Font(R.font.barlow, weight = FontWeight.Normal))
val courier = FontFamily(Font(R.font.courier, weight = FontWeight.Normal))
val kineks = FontFamily(Font(R.font.kineks, weight = FontWeight.Normal))
val manaspace = FontFamily(Font(R.font.manaspace, weight = FontWeight.Normal))
val montserrat_regular = FontFamily(Font(R.font.montserrat_regular, weight = FontWeight.Normal))
val montserrat_bold = FontFamily(Font(R.font.montserrat_bold, weight = FontWeight.Normal))

// Set of Material typography styles to start with
val Typography = Typography(
    bodySmall = TextStyle(
        fontFamily = montserrat_regular,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),

    bodyLarge = TextStyle(
        fontFamily = montserrat_regular,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),

    titleLarge = TextStyle(
        fontFamily = montserrat_regular,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),
)