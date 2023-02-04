package com.meetozan.caloriecounter.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.meetozan.caloriecounter.R
import com.meetozan.caloriecounter.ui.main.profile.UserViewModel
import com.meetozan.caloriecounter.data.User
import com.meetozan.caloriecounter.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val dbUser = Firebase.firestore.collection("users")
    private lateinit var binding: FragmentMainBinding
    private lateinit var auth: FirebaseAuth
    private val viewModel by lazy { UserViewModel() }

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

        dbUser.document(auth.currentUser?.email.toString())
            .get()
            .addOnSuccessListener {
                val user = it.toObject<User>()
                if (user?.calorieGoal == -1) {
                    findNavController().navigate(R.id.action_mainFragment_to_viewPagerFragment)
                }
            }

        observer()
    }

    private fun observer() {
        viewModel.userInfo.observe(viewLifecycleOwner) {
            binding.txtName.text = it.name
        }
    }
}