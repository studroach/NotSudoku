package com.example.notsudoku

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.GestureDetectorCompat
import kotlin.math.floor
import kotlin.math.min

class Gridbuttons(context: Context, attrs: AttributeSet?) : View(context, attrs), GestureDetector.OnGestureListener {

    private var lineColor = Color.DKGRAY
    private var fillColor = Color.CYAN
    private var textFillColor = Color.BLACK

    private val paint = Paint()
    private val textPaint = Paint()
    private val thisTextBound = Rect()

    private var counter = arrayOf(arrayOf(0,0,0,0,0,0,0,0,0),
                                  arrayOf(0,0,0,0,0,0,0,0,0),
                                  arrayOf(0,0,0,0,0,0,0,0,0),
                                  arrayOf(0,0,0,0,0,0,0,0,0),
                                  arrayOf(0,0,0,0,0,0,0,0,0),
                                  arrayOf(0,0,0,0,0,0,0,0,0),
                                  arrayOf(0,0,0,0,0,0,0,0,0),
                                  arrayOf(0,0,0,0,0,0,0,0,0),
                                  arrayOf(0,0,0,0,0,0,0,0,0))

    private var width = 0.0f
    private var height = 0.0f
    private var verticalBuff = 0.0f
    private var horizontalBuff = 0.0f
    private var shapeWidth = 0.0f
    private var cellWidth = 0.0f
    private var halfCell = 0.0f

    private var mDetector = GestureDetectorCompat(this.context, this)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        width = w.toFloat()
        height = h.toFloat()

        if(width > height){
            verticalBuff = 0f
            horizontalBuff = (width - height) / 2
            cellWidth = height / 9
            shapeWidth = height
        }else{
            verticalBuff = (height - width) / 2
            horizontalBuff = 0f
            cellWidth = width / 9
            shapeWidth = width
        }

        halfCell = cellWidth / 2

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(mDetector.onTouchEvent(event)){
            return true
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val shell = Rect(horizontalBuff.toInt(),
                verticalBuff.toInt(),
                (width - horizontalBuff).toInt(),
                (height - verticalBuff).toInt());

        paint.color = fillColor
        canvas?.drawRect(shell, paint)

        paint.color = lineColor
        paint.strokeWidth = 5f
        for(i in 0..9){
            canvas?.drawLine((cellWidth*i) + horizontalBuff, verticalBuff, (cellWidth*i) + horizontalBuff, height-verticalBuff, paint)
            canvas?.drawLine(horizontalBuff, (cellWidth*i) + verticalBuff, width-horizontalBuff, (cellWidth*i) + verticalBuff, paint)
        }

        textPaint.color = textFillColor
        textPaint.style = Paint.Style.FILL_AND_STROKE
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.textSize = 10f

        textPaint.getTextBounds("0", 0, 1, thisTextBound)
        textPaint.textSize = (10f * min((cellWidth * .9)/(thisTextBound.width()),
                                        (cellWidth * .9)/(thisTextBound.height()))).toFloat()

        for(y in 0..8){
            for(x in 0..8){
                canvas?.drawText(counter[x][y].toString(), horizontalBuff + halfCell + (x * cellWidth), verticalBuff + (cellWidth * .9f) + (y * cellWidth), textPaint)
            }
        }
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent?) {
        //TODO("Not yet implemented")
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        if(e != null){
            var col = floor((e.x - horizontalBuff)/cellWidth).toInt()
            var row = floor((e.y - verticalBuff)/cellWidth).toInt()
            if(counter[col][row] == 9){
                counter[col][row] = 0
                invalidate()
            }else{
                counter[col][row] = (counter[col][row] + 1)
                invalidate()
            }
        }
        return true
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
        //TODO("Not yet implemented")
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        //TODO("Not yet implemented")
        return false
    }
}