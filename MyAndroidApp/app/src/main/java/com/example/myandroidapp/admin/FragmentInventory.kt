package com.example.myandroidapp.admin


import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class FragmentInventory : Fragment(com.example.myandroidapp.R.layout.fragment_inventory) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(com.example.myandroidapp.R.layout.fragment_inventory, container, false)


        return view
        }


        }

