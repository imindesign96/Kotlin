package com.example.myapp.admin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore


class FragmentDetailData : Fragment(R.layout.fragment_detail_data) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = activity as AppCompatActivity?
        if (activity != null) {
            activity.supportActionBar!!.setDisplayUseLogoEnabled(false)
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_detail_data, container, false)
        val sharedViewModel = activity?.let { ViewModelProvider(it).get(SharedViewModel::class.java) }

        val buy3GbBtn = view.findViewById<Button>(R.id.buy3GbBtn1)
        val tvOnlyData1 = view.findViewById<TextView>(R.id.tvPriceData1)
        val tvDataAndView1 = view.findViewById<TextView>(R.id.tvPriceDataAndCall1)
        //save data btn
        val radioDataOnlyBtn = view.findViewById<RadioButton>(R.id.DataOnlyBtn)
        val radioDataAndCallBtn = view.findViewById<RadioButton>(R.id.DataAndCallBtn)
        val groupBtn = view.findViewById<RadioGroup>(R.id.FragGroupBtn)

        groupBtn.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton = view.findViewById<RadioButton>(checkedId)
            val selectedValue = selectedRadioButton.text
            sharedViewModel?.selected?.value = selectedValue as String?
        }

        sharedViewModel?.selected?.observe(viewLifecycleOwner, Observer { selected ->

            if (radioDataOnlyBtn.text == selected) {
                radioDataOnlyBtn.isChecked = true
                radioDataAndCallBtn.isChecked = false
                val selectedValue = tvOnlyData1.text
                tvOnlyData1.visibility = View.VISIBLE
                tvDataAndView1.visibility = View.GONE
                sharedViewModel?.data?.value = selectedValue as String?
            }
            if (radioDataAndCallBtn.text == selected) {
                radioDataAndCallBtn.isChecked = true
                radioDataOnlyBtn.isChecked = false
                val selectedValue = tvDataAndView1.text
                tvOnlyData1.visibility = View.GONE
                tvDataAndView1.visibility = View.VISIBLE
                sharedViewModel?.data?.value = selectedValue as String?
            }
        })


        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("DataPackage")
        sharedViewModel?.data?.observe(viewLifecycleOwner, Observer { data ->
            myRef.setValue(data)
        })


        buy3GbBtn.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentDetailData_to_fragmentHomeBuy)
        }



        hideBottomNav()


        return view
    }

    private fun hideBottomNav(){
        // Get the activity
        val activity = activity as AppCompatActivity?

        // Get the bottom navigation view
        val bottomNav = activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationViewMain)

        // Hide the bottom navigation view
        bottomNav.visibility = View.GONE
    }
}