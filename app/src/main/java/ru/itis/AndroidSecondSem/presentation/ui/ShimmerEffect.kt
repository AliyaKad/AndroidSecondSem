package ru.itis.AndroidSecondSem.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerEffect() {
    Box(
        modifier = Modifier
            .size(width = 250.dp, height = 24.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.LightGray, Color.Gray, Color.LightGray),
                    start = Offset(0f, 0f),
                    end = Offset(300f, 0f)
                ),
                shape = RoundedCornerShape(4.dp)
            )
    )
}