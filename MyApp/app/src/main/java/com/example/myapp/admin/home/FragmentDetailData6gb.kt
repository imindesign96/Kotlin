package com.example.myapp.admin.home

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.myapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class FragmentDetailData6gb : Fragment(R.layout.fragment_detail_data6gb) {



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigate(R.id.action_fragmentDetailData6gb_to_homeFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

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
        val view : View =  inflater.inflate(R.layout.fragment_detail_data6gb, container, false)

        val buy6GbBtn1 = view.findViewById<Button>(R.id.buy6GbBtn1)
        buy6GbBtn1.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentDetailData6gb_to_fragmentHomeBuy)
        }

        val radioDataOnlyBtn = view.findViewById<Button>(R.id.radioDataOnly)
        val radioDataAndCallBtn = view.findViewById<Button>(R.id.radioDataAndCall)

        radioDataOnlyBtn.setOnClickListener{
            view.findViewById<TextView>(R.id.tvPriceData1).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.tvPriceDataAndCall1).visibility = View.GONE
        }

        radioDataAndCallBtn.setOnClickListener{
            view.findViewById<TextView>(R.id.tvPriceData1).visibility = View.GONE
            view.findViewById<TextView>(R.id.tvPriceDataAndCall1).visibility = View.VISIBLE
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