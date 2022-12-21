package com.example.myapp.admin.total

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.google.firebase.database.*



class SimActivity : AppCompatActivity() {

    private lateinit var dbRef : DatabaseReference
    private lateinit var simRecyclerView : RecyclerView
    private lateinit var simArrayList : ArrayList<SimData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sim)

        simRecyclerView = findViewById(R.id.userList)
        simRecyclerView.layoutManager = LinearLayoutManager(this)
        simRecyclerView.setHasFixedSize(true)

        simArrayList = mutableListOf<SimData>() as ArrayList<SimData>


        dbRef = FirebaseDatabase.getInstance().getReference("User")
        //XU ly khi click nut save


        dbRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (childSnapshot in snapshot.children) {
                    val data = childSnapshot.getValue(SimData::class.java)
                    if (data != null) {
                        simArrayList.add(data)
                    }
                }
                val adapter = SimAdapter(simArrayList as ArrayList<SimData>)
                simRecyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

    }


}