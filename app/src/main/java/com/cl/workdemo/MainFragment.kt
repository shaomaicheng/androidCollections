package com.cl.workdemo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.cl.workdemo.databinding.FragmentMainBinding
import com.cl.workdemo.databinding.ItemMainListBinding
import com.cl.workdemo.meta.MainTitle
import java.util.zip.Inflater

class MainFragment : Fragment() {

    private var binding : FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding!!.root
        init()
        return view
    }

    private fun init() {
        context?.let {context->
            binding?.recyclerView?.adapter = MainAdapter(context, arrayListOf(
                MainTitle("网络请求")
            )) {mainTitle->
                NavHostFragment.findNavController(this)
                    .navigate(R.id.action_mainFragment_to_networkFragment)
            }
        }
    }

}

class MainAdapter(val context: Context, val titles: List<MainTitle>, val itemClick: (MainTitle)->Unit) : RecyclerView.Adapter<MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(ItemMainListBinding.inflate(
            LayoutInflater.from(context),parent,false
        ),itemClick)
    }

    override fun getItemCount(): Int = titles.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(titles[position])
    }

}

class MainViewHolder(val binding:ItemMainListBinding, val itemClick:(MainTitle)->Unit) : RecyclerView.ViewHolder(binding.root) {
    fun bind(mainTitle: MainTitle){
        binding.tvTitle.text = mainTitle.title
        binding.tvTitle.setOnClickListener {
            itemClick(mainTitle)
        }
    }
}
