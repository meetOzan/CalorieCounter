package com.meetozan.caloriecounter.ui.main.viewpager.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.meetozan.caloriecounter.R
import com.meetozan.caloriecounter.ui.main.profile.UserViewModel
import com.meetozan.caloriecounter.databinding.FragmentThirdScreenBinding

class ThirdScreenFragment : Fragment() {

    private lateinit var binding: FragmentThirdScreenBinding
    private val viewModel by lazy { UserViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThirdScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.thirdNext.setOnClickListener {
            if (binding.etCalorieGoal.text.isNotEmpty()) {
                val calorieGoal = Integer.parseInt(binding.etCalorieGoal.text.toString())
                viewModel.setSingleData(calorieGoal.toString(),"calorie")
                it.findNavController().navigate(R.id.action_viewPagerFragment_to_mainFragment)
            } else {
                Toast.makeText(requireContext(), "Enter calorie goal first.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
