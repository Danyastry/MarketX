package com.example.pet_shop.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.sp
import com.example.pet_shop.R

val montserrat_reg = Font(R.font.montserrat_reg).toFontFamily()
val montserrat_bold = Font(R.font.montserrat_bold).toFontFamily()
val gravitas_reg = Font(R.font.gratitas_reg).toFontFamily()

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = montserrat_reg,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

)