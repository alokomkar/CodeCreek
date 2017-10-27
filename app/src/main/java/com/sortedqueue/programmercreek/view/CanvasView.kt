package com.sortedqueue.programmercreek.view

/**
 * Created by Alok on 03/04/17.
 */

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CanvasView(internal var context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var mBitmap: Bitmap? = null
    private var mCanvas: Canvas? = null
    private val mPath: Path
    private val mPaint: Paint
    private var mX: Float = 0.toFloat()
    private var mY: Float = 0.toFloat()

    init {

        // we set a new Path
        mPath = Path()

        // and we set a new Paint with the desired attributes
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeWidth = 4f
    }

    // override onSizeChanged
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // your Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap!!)
    }

    // override onDraw
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // draw the mPath with the mPaint on the canvas when onDraw
        canvas.drawPath(mPath, mPaint)
    }

    // when ACTION_DOWN start touch according to the x,y values
    private fun startTouch(x: Float, y: Float) {
        mPath.moveTo(x, y)
        mX = x
        mY = y
    }

    // when ACTION_MOVE move touch according to the x,y values
    private fun moveTouch(x: Float, y: Float) {
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    fun clearCanvas() {
        mPath.reset()
        invalidate()
    }

    // when ACTION_UP stop touch
    private fun upTouch() {
        mPath.lineTo(mX, mY)
    }

    //override the onTouchEvent
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startTouch(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                moveTouch(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                upTouch()
                invalidate()
            }
        }
        return true
    }

    companion object {
        private val TOLERANCE = 5f
    }
}
