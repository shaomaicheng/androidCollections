package com.cl.workdemo.jetpack

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.cl.workdemo.databinding.FragmentJetpackBinding

class JetpackPageFragment: Fragment() {

    private val TAG = this::class.java.simpleName

    private lateinit var binding: FragmentJetpackBinding

    private lateinit var dispatcher: OnBackPressedDispatcher
    private lateinit var callback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJetpackBinding.inflate(inflater, container, false)
            return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        callback = object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.e(TAG, "handleOnBackPressed")
                showAlert()
            }

        }
        dispatcher = requireActivity().onBackPressedDispatcher
        dispatcher.addCallback(this, callback)
    }

    private fun showAlert() {
        context?.let {
            AlertDialog.Builder(it)
                .setMessage("是否退出这个页面")
                .setNegativeButton("取消") {dialog, which ->
                    dialog?.dismiss()
                }
                .setPositiveButton("确定") {dialog, which ->
                    dialog?.dismiss()
                    if (::dispatcher.isInitialized && ::callback.isInitialized) {
                        Log.e(TAG, "dispatcher.onBackPressed")
                        callback.isEnabled = false
                        dispatcher.onBackPressed()
                    }
                }
                .create()
                .show()
        }
    }
}