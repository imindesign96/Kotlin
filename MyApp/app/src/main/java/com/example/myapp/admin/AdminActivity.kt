package com.example.myapp.admin


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityAdminBinding


class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardView1?.setOnClickListener {
            // If the CardView is already expanded, set its visibility
            // to gone and change the expand less icon to expand more.
            if (binding.hiddenView.visibility === View.GONE) {
                binding.cardView2.visibility = View.GONE
                binding.hiddenView.visibility = View.VISIBLE
            }

        }
        //click anywhere searchView
        binding.searchViewInventory.setOnClickListener(View.OnClickListener {
            binding.searchViewInventory.setIconified(
                false
            )
        })


    }
}