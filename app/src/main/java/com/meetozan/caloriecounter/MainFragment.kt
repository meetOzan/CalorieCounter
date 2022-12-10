package com.meetozan.caloriecounter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.meetozan.caloriecounter.databinding.FragmentMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {

    private val db = Firebase.firestore.collection("users")
    private lateinit var binding: FragmentMainBinding
    private lateinit var auth: FirebaseAuth

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

        readName()

        binding.cardAddMeal.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_addMealFragment)
        }

        binding.cardCalendar.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_calendarFragment)
        }

        binding.cardFoods.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_foodsFragment)
        }

        binding.cardLeaderboard.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_leaderboardFragment)
        }

    }

    private fun readName() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                db.document(auth.currentUser?.email.toString())
                    .get()
                    .addOnSuccessListener {
                        val user = it.toObject<User>()
                        binding.txtName.text = user?.name.toString()
                    }.await()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}