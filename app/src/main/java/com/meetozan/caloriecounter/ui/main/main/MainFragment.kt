package com.meetozan.caloriecounter.ui.main.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.meetozan.caloriecounter.R
import com.meetozan.caloriecounter.data.User
import com.meetozan.caloriecounter.databinding.FragmentMainBinding
import com.meetozan.caloriecounter.ui.main.profile.UserViewModel

class MainFragment : Fragment() {

    private val dbUser = Firebase.firestore.collection("users")
    private lateinit var binding: FragmentMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var rv: RecyclerView
    private lateinit var adapter: MainAdapter
    private val userViewModel by lazy { UserViewModel() }
    private val mainViewModel by lazy { MainViewModel(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        rv = binding.recyclerView
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv.layoutManager = linearLayoutManager

        dbUser.document(auth.currentUser?.email.toString())
            .get()
            .addOnSuccessListener {
                val user = it.toObject<User>()
                if (user?.calorieGoal == -1) {
                    findNavController().navigate(R.id.action_mainFragment_to_viewPagerFragment)
                }
            }

        userObserver()
    }

    private fun userObserver() {
        userViewModel.userInfo.observe(viewLifecycleOwner) { user ->
            binding.txtName.text = user.name
            binding.goalCalorie.text = user.calorieGoal.toString()
            binding.currentCalorie.text = user.currentCalorie.toString()

            mainViewModel.mainList.observe(viewLifecycleOwner) {
                adapter = MainAdapter(it)
                rv.adapter = adapter

                var totalCalorie = 0

                for (index in it.indices) {
                    totalCalorie += Integer.parseInt(it[index].calorie) * Integer.parseInt(it[index].many)
                }

                binding.currentCalorie.text = totalCalorie.toString()

                if (totalCalorie > Integer.parseInt(binding.goalCalorie.text.toString())) {
                    binding.currentCalorie.setTextColor(Color.RED)
                }
                if (rv.isNotEmpty()) {
                    binding.nothingLinearLayout.visibility = View.VISIBLE
                } else {
                    binding.nothingLinearLayout.visibility = View.INVISIBLE
                }

            }
        }
    }
}