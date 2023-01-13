package com.example.myapp

import android.os.Bundle
import android.view.Menu
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapp.admin.FragmentSales
import com.example.myapp.admin.home.HomeFragment
import com.example.myapp.admin.total.FragmentSimTotal
import com.example.myapp.databinding.ActivityMainBinding
import com.github.mikephil.charting.utils.Utils.init
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var signOutBtn: Button
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        // Get the navigation host fragment from this Activity
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // Instantiate the navController using the NavHostFragment
        navController = navHostFragment.navController
        // Make sure actions in the ActionBar get propagated to the NavController
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.logomobile)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        setupActionBarWithNavController(navController)

        binding.bottomNavigationViewMain.setOnNavigationItemSelectedListener(){
            val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
            if (currentFragment is HomeFragment) {
                when(it.itemId){
                    R.id.navi_home -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_self)
                    R.id.navi_smile -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_fragmentSimTotal)
                    R.id.navi_pets -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_fragmentSales)
                    R.id.navi_sun -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_fragmentUsers)
                }
            } else if (currentFragment is FragmentSimTotal) {
                when(it.itemId){
                    R.id.navi_home -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentSimTotal_to_homeFragment)
                    R.id.navi_smile -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentSimTotal_self)
                    R.id.navi_pets -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentSimTotal_to_fragmentSales)
                    R.id.navi_sun -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentSimTotal_to_fragmentUsers)
                }
            } else if (currentFragment is FragmentSales) {
                when(it.itemId){
                    R.id.navi_home -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentSales_to_homeFragment)
                    R.id.navi_smile -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentSales_to_fragmentSimTotal)
                    R.id.navi_pets -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentSales_self)
                    R.id.navi_sun -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentSales_to_fragmentUsers)
                }
            } else {
                when(it.itemId){
                    R.id.navi_home -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentUsers_to_homeFragment)
                    R.id.navi_smile -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentUsers_to_fragmentSimTotal)
                    R.id.navi_pets -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentUsers_to_fragmentSales)
                    R.id.navi_sun -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentUsers_self)
                }
            }
            true
        }




    }


    /**
     * Enables back button support. Simply navigates one element up on the stack.
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_navigation_menu, menu)
        return true
    }

}