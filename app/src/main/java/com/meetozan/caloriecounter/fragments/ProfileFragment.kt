package com.meetozan.caloriecounter.fragments

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.meetozan.caloriecounter.LoginActivity
import com.meetozan.caloriecounter.R
import com.meetozan.caloriecounter.data.User
import com.meetozan.caloriecounter.databinding.FragmentProfileBinding
import com.mikhaellopez.circularprogressbar.CircularProgressBar

@Suppress("CAST_NEVER_SUCCEEDS")
class ProfileFragment : Fragment() {

    private val dbUser = Firebase.firestore.collection("users")
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        readData()

        binding.circularProgressBar.apply {
            setProgressWithAnimation(65f, 1000)

            progressMax = 200f
            progressBarColorStart = R.color.primaryColor
            progressBarColorEnd = Color.GREEN
            progressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM

            progressBarWidth = 7f
            backgroundProgressBarWidth = 3f

            roundBorder = true
            progressDirection = CircularProgressBar.ProgressDirection.TO_RIGHT
        }

        binding.btnEditProfile.setOnClickListener {

            val dialog = LayoutInflater.from(context).inflate(R.layout.edit_profile_dialog, null)
            val builder = AlertDialog.Builder(context).setView(dialog).show()
            builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            builder.setCancelable(true)


        }

        binding.imageLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }
    }

    private fun readData() {
        dbUser.document(auth.currentUser?.email.toString())
            .get()
            .addOnSuccessListener {
                val user = it.toObject<User>()
                binding.profileName.text = user?.name
                binding.profileHeight.text = user?.height
                binding.profileWeight.text = user?.weight
                binding.profileCalorieProgress.text = user?.calorieGoal.toString()
            }
    }
}