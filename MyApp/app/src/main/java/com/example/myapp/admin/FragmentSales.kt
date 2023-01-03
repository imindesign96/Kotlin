package com.example.myapp.admin

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment

import com.example.myapp.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate


class FragmentSales : Fragment(R.layout.fragment_sales) {

    private lateinit var pieChart1 : PieChart
    private lateinit var pieChart2 : PieChart
    private lateinit var pieChart3 : PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_sales, container, false)
        pieChart1 = view.findViewById(R.id.chart1)
        pieChart2 = view.findViewById(R.id.chart2)
        pieChart3 = view.findViewById(R.id.chart3)

        val items = arrayOf("本日", "月次")
        val adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, items) }
        val spinner  = view.findViewById<Spinner>(R.id.spinner)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                showShinki()
                showKeiyaku()
                showSales()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }



        return view
    }

    private fun showShinki(){

        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(PieEntry(90f, "GB"))

        val colors: ArrayList<Int> = ArrayList()
        val pieDataSet = PieDataSet(pieEntries, "データ")

        colors.add(ColorTemplate.COLORFUL_COLORS[0])
        colors.add(ColorTemplate.COLORFUL_COLORS[1])
        colors.add(ColorTemplate.COLORFUL_COLORS[2])
        colors.add(ColorTemplate.COLORFUL_COLORS[3])
        pieDataSet.setColors(Color.parseColor("#2979FF"))

        val pieData = PieData(pieDataSet)

        pieChart1.data = pieData

        pieData.setValueTextColor(Color.WHITE)
        pieData.setValueTextSize(14f)
    }
    private fun showKeiyaku(){

        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(PieEntry(90f, "GB"))

        val colors: ArrayList<Int> = ArrayList()
        val pieDataSet = PieDataSet(pieEntries, "データ")

        colors.add(ColorTemplate.COLORFUL_COLORS[0])
        colors.add(ColorTemplate.COLORFUL_COLORS[1])
        colors.add(ColorTemplate.COLORFUL_COLORS[2])
        colors.add(ColorTemplate.COLORFUL_COLORS[3])
        pieDataSet.setColors(Color.parseColor("#2979FF"))

        val pieData = PieData(pieDataSet)

        pieChart2.data = pieData

        pieData.setValueTextColor(Color.WHITE)
        pieData.setValueTextSize(14f)
    }
    private fun showSales(){

        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(PieEntry(90f, "GB"))

        val colors: ArrayList<Int> = ArrayList()
        val pieDataSet = PieDataSet(pieEntries, "データ")

        colors.add(ColorTemplate.COLORFUL_COLORS[0])
        colors.add(ColorTemplate.COLORFUL_COLORS[1])
        colors.add(ColorTemplate.COLORFUL_COLORS[2])
        colors.add(ColorTemplate.COLORFUL_COLORS[3])
        pieDataSet.setColors(Color.parseColor("#2979FF"))

        val pieData = PieData(pieDataSet)

        pieChart3.data = pieData

        pieData.setValueTextColor(Color.WHITE)
        pieData.setValueTextSize(14f)
    }
}