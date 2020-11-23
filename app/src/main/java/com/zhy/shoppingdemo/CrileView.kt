package com.zhy.shoppingdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

class CrileView(context: Context):View(context) {
    private var mHeight = 0
    private var mWidth = 0
    private lateinit var mPaint:Paint
    private var radius = 0f
    init {
        mPaint = Paint().apply {
            style = Paint.Style.FILL_AND_STROKE
            isAntiAlias = true
            color = Color.RED
        }
    }
    fun setWH(width:Int,height:Int,x:Int,y:Int) {
        val params = ConstraintLayout.LayoutParams(0,0)
        params?.apply {
            this.width = width
            this.height = height

        }
        layoutParams = params
        //设置xy坐标
        setX(x.toFloat())
        setY(y.toFloat())
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mHeight = h - 20
        mWidth = w - 20
        radius = Math.min(mWidth,mHeight).toFloat() / 2
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(mWidth * 1.0f / 2,mHeight * 1.0f / 2,radius,mPaint)
    }
}