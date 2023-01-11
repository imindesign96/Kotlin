package com.example.myapp.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapp.R
import com.example.myapp.admin.home.HomeFragment
import com.example.myapp.admin.total.FragmentSimTotal
import com.example.myapp.admin.users.FragmentUsers
import com.example.myapp.databinding.ActivityAdminBinding
import com.google.firebase.messaging.FirebaseMessaging


class AdminActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAdminBinding
    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.frame1, fragment)
            commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = com.example.myapp.databinding.ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)


        FirebaseMessaging.getInstance().getToken()

        replaceFragment(FragmentUsers())



       binding.bottomNavigationView.setOnItemSelectedListener {
           when(it.itemId){
               R.id.navi_home -> replaceFragment(HomeFragment())
               R.id.navi_smile -> replaceFragment(FragmentSimTotal())
               R.id.navi_pets -> replaceFragment(FragmentSales())
               R.id.navi_sun -> replaceFragment(FragmentUsers())
               else -> {

               }
           }
           true
       }


    }




}