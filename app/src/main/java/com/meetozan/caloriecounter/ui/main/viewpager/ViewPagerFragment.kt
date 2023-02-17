package com.meetozan.caloriecounter.ui.main.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.meetozan.caloriecounter.databinding.FragmentViewPagerBinding
import com.meetozan.caloriecounter.ui.main.viewpager.screens.FirstScreenFragment
import com.meetozan.caloriecounter.ui.main.viewpager.screens.SecondScreenFragment
import com.meetozan.caloriecounter.ui.main.viewpager.screens.ThirdScreenFragment

class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = arrayListOf(
            FirstScreenFragment(),
            SecondScreenFragment(),
            ThirdScreenFragment()
        )

        val adapter =
            ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)

        binding.viewpager.adapter = adapter

    }
}