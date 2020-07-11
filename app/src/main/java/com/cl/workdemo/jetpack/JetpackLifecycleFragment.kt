package com.cl.workdemo.jetpack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cl.workdemo.databinding.FragmentJetpackLifecycleBinding

class JetpackLifecycleFragment:Fragment() {

    private lateinit var binding: FragmentJetpackLifecycleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJetpackLifecycleBinding.inflate(inflater, container, false)
        return binding.root
    }
}