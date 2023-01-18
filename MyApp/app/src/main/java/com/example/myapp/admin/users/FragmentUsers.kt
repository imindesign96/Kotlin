package com.example.myapp.admin.users

import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.databinding.FragmentUsersBinding
import com.google.firebase.database.*



class FragmentUsers : Fragment(R.layout.fragment_users) {

    private lateinit var dbRef: DatabaseReference
    private lateinit var usersArrayList: ArrayList<UsersData>
    private lateinit var searchList: ArrayList<UsersData>
    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var adapter: UsersAdapter
    private lateinit var binding: FragmentUsersBinding
    private lateinit var today: Calendar
    private lateinit var current : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(inflater, container, false)

        usersRecyclerView = binding.userList
        usersRecyclerView.layoutManager = LinearLayoutManager(this.context)
        usersRecyclerView.setHasFixedSize(true)
        usersArrayList = mutableListOf<UsersData>() as ArrayList<UsersData>
        searchList = mutableListOf<UsersData>() as ArrayList<UsersData>
        adapter = UsersAdapter(usersArrayList)
        usersRecyclerView.adapter = adapter



        binding.notifyBtn.setOnClickListener {
            binding.notify.visibility =
                if (binding.notify.visibility == GONE) VISIBLE else GONE
            binding.blackBackground.visibility =
                if (binding.blackBackground.visibility == View.GONE) View.VISIBLE else View.GONE

        }

        dbRef = FirebaseDatabase.getInstance().getReference("User").child("UsersData")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                usersArrayList.clear()
                if (snapshot.exists()) {
                    for (childSnapshot in snapshot.children) {
                        val data = childSnapshot.getValue(UsersData::class.java)
                        if (data != null) {
                            usersArrayList.add(data)
                            usersArrayList.size
                            binding.G1Count.text =
                                usersArrayList.size.toString()
                            val unPaidCount = usersArrayList.count { it.status == "未支払い" }
                            binding.unPaidCount.text =
                                unPaidCount.toString()
                            val paidCount = usersArrayList.count { it.status == "支払完了" }
                            binding.paidCount.text = paidCount.toString()
                        }
                        val adapter = UsersAdapter(usersArrayList)
                        usersRecyclerView.adapter = adapter
                        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                return false
                            }

                            override fun onQueryTextChange(newText: String?): Boolean {
                                searchList.clear()
                                if (newText != null) {
                                    for (userInfo in usersArrayList) {
                                        if (userInfo.userName.toString().contains(newText) || userInfo.email.toString().contains(newText)) {
                                            searchList.add(userInfo)
                                        }
                                    }
                                    if (searchList.isEmpty()) {
                                        Toast.makeText(context, "No Data found", Toast.LENGTH_SHORT).show()
                                    } else {
                                        adapter.setFilteredList(searchList)
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

        val textNotify = binding.textNotify.editableText

        ///When lick to send a notify
        binding.sendNotify.setOnClickListener {
            // Create the notification
            val builder = NotificationCompat.Builder(it.context, "ID")
                .setSmallIcon(R.drawable.filter_icon)
                .setContentTitle("MyApp notification")
                .setContentText(textNotify.toString())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText( "$textNotify $current")
                )

            with(NotificationManagerCompat.from(it.context)) {
                // notificationId is a unique int for each notification that you must define
                notify(0, builder.build())


                binding.notify.visibility =
                    if (binding.notify.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                binding.blackBackground.visibility =
                    if (binding.blackBackground.visibility == View.VISIBLE) View.GONE else View.VISIBLE

            }
        }
        binding.setDay.setOnClickListener {

            binding.datePicker1.visibility =
                if (binding.datePicker1.visibility == View.GONE) View.VISIBLE else View.GONE
        }
        val datePicker = binding.datePicker1

        today = Calendar.getInstance()
        val time = today.time
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        current = formatter.format(time)

        datePicker.init(
            today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month + 1
            val pickedDay = "You Selected: $day/$month/$year"
            Toast.makeText(context, pickedDay, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}