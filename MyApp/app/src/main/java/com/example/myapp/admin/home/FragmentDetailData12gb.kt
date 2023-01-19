package com.example.myapp.admin.home

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapp.FirebaseConnection
import com.example.myapp.R
import com.example.myapp.admin.total.Data
import com.example.myapp.admin.total.DataService
import com.example.myapp.admin.users.UsersViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.FirebaseDatabase


class FragmentDetailData12gb : Fragment(R.layout.fragment_detail_data12gb) {

    private val userViewModel : UsersViewModel by activityViewModels()
    private val firebaseConnection : FirebaseConnection by activityViewModels()
    private lateinit var backBtn : Button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigate(R.id.action_fragmentDetailData12gb_to_homeFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val activity = activity as AppCompatActivity?
        if (activity != null) {
            activity.supportActionBar!!.setDisplayUseLogoEnabled(false)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View =  inflater.inflate(R.layout.fragment_detail_data12gb, container, false)

        val buy12GbBtn = view.findViewById<Button>(R.id.buy12GbBtn1)
        buy12GbBtn.setOnClickListener {
            getPrice()
            userViewModel.setData(Data.TWELVE)
            firebaseConnection.getRandomSim()
            findNavController().navigate(R.id.action_fragmentDetailData12gb_to_fragmentHomeBuy)
        }

        val radioDataOnlyBtn = view.findViewById<RadioButton>(R.id.radioDataOnly3)
        val radioDataAndCallBtn = view.findViewById<RadioButton>(R.id.radioDataAndCall3)
        val sharedViewModel = activity?.let { ViewModelProvider(it).get(SharedViewModel::class.java) }
        val tvData1 = view.findViewById<TextView>(R.id.tvPriceData1)
        val tvData2 = view.findViewById<TextView>(R.id.tvPriceDataAndCall1)

        val groupBtn = view.findViewById<RadioGroup>(R.id.radioBtnGroup3)
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

                val selectedValue = tvData1.text
                tvData1.visibility = View.VISIBLE
                tvData2.visibility = View.GONE
                sharedViewModel?.data?.value = selectedValue as String?
            }
            if (radioDataAndCallBtn.text == selected) {
                userViewModel.setService(DataService.CALL_DATA)
                radioDataAndCallBtn.isChecked = true
                radioDataOnlyBtn.isChecked = false

                val selectedValue = tvData2.text
                tvData1.visibility = View.GONE
                tvData2.visibility = View.VISIBLE
                sharedViewModel?.data?.value = selectedValue as String?
            }
        })


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