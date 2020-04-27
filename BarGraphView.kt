package com.example.bargraphview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class BarGraphView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var graphWidth = 0
    private var graphHeight = 0

    private val paints = arrayOfNulls<Paint>(4)
    private var data = floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f)

    private val colors = intArrayOf(
        Color.parseColor("#2b94cc"),
        Color.parseColor("#8ba109"),
        Color.parseColor("#e2cf00"),
        Color.parseColor("#d45900")
    )

    init {
        for (i in colors.indices) {
            paints[i] = Paint()
            paints[i]!!.color = colors[i]
            paints[i]!!.strokeWidth = 50f
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        graphWidth = right - left
        graphHeight = bottom - top
        val strokeWidth = graphHeight.toFloat()
        for (i in colors.indices) {
            paints[i]!!.strokeWidth = strokeWidth
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.WHITE)
        drawBars(canvas)
    }

    private fun drawBars(canvas: Canvas) {
        val total = computeTotal(data)
        var startX: Float
        var stopX = 0f
        val yAxis = graphHeight / 2.toFloat()
        for (i in data.indices) {
            val barLength = graphWidth * (data[i] / total)
            startX = stopX
            stopX = startX + barLength
            canvas.drawLine(startX, yAxis, stopX, yAxis, paints[i]!!)
        }
    }

    private fun computeTotal(data: FloatArray): Float {
        var total = 0f
        for (datum in data) {
            total += datum
        }
        return total
    }

    fun setData(data: FloatArray) {
        this.data = data
        invalidate()
    }
}
