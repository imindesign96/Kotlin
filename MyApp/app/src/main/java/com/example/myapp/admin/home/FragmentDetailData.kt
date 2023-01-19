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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapp.FirebaseConnection
import com.example.myapp.R
import com.example.myapp.admin.total.Data
import com.example.myapp.admin.total.DataService
import com.example.myapp.admin.total.SimService
import com.example.myapp.admin.users.UsersViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore


class FragmentDetailData : Fragment(R.layout.fragment_detail_data) {

    private val firebaseConnection : FirebaseConnection by activityViewModels()
    private val userViewModel : UsersViewModel by activityViewModels()
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
                userViewModel.setService(DataService.DATA)
                radioDataOnlyBtn.isChecked = true
                radioDataAndCallBtn.isChecked = false
                val selectedValue = tvOnlyData1.text
                tvOnlyData1.visibility = View.VISIBLE
                tvDataAndView1.visibility = View.GONE
                sharedViewModel?.data?.value = selectedValue as String?
            }
            if (radioDataAndCallBtn.text == selected) {
                userViewModel.setService(DataService.CALL_DATA)
                radioDataAndCallBtn.isChecked = true
                radioDataOnlyBtn.isChecked = false
                val selectedValue = tvDataAndView1.text
                tvOnlyData1.visibility = View.GONE
                tvDataAndView1.visibility = View.VISIBLE
                sharedViewModel?.data?.value = selectedValue as String?
            }
        })





        buy3GbBtn.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentDetailData_to_fragmentHomeBuy)
            userViewModel.setData(Data.THREE)
            firebaseConnection.getRandomSim()
            getPrice()
        }



        hideBottomNav()


        return view
    }

    private fun getPrice(){
        val sharedViewModel = activity?.let { ViewModelProvider(it).get(SharedViewModel::class.java) }
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("DataPackage")
        sharedViewModel?.data?.observe(viewLifecycleOwner, Observer { data ->
            myRef.setValue(data)
        })
    }

    private fun hideBottomNav(){
        // Get the activity
        val activity = activity as AppCompatActivity?

        // Get the bottom navigation view
        val bottomNav = activity!!.findViewById<BottomNavigationView>(R.id.potentialBottomNav)
        val bottomNav2 = activity!!.findViewById<BottomNavigationView>(R.id.presentBottomNav)
        // Hide the bottom navigation view
        bottomNav.visibility = View.GONE
        bottomNav2.visibility = View.GONE
    }
}