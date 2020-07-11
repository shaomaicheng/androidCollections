package com.cl.workdemo.jetpack

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
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
        jetpackLifecycleVM =  ViewModelProvider(this)[JetpackLifecycleViewModel::class.java]

        binding.lifecycleOwner = this
        binding.vm = jetpackLifecycleVM

        binding.clicker = View.OnClickListener {
            when (it.id) {
                R.id.btnInstallOrUninstall -> {

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

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    private var lifecycleOwner : LifecycleOwner = LifecycleOwnerWrapper(context as FragmentActivity)

    

}

class LifecycleOwnerWrapper(val owner: LifecycleOwner) : LifecycleOwner {

    private var lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(owner)

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

}