package dev.jjerrell.root.records.android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RootRecordsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFF4CAF50),  // Green
            secondary = Color(0xFF8BC34A),  // Light Green
            tertiary = Color(0xFF795548),  // Brown
            background = Color(0xFF2E7D32),  // Dark Green
            surface = Color(0xFF1B5E20),  // Very Dark Green
            error = Color(0xFFB00020),
            onPrimary = Color.White,
            onSecondary = Color.White,
            onTertiary = Color.White,
            onBackground = Color.White,
            onSurface = Color.White,
            onError = Color.White
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF4CAF50),  // Green
            secondary = Color(0xFF8BC34A),  // Light Green
            tertiary = Color(0xFF795548),  // Brown
            background = Color(0xFFC8E6C9),  // Light Green
            surface = Color(0xFFA5D6A7),  // Soft Green
            error = Color(0xFFB00020),
            onPrimary = Color.Black,
            onSecondary = Color.Black,
            onTertiary = Color.Black,
            onBackground = Color.Black,
            onSurface = Color.Black,
            onError = Color.White
        )
    }
    val typography = Typography(
        bodyMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
