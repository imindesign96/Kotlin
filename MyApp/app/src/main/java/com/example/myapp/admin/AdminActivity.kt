package com.example.myapp.admin


import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager

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
            if (binding.hiddenView!!.visibility === View.GONE) {
                binding.cardView2!!.visibility = View.GONE
                binding.hiddenView!!.visibility = View.VISIBLE
            }

        }
        binding.hiddenView.setOnClickListener {
            if (binding.hiddenView!!.visibility === View.VISIBLE) {
                binding.cardView2!!.visibility = View.VISIBLE
                binding.hiddenView!!.visibility = View.GONE
            }
        }


    }
}