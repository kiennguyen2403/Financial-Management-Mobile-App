package com.example.customproject.ui.calendar

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.customproject.databinding.FragmentCalendarBinding
import com.github.sundeepk.compactcalendarview.CompactCalendarView.CompactCalendarViewListener
import java.util.*

class CalendarFragment : Fragment() {

    private lateinit var viewModel: CalendarViewModel
    private val binding get() = _binding!!
    private var _binding: FragmentCalendarBinding? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[CalendarViewModel::class.java]
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)


        val root:View = binding.root

        val calendar = binding.compactcalendarView
        val date = binding.date
        date.text = (Calendar.getInstance().get(Calendar.MONTH)+1).toString()+"-"+Calendar.getInstance().get(Calendar.YEAR).toString()
        viewModel.getEvent().observe(viewLifecycleOwner) { events ->
            if (events.isNotEmpty()) {
                events.forEach {
                    calendar.addEvent(it)
                }
            }
        }
        calendar.setListener(object : CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
               val events = calendar.getEvents(dateClicked)
//                var incomeNum =0
//                var spendingNum = 0
//                events.forEach {
//                    if( it.color==-65536)
//                    {
//                        spendingNum+=1
//                    }
//                    else{
//                        incomeNum+=1
//                    }
//                }
//                val myToast = Toast.makeText(context, "You have make "+incomeNum.toString() +" income transactions and "+spendingNum.toString()+" spending transactions"  , Toast.LENGTH_SHORT)
//                myToast.setGravity(Gravity.START, 200, 200)
//                myToast.show()
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {
              date.text = (firstDayOfNewMonth.month+1).toString()+"-20"+(firstDayOfNewMonth.year).toString().slice(1..2)
            }
        })
        return root
    }
}