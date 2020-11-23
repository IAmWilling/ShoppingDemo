package com.zhy.shoppingdemo

import android.animation.ValueAnimator
import android.graphics.Path
import android.graphics.PathMeasure
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity(), ItemClick {
    private val mPath = Path()
    private val mPathMeasure = PathMeasure()
    private lateinit var animate: ValueAnimator
    lateinit var crileView: CrileView
    lateinit var itemAdapter: ItemAdapter
    private var mShopLocation = IntArray(2)
    private var strings = arrayListOf<String>(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeUtil.setStatusBarTransparent(this, true, false)
        setContentView(R.layout.activity_main)
        crileView = CrileView(this)
        itemAdapter = ItemAdapter(strings, R.layout.item)
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerview.adapter = itemAdapter
        listener()
        shop.post{
            shop.getLocationInWindow(mShopLocation)
        }
    }
    //拿到根布局
    fun getRootView(): ViewGroup {
        return (findViewById<ViewGroup>(android.R.id.content)).getChildAt(0) as ViewGroup
    }

    fun listener() {
        itemAdapter.setItemClick(this)
    }

    fun setAnimate(view: View) {

        animate = ValueAnimator.ofFloat(0f, mPathMeasure.length).apply {
            duration = 300
            interpolator = AccelerateInterpolator()
            addUpdateListener {
                val pos = FloatArray(2)
                var value = it.animatedValue as Float
                mPathMeasure.getPosTan(value, pos, null)

                if(pos[1] >= mShopLocation[1] + shop.height / 4) {
                    getRootView()?.removeView(view!!)
                    boximage.setImageResource(R.mipmap.box_close)
                    return@addUpdateListener
                }
                view?.translationX = pos[0]
                view?.translationY = pos[1]
            }
            start()
        }
    }


    override fun itemClick(view: View, position: Int) {
        val crileView = CrileView(this)
        mPath.reset()
        val it = view
        val location = IntArray(2)
        it.getLocationInWindow(location)
        val thatViewCenterX = location[0] + it.width / 2
        val thatViewCenterY = location[1] + it.height / 2
        crileView.setWH(it.width, it.height, location[0], location[1])

        getRootView()?.apply {
//            removeView(crileView)
            addView(crileView)
        }
        val mubiaolocation = IntArray(2)
        shop.getLocationInWindow(mubiaolocation)
        val mbViewCenterX = mubiaolocation[0] + shop.width / 2
        val mbViewCenterY = mubiaolocation[1] + shop.height / 2
        mPath.apply {
            moveTo(thatViewCenterX.toFloat(), thatViewCenterY.toFloat() - 50)
            quadTo(
                thatViewCenterX.toFloat() - it.width * 8,
                thatViewCenterY.toFloat() - it.width * 8,
                mbViewCenterX.toFloat(),
                mbViewCenterY.toFloat()
            )
        }
        mPathMeasure.setPath(mPath, false)
        mPath.close()
        boximage.setImageResource(R.mipmap.box_open)
        setAnimate(crileView)
    }
}