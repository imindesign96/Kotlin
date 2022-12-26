package com.example.myapp.admin


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapp.R
import com.example.myapp.admin.users.FragmentUsers
import com.example.myapp.databinding.ActivityAdminBinding


class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.frame1, fragment)
            commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)



       replaceFragment(FragmentUsers())
       binding.bottomNavigationView.setOnItemSelectedListener {
           when(it.itemId){
               R.id.navi_home -> replaceFragment(HomeFragment())
               R.id.navi_smile -> replaceFragment(FragmentInventory())
               R.id.navi_pets -> replaceFragment(FragmentSales())
               R.id.navi_sun -> replaceFragment(FragmentUsers())
               else -> {

               }
           }
           true
       }


    }




}