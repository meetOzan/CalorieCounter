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
    private lateinit var calendarViewModel: CalendarViewModel
    private lateinit var adapter: CalendarAdapter
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val current = LocalDate.now()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        binding.txtCalendar.text = current.format(formatter)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv = binding.recyclerView
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv.layoutManager = linearLayoutManager

        var minusClickCounter = 1
        var plusClickCounter = 1

        if (binding.txtCalendar.text.toString() == current.format(formatter).toString()) {
            calendarViewModel =
                CalendarViewModel(requireContext(), binding.txtCalendar.text.toString())
            observer()
        }

        binding.imageLeft.setOnClickListener {
            if (minusClickCounter < 3) {
                binding.txtCalendar.text =
                    current.minusDays(minusClickCounter.toLong()).format(formatter).toString()
                minusClickCounter += 1
                plusClickCounter -= 1
                calendarViewModel =
                    CalendarViewModel(requireContext(), binding.txtCalendar.text.toString())
                observer()
            } else {
                Toast.makeText(context, "You can only see 2 days ago", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageRight.setOnClickListener {
            if (binding.txtCalendar.text.toString() != current.format(formatter).toString()) {
                binding.txtCalendar.text =
                    current.plusDays(plusClickCounter.toLong()).format(formatter).toString()
                plusClickCounter += 1
                minusClickCounter -= 1
            } else {
                Toast.makeText(context, "Tomorrow is tomorrow", Toast.LENGTH_SHORT).show()
            }
            calendarViewModel =
                CalendarViewModel(requireContext(), binding.txtCalendar.text.toString())
            observer()
        }
    }

    private fun observer() {
        calendarViewModel.calendarList.observe(viewLifecycleOwner) {
            adapter = CalendarAdapter(it)
            rv.adapter = adapter
            binding.txtTotalCalorie.text = adapter.totalCalorie.toString()


            var totalCalorie = 0

            for (index in it.indices) {
                totalCalorie += Integer.parseInt(it[index].calorie) * Integer.parseInt(it[index].many)
            }

            binding.txtTotalCalorie.text = totalCalorie.toString()
        }
    }
}