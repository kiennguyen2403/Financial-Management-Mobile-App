package com.example.customproject.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.customproject.databinding.FragmentHomeBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var spendingpieChart:PieChart
    private lateinit var incomepieChart: PieChart


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.total
        val totalincome:TextView = binding.totalincome
        val totalspending:TextView = binding.totalspending
        val progressBar:ProgressBar = binding.progressBar2

        homeViewModel.getTotal(progressBar).observe(viewLifecycleOwner) {
            if (it != null) {
                textView.text = "Total: $it$"
            }
        }

        homeViewModel.totalspending.observe(viewLifecycleOwner){
            totalspending.text = "Total spending: $it$"
        }
        homeViewModel.totalincome.observe(viewLifecycleOwner){
            totalincome.text = "Total income: $it$"
        }


        spendingpieChart= binding.spendingpiechart
        setupPieChart(spendingpieChart)
        homeViewModel.getallSpendingData().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                loadPieChartData(it,spendingpieChart)
            }
        }


        incomepieChart= binding.incomepiechart
        setupPieChart(incomepieChart)
        homeViewModel.getallIncomeData().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
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
        var labelname=""
        var total = 0F
        transactionlist.forEach { item->
            if (labelname!=item.keys.first()) {
                if (labelname!="") {
                    entries.add(PieEntry(item[item.keys.first()].toString().toFloat(), item.keys.first()))
                }
                total = 0F
                labelname = item.keys.first()
            }
            else{
                total.plus(item[item.keys.first()].toString().toFloat())

            }
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

    override fun onResume() {
        super.onResume()
        Log.d("200","Resume")
    }
}