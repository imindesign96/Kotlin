package com.example.myapp.admin.users

import android.content.ClipData
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.admin.total.SimData
import com.google.firebase.FirebaseOptions

import com.google.firebase.database.*


class FragmentUsers : Fragment(R.layout.fragment_users) {

    private lateinit var dbRef : DatabaseReference
    private lateinit var usersArrayList : ArrayList<UsersData>
    private lateinit var usersRecyclerView : RecyclerView
    private lateinit var adapter: UsersAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_users, container, false)

        usersRecyclerView = view.findViewById(R.id.userList)
        usersRecyclerView.layoutManager = LinearLayoutManager(this.context)
        usersRecyclerView.setHasFixedSize(true)
        usersArrayList = mutableListOf<UsersData>() as ArrayList<UsersData>
        adapter = UsersAdapter(usersArrayList)
        usersRecyclerView.adapter = adapter


//        view.findViewById<SearchView>(R.id.searchView).
//        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                val newQuery = FirebaseDatabase.getInstance().reference.child("UsersList")
//                    .orderByChild("userName")
//                    .startAt(newText)
//                    .endAt(newText + "\uf8ff")
//                if (newQuery != null) {
//                    val filteredList = ArrayList<SimData>()
//                    for (i in usersArrayList) {
//                        if (i.userName.toString().contains(newQuery) == true) {
//                            filteredList.add(i)
//                        }
//                    }
//                    if (filteredList.isEmpty()) {
//                        Toast.makeText(context, "No Data found", Toast.LENGTH_SHORT).show()
//                    } else {
//                        adapter.setFilteredList(filteredList)
//                    }
//                }
//                adapter.setFilteredList(newQuery)
//                return true
//            }
//
//
//        })

        dbRef = FirebaseDatabase.getInstance().getReference("User").child("UsersData")

        dbRef.addValueEventListener(object: ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) {
                usersArrayList.clear()
                if (snapshot.exists()) {
                    for (childSnapshot in snapshot.children) {
                        val data = childSnapshot.getValue(UsersData::class.java)
                        if (data != null) {
                            usersArrayList.add(data)
                            usersArrayList.size
                            view.findViewById<TextView>(R.id.G1Count).text = usersArrayList.size.toString()
                            val unPaidCount = usersArrayList.count { it.status == "未支払い" }
                            view.findViewById<TextView>(R.id.unPaidCount).text = unPaidCount.toString()
                            val paidCount = usersArrayList.count { it.status == "支払完了" }
                            view.findViewById<TextView>(R.id.paidCount).text = paidCount.toString()
                        }
                        val adapter = UsersAdapter(usersArrayList)
                        usersRecyclerView.adapter = adapter
                    }
                }

            }
            override fun onCancelled(error: DatabaseError) {
            }
        })







        return view
    }





}