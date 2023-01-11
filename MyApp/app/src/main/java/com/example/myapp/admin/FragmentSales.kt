package com.example.myapp.admin

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
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
    private lateinit var pieChart4 : PieChart
    private lateinit var pieChart5 : PieChart
    private lateinit var pieChart6 : PieChart
    private lateinit var title1 : TextView
    private lateinit var title2 : TextView
    private lateinit var title3 : TextView
    private lateinit var title4 : TextView
    private lateinit var title5 : TextView
    private lateinit var title6 : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_sales, container, false)
        pieChart1 = view.findViewById(R.id.chart1)
        pieChart2 = view.findViewById(R.id.chart2)
        pieChart3 = view.findViewById(R.id.chart3)
        pieChart4 = view.findViewById(R.id.chart4)
        pieChart5 = view.findViewById(R.id.chart5)
        pieChart6 = view.findViewById(R.id.chart6)
        title1 = view.findViewById(R.id.titleSale1)
        title2 = view.findViewById(R.id.titleSale2)
        title3 = view.findViewById(R.id.titleSale3)
        title4 = view.findViewById(R.id.titleSale4)
        title5 = view.findViewById(R.id.titleSale5)
        title6 = view.findViewById(R.id.titleSale6)


        val items = arrayOf("本日", "月次")
        val adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, items) }
        val spinner  = view.findViewById<Spinner>(R.id.spinner)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
              if(items[position] == "本日"){
                  showShinki()
                  showKeiyaku()
                  showSales()
              }
              else{
                  showShinkiMonth()
                  showKeiyakuMonth()
                  showSalesMonth()
              }

            }
                override fun onNothingSelected(parent: AdapterView<*>) {

                }

        }


        return view
    }

    private fun showShinki() {
        pieChart1.visibility = View.VISIBLE
        pieChart4.visibility = View.GONE
        title1.visibility = View.VISIBLE
        title4.visibility = View.GONE

        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(PieEntry(90f, "GB"))

        val colors: ArrayList<Int> = ArrayList()
        val pieDataSet = PieDataSet(pieEntries, "データ")

        colors.add(ColorTemplate.COLORFUL_COLORS[0])
        colors.add(ColorTemplate.COLORFUL_COLORS[1])
        colors.add(ColorTemplate.COLORFUL_COLORS[2])
        colors.add(ColorTemplate.COLORFUL_COLORS[3])
        pieDataSet.setColors(Color.parseColor("#e76f51"))

        val pieData = PieData(pieDataSet)

        this.pieChart1.data = pieData

        pieData.setValueTextColor(Color.WHITE)
        pieData.setValueTextSize(14f)
    }
    private fun showKeiyaku(){
        pieChart2.visibility = View.VISIBLE
        pieChart5.visibility = View.GONE
        title2.visibility = View.VISIBLE
        title5.visibility = View.GONE

        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(PieEntry(90f, "GB"))

        val colors: ArrayList<Int> = ArrayList()
        val pieDataSet = PieDataSet(pieEntries, "データ")

        colors.add(ColorTemplate.COLORFUL_COLORS[0])
        colors.add(ColorTemplate.COLORFUL_COLORS[1])
        colors.add(ColorTemplate.COLORFUL_COLORS[2])
        colors.add(ColorTemplate.COLORFUL_COLORS[3])
        pieDataSet.setColors(Color.parseColor("#f4a261"))

        val pieData = PieData(pieDataSet)

        pieChart2.data = pieData

        pieData.setValueTextColor(Color.WHITE)
        pieData.setValueTextSize(14f)
    }
    private fun showSales(){

        pieChart3.visibility = View.VISIBLE
        pieChart6.visibility = View.GONE
        title3.visibility = View.VISIBLE
        title6.visibility = View.GONE

        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(PieEntry(90f, "GB"))

        val colors: ArrayList<Int> = ArrayList()
        val pieDataSet = PieDataSet(pieEntries, "データ")

        colors.add(ColorTemplate.COLORFUL_COLORS[0])
        colors.add(ColorTemplate.COLORFUL_COLORS[1])
        colors.add(ColorTemplate.COLORFUL_COLORS[2])
        colors.add(ColorTemplate.COLORFUL_COLORS[3])
        pieDataSet.setColors(Color.parseColor("#e9c46a"))

        val pieData = PieData(pieDataSet)

        pieChart3.data = pieData

        pieData.setValueTextColor(Color.WHITE)
        pieData.setValueTextSize(14f)
    }
    private fun showShinkiMonth() {
        pieChart4.visibility = View.VISIBLE
        pieChart1.visibility = View.GONE
        title1.visibility = View.GONE
        title4.visibility = View.VISIBLE
        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(PieEntry(90f, "GB"))

        val colors: ArrayList<Int> = ArrayList()
        val pieDataSet = PieDataSet(pieEntries, "データ")

        colors.add(ColorTemplate.COLORFUL_COLORS[0])
        colors.add(ColorTemplate.COLORFUL_COLORS[1])
        colors.add(ColorTemplate.COLORFUL_COLORS[2])
        colors.add(ColorTemplate.COLORFUL_COLORS[3])
        pieDataSet.setColors(Color.parseColor("#5e548e"))

        val pieData = PieData(pieDataSet)

        this.pieChart4.data = pieData

        pieData.setValueTextColor(Color.WHITE)
        pieData.setValueTextSize(14f)
    }
    private fun showKeiyakuMonth(){
        pieChart5.visibility = View.VISIBLE
        pieChart2.visibility = View.GONE
        title2.visibility = View.GONE
        title5.visibility = View.VISIBLE

        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(PieEntry(90f, "GB"))

        val colors: ArrayList<Int> = ArrayList()
        val pieDataSet = PieDataSet(pieEntries, "データ")

        colors.add(ColorTemplate.COLORFUL_COLORS[0])
        colors.add(ColorTemplate.COLORFUL_COLORS[1])
        colors.add(ColorTemplate.COLORFUL_COLORS[2])
        colors.add(ColorTemplate.COLORFUL_COLORS[3])
        pieDataSet.setColors(Color.parseColor("#9f86c0"))

        val pieData = PieData(pieDataSet)

        pieChart5.data = pieData

        pieData.setValueTextColor(Color.WHITE)
        pieData.setValueTextSize(14f)
    }
    private fun showSalesMonth(){

        pieChart6.visibility = View.VISIBLE
        pieChart3.visibility = View.GONE
        title3.visibility = View.GONE
        title6.visibility = View.VISIBLE

        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(PieEntry(90f, "GB"))

        val colors: ArrayList<Int> = ArrayList()
        val pieDataSet = PieDataSet(pieEntries, "データ")

        colors.add(ColorTemplate.COLORFUL_COLORS[0])
        colors.add(ColorTemplate.COLORFUL_COLORS[1])
        colors.add(ColorTemplate.COLORFUL_COLORS[2])
        colors.add(ColorTemplate.COLORFUL_COLORS[3])
        pieDataSet.setColors(Color.parseColor("#be95c4"))

        val pieData = PieData(pieDataSet)

        pieChart6.data = pieData

        pieData.setValueTextColor(Color.WHITE)
        pieData.setValueTextSize(14f)
    }


}