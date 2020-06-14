package com.cl.workdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cl.workdemo.databinding.FragmentFrescoBinding
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.DraweeHolder

class FrescoFragment:Fragment() {
    var binding : FragmentFrescoBinding? =null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFrescoBinding.inflate(inflater,container,false)
        init()
        return binding!!.root
    }

    private fun init() {
        binding?.let {binding->
            val uri = getUriFromDrawableRes(R.drawable.fresco_test)
            binding.simpleImg1.setImageResource(R.drawable.fresco_test)
            binding.simpleDraweeView1.setImageURI(uri)
            binding.simpleDraweeView2.setUri(uri)
        }
    }

    fun getUriFromDrawableRes(id:Int):String {
        return "res:///${id}"
    }
}

class CustomSimpleDraweeView: View {
    private var mDraweeHolder: DraweeHolder<GenericDraweeHierarchy>
    private val paint = Paint()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mDraweeHolder.onDetach()
    }

    override fun onStartTemporaryDetach() {
        super.onStartTemporaryDetach()
        mDraweeHolder.onDetach()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mDraweeHolder.onAttach()
    }

    override fun onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach()
        mDraweeHolder.onAttach()
    }

    init {
        val hierarchy = GenericDraweeHierarchyBuilder(resources)
            .build()
        mDraweeHolder = DraweeHolder.create(hierarchy, context)
        mDraweeHolder.topLevelDrawable.callback = this
    }

    override fun verifyDrawable(who: Drawable): Boolean {
        if (who == mDraweeHolder.topLevelDrawable) {
            return true
        }
        return super.verifyDrawable(who)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val drawable = mDraweeHolder.topLevelDrawable
        drawable.setBounds(0, 0, width, height)
        canvas?.let {
            drawable.draw(canvas)
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 5f
            paint.color = Color.BLUE
            paint.isAntiAlias = true
            val center = (width / 2).toFloat()
            val radius = center  - 5
            canvas.drawCircle(center, center, radius, paint)
        }
    }

    fun setUri(uri:String) {
        val controller = Fresco.newDraweeControllerBuilder()
            .setUri(uri)
            .setOldController(mDraweeHolder.controller)
            .build()
        mDraweeHolder.controller = controller
        invalidate()
    }

}