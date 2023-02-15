package com.meetozan.caloriecounter.ui.main.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meetozan.caloriecounter.databinding.FragmentCalendarBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    private lateinit var rv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv = binding.recyclerView
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv.layoutManager = linearLayoutManager

        //binding.txtTotalCalorie.setText(adapter.totalCalorie)

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val current = LocalDate.now()
        binding.txtCalendar.text = current.format(formatter)

        var clickCounter = 1

        binding.imageLeft.setOnClickListener {
            if (clickCounter < 6) {
                binding.txtCalendar.text =
                    current.minusDays(clickCounter.toLong()).format(formatter).toString()
                clickCounter += 1
            } else {
                Toast.makeText(requireContext(), "You can see only 5 days ago", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
}