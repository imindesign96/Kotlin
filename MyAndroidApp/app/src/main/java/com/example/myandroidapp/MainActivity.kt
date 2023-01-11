package com.example.myandroidapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myandroidapp.admin.FragmentSales
import com.example.myandroidapp.admin.HomeFragment
import com.example.myandroidapp.admin.total.FragmentSimTotal
import com.example.myandroidapp.admin.users.FragmentUsers
import com.example.myapp.R
import com.example.myapp.databinding.ActivityMainBinding

import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var signOutBtn: Button

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.frame1, fragment)
            commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        replaceFragment(HomeFragment())



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
