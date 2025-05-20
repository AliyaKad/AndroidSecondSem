package ru.itis.AndroidSecondSem.presentation.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Path


@Composable
fun Graph(points: List<Float>) {
    val maxValue = points.maxOrNull() ?: 0f
    val path = Path()

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val padding = 20.dp.toPx()
        val graphWidth = size.width - 2 * padding
        val graphHeight = size.height - 2 * padding
        val stepX = graphWidth / (points.size - 1)

        drawLine(
            start = Offset(padding, padding),
            end = Offset(padding, size.height - padding),
            color = Color.Black,
            strokeWidth = 2f
        )
        drawLine(
            start = Offset(padding, size.height - padding),
            end = Offset(size.width - padding, size.height - padding),
            color = Color.Black,
            strokeWidth = 2f
        )

        points.forEachIndexed { index, value ->
            val x = padding + index * stepX
            val y = size.height - padding - (value / maxValue) * graphHeight

            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }

            drawCircle(
                color = Color.Blue,
                center = Offset(x, y),
                radius = 5f
            )
        }

        path.lineTo(size.width - padding, size.height - padding)
        path.lineTo(padding, size.height - padding)
        path.close()

        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(width = 2f)
        )

        val brush = Brush.verticalGradient(
            colors = listOf(Color.Blue.copy(alpha = 0.5f), Color.Transparent),
            startY = padding,
            endY = size.height - padding
        )

        drawPath(
            path = path,
            brush = brush
        )
    }
}