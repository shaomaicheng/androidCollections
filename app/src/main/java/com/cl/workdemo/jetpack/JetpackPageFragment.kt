package com.cl.workdemo.jetpack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cl.workdemo.databinding.FragmentJetpackBinding

class JetpackPageFragment: Fragment() {
    private lateinit var binding: FragmentJetpackBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJetpackBinding.inflate(inflater, container, false)
            return binding.root
    }
}