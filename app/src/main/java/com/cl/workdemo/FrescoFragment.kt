package com.cl.workdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cl.workdemo.databinding.FragmentFrescoBinding

class FrescoFragment:Fragment() {
    var binding : FragmentFrescoBinding? =null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFrescoBinding.inflate(inflater,container,false)
        return binding!!.root
    }
}