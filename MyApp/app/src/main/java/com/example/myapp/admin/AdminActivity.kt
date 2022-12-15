package com.example.myapp.admin


import android.app.ProgressDialog.show
import android.content.ClipData.Item
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.example.myapp.databinding.ActivityAdminBinding


class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(com.example.myapp.R.id.frame1, fragment)
            commit()
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

       replaceFragment(FragmentInventory())
       binding.bottomNavigationView.setOnItemSelectedListener {
           when(it.itemId){
               com.example.myapp.R.id.navi_smile -> replaceFragment(FragmentInventory())
               com.example.myapp.R.id.navi_pets -> replaceFragment(FragmentSales())
               com.example.myapp.R.id.navi_sun -> replaceFragment(FragmentUsers())
               else -> {

               }
           }
           true
       }



//        binding.cardView1?.setOnClickListener {
//            // If the CardView is already expanded, set its visibility
//            // to gone and change the expand less icon to expand more.
//            if (binding.hiddenView.visibility === View.GONE) {
//                binding.cardView2.visibility = View.GONE
//                binding.hiddenView.visibility = View.VISIBLE
//            }
//
//        }
//
//        binding.outlinedButton?.setOnClickListener {
//            // If the CardView is already expanded, set its visibility
//            // to gone and change the expand less icon to expand more.
//            if (binding.hiddenView.visibility === View.VISIBLE) {
//                binding.cardView2.visibility = View.VISIBLE
//                binding.hiddenView.visibility = View.GONE
//            }
//
//        }
//
//
//
//        //click anywhere searchView
//        binding.searchViewInventory.setOnClickListener(View.OnClickListener {
//            binding.searchViewInventory.setIconified(
//                false
//            )
//        })


    }




}