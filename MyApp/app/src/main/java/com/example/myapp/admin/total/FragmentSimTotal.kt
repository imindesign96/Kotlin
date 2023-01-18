package com.example.myapp.admin.total


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.databinding.ActivityAdminBinding
import com.example.myapp.databinding.FragmentSimTotalBinding
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


class FragmentSimTotal : Fragment(R.layout.fragment_sim_total) {

    private lateinit var binding: FragmentSimTotalBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var simArrayList: ArrayList<SimData>
    private lateinit var filteredList: ArrayList<SimData>
    private lateinit var simRecyclerView: RecyclerView
    private lateinit var adapter: SimAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSimTotalBinding.inflate(inflater, container, false)

        simRecyclerView = binding.simList
        simRecyclerView.layoutManager = LinearLayoutManager(this.context)
        simRecyclerView.setHasFixedSize(true)

        simArrayList = mutableListOf<SimData>() as ArrayList<SimData>
        filteredList = mutableListOf<SimData>() as ArrayList<SimData>
        binding.addSim.setOnClickListener {
            val action = FragmentSimTotalDirections.actionFragmentSimTotalToFragmentAddSim()
            findNavController().navigate(action)
        }

        adapter = SimAdapter(simArrayList)
        simRecyclerView.adapter = adapter

        dbRef = FirebaseDatabase.getInstance().getReference("User").child("SimData")

        dbRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                simArrayList.clear()
                if (snapshot.exists()) {
                    for (childSnapshot in snapshot.children) {
                        val data = childSnapshot.getValue(SimData::class.java)
                        if (data != null) {
                            simArrayList.add(data)
                        }
                        val adapter = SimAdapter(simArrayList)
                        simRecyclerView.adapter = adapter
                        binding.simSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                return false
                            }

                            override fun onQueryTextChange(newText: String?): Boolean {
                                filteredList.clear()
                                if (newText != null) {
                                    for (simInfo in simArrayList) {
                                        if (simInfo.phoneNumber.toString().contains(newText) || simInfo.simCode?.contains(newText) == true) {
                                            filteredList.add(simInfo)
                                        }
                                    }
                                    if (filteredList.isEmpty()) {
                                        Toast.makeText(context, "No Data found", Toast.LENGTH_SHORT).show()
                                    } else {
                                        adapter.setFilteredList(filteredList)
                                    }
                                }
                                return true
                            }

                        })
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return binding.root
    }
}