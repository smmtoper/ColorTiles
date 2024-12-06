package com.example.colortiles

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlin.random.Random

class Tiles(ctx: Context) : View(ctx) {
    private val field = Array(4) { BooleanArray(4) { Random.nextBoolean() } }
    private val paint = Paint()

    override fun onDraw(canvas: Canvas) {
        val cellSize = (width.coerceAtMost(height) / 4).toFloat()
        for (row in 0..3) {
            for (col in 0..3) {
                paint.color = if (field[row][col]) Color.WHITE else Color.BLACK
                canvas.drawRect(
                    col * cellSize, row * cellSize,
                    (col + 1) * cellSize, (row + 1) * cellSize, paint
                )
            }
        }
        paint.color = Color.GRAY
        paint.strokeWidth = 5f
        for (i in 0..4) {
            canvas.drawLine(i * cellSize, 0f, i * cellSize, cellSize * 4, paint)
            canvas.drawLine(0f, i * cellSize, cellSize * 4, i * cellSize, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val cellSize = (width.coerceAtMost(height) / 4).toFloat()
            val col = (event.x / cellSize).toInt()
            val row = (event.y / cellSize).toInt()

            if (row in 0..3 && col in 0..3) {
                for (i in 0..3) {
                    field[row][i] = !field[row][i]
                    field[i][col] = !field[i][col]
                }
                field[row][col] = !field[row][col]
                invalidate()

                if (field.all { row -> row.all { it == field[0][0] } }) {
                    Toast.makeText(context, "Победа", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return true
    }
}
