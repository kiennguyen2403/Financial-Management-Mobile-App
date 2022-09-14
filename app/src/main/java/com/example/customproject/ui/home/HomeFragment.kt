package com.example.customproject.ui.home

import android.graphics.Color
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.customproject.controller.TransactionController
import com.example.customproject.databinding.FragmentHomeBinding
import com.example.customproject.model.Transaction
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.Timestamp


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var spendingpieChart:PieChart
    private lateinit var incomepieChart: PieChart


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.getTotal().observe(viewLifecycleOwner) {
            if (it != null) {
                textView.text = "Total: " + it.toString() + "$"
            }
        }


        spendingpieChart= binding.spendingpiechart
        setupPieChart(spendingpieChart);
        homeViewModel.getallSpendingData().observe(viewLifecycleOwner) {
            if (it.size>0) {
                loadPieChartData(it,spendingpieChart)
            }
        }


        incomepieChart= binding.incomepiechart
        setupPieChart(incomepieChart);
        homeViewModel.getallIncomeData().observe(viewLifecycleOwner) {
            if (it.size>0) {
                loadPieChartData(it,incomepieChart)
            }

        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun loadPieChartData(transactionlist: List<Map<String,Long>>,pieChart: PieChart) {
        val entries: ArrayList<PieEntry> = ArrayList()

        transactionlist.forEach { item->
            entries.add(PieEntry(item[item.keys.first()].toString().toFloat(), item.keys.first()))
        }

        val colors: ArrayList<Int> = ArrayList()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }
        val dataSet = PieDataSet(entries, "Expense Category")
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)
        pieChart.data = data
        pieChart.invalidate()
        pieChart.animateY(1400, Easing.EaseInOutQuad)
    }
    private fun setupPieChart(pieChart: PieChart) {
        pieChart.isDrawHoleEnabled = true
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.centerText = "Transaction types by Category"
        pieChart.setCenterTextSize(20f)
        pieChart.description.isEnabled = false
        val l = pieChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = true
    }
}