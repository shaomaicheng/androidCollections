package com.cl.workdemo.jetpack

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.cl.workdemo.R
import com.cl.workdemo.databinding.FragmentJetpackLifecycleBinding
import kotlinx.android.synthetic.main.fragment_jetpack_lifecycle.view.*

class JetpackLifecycleFragment:Fragment() {

    private lateinit var binding: FragmentJetpackLifecycleBinding

    private lateinit var jetpackLifecycleVM : JetpackLifecycleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJetpackLifecycleBinding.inflate(inflater, container, false)
        val lifecycleOb = LifecycleEventObserver { source, event ->
            Log.e("lifecycleEventObserver", event.toString())
            when (event) {
                Lifecycle.Event.ON_DESTROY -> {
                    // 销毁逻辑
                    for (index in 0 until binding.stubContainer.childCount) {
                        val child = binding.stubContainer[index]
                        if (child is StubView) {
                            child.release()
                        }
                    }
                }
            }
        }
        val stubVH = StubViewHolder(this, binding).apply {
            addLifecycleObserver(lifecycleOb)
        }
        lifecycle.addObserver(lifecycleOb)
        jetpackLifecycleVM =  ViewModelProvider(this)[JetpackLifecycleViewModel::class.java]

        binding.lifecycleOwner = this
        binding.vm = jetpackLifecycleVM

        binding.clicker = View.OnClickListener {
            when (it.id) {
                R.id.btnInstallOrUninstall -> {
                    if (jetpackLifecycleVM.installed.value == true) {
                        // 拔掉
                        stubVH.unInstall()
                    } else {
                        // 插入
                        stubVH.install()
                    }
                    jetpackLifecycleVM.installed.value = !(jetpackLifecycleVM.installed.value ?: false)
                }
            }
        }

        return binding.root
    }
}

class JetpackLifecycleViewModel : ViewModel() {
    val installed:MutableLiveData<Boolean> = MutableLiveData(false)
}

class StubView : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun release() {
        Log.e("StubView", "release")
    }

    init {
        val tv = TextView(context).apply {
            text= "占位控件"
            gravity = Gravity.CENTER
            setBackgroundColor(Color.BLUE)
            setTextColor(Color.WHITE)
        }
        this.addView(tv)
        val lp = tv.layoutParams
        lp.width= ViewGroup.LayoutParams.MATCH_PARENT
        lp.height = 40
        tv.layoutParams= lp
    }
}

class StubViewHolder(val owner: Fragment, val binding: FragmentJetpackLifecycleBinding) {
    private var lifecycleOwner : LifecycleOwner = LifecycleOwnerWrapper(owner)
    private val TAG = this::class.java.simpleName

    fun addLifecycleObserver(ob: LifecycleObserver) {
        (lifecycleOwner as LifecycleOwnerWrapper).addObserver(ob)
    }

    fun install() {
        val stub = StubView(owner.context)
        (lifecycleOwner as LifecycleOwnerWrapper).markState(Lifecycle.State.STARTED)
        binding.stubContainer.addView(stub)
    }

    fun unInstall() {
        for (index in 0 until binding.stubContainer.childCount) {
            val child = binding.stubContainer[index]
            if (child is StubView) {
                (lifecycleOwner as LifecycleOwnerWrapper).markState(Lifecycle.State.DESTROYED)
                binding.stubContainer.removeView(child)
            }
        }
    }
}

class LifecycleOwnerWrapper(owner: LifecycleOwner) : LifecycleOwner {

    private var lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(owner)

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

    fun markState(state: Lifecycle.State) {
        lifecycleRegistry.currentState = state
    }

    fun addObserver(ob: LifecycleObserver) {
        lifecycleRegistry.addObserver(ob)
    }

}