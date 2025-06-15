package ru.itis.AndroidSecondSem.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.*

class CircularDiagramView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val sectorValues = mutableListOf<Float>()
    private val sectorColors = mutableListOf<Int>()
    private var selectedSectorIndex = -1

    private var outerRadius = 0f
    private var innerRadius = 80f
    private var ringWidth = 40f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = ringWidth
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 50f
        textAlign = Paint.Align.LEFT
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        outerRadius = minOf(w, h) / 2f * 0.9f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (sectorValues.isEmpty() || sectorColors.isEmpty()) return

        val centerX = width / 2f
        val centerY = height / 2f

        var currentRadius = innerRadius + ringWidth / 2

        for (i in sectorValues.indices) {
            val value = sectorValues[i]
            val color = sectorColors[i]

            paint.color = if (i == selectedSectorIndex) Color.YELLOW else color

            val sweepAngle = (value / 100f) * 360f

            canvas.drawArc(
                RectF(
                    centerX - currentRadius,
                    centerY - currentRadius,
                    centerX + currentRadius,
                    centerY + currentRadius
                ),
                90f,
                sweepAngle,
                false,
                paint
            )

            currentRadius += ringWidth
        }

        drawPercentageLabels(canvas)
    }

    private fun drawPercentageLabels(canvas: Canvas) {
        val startX = width - 100f
        val startY = height / 2f - (sectorValues.size * 60).toFloat()

        for (i in sectorValues.indices) {
            val value = sectorValues[i]
            val color = if (i == selectedSectorIndex) Color.YELLOW else sectorColors[i]

            textPaint.color = color

            val percent = value.toInt()
            canvas.drawText("$percent%", startX, startY + i * 70, textPaint)
        }
    }


    fun setSectorValues(values: List<Float>) {
        sectorValues.clear()
        for (value in values) {
            val clamped = value.coerceIn(0f..100f)
            sectorValues.add(clamped)
        }
        invalidate()
    }

    fun setSectorColors(colors: List<Int>) {
        sectorColors.clear()
        sectorColors.addAll(colors)
        invalidate()
    }

    fun setInnerRadius(radius: Float) {
        innerRadius = radius
        invalidate()
    }

    fun setRingWidth(width: Float) {
        ringWidth = width
        paint.strokeWidth = width
        invalidate()
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action != MotionEvent.ACTION_DOWN) return true

        val x = event.x
        val y = event.y
        val centerX = width / 2f
        val centerY = height / 2f

        for (i in sectorValues.indices.reversed()) {
            val value = sectorValues[i]
            val sweepAngle = (value / 100f) * 360f

            val distance = hypot(x - centerX, y - centerY)
            val radius = innerRadius + ringWidth / 2 + i * ringWidth

            if (distance in (radius - ringWidth / 2)..(radius + ringWidth / 2)) {
                selectedSectorIndex = if (selectedSectorIndex == i) -1 else i
                invalidate()
                break
            }
        }

        return true
    }
}