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
import com.cl.workdemo.databinding.FragmentJetpackChildBinding

class JetpackChildPageFragment : Fragment() {

    private val TAG = this::class.simpleName

    private lateinit var binding: FragmentJetpackChildBinding

    private lateinit var dispatcher: OnBackPressedDispatcher
    private lateinit var callback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJetpackChildBinding.inflate(inflater, container, false)
        binding.jetpackText.setOnClickListener {

        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dispatcher = requireActivity().onBackPressedDispatcher
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.e(TAG, "handleOnBackPressed")
                showBackDialog()
            }
        }
        dispatcher.addCallback(this, callback)
    }

    private fun showBackDialog() {
        context?.let {
            AlertDialog.Builder(it)
                .setPositiveButton("关闭") {dialog, which ->
                    dialog?.dismiss()
                    callback.isEnabled = false
                    dispatcher.onBackPressed()
                }
                .create()
                .show()
        }
    }

}