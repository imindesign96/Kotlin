package com.example.myapp.admin.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.google.firebase.database.*


class FragmentUsers : Fragment(R.layout.fragment_users) {

    private lateinit var dbRef : DatabaseReference
    private lateinit var usersArrayList : ArrayList<UsersData>
    private lateinit var usersRecyclerView : RecyclerView
    private lateinit var adapter: UsersAdapter


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



        view.findViewById<Button>(R.id.notifyBtn).setOnClickListener {
            view.findViewById<View>(R.id.notify).visibility = if(view.findViewById<View>(R.id.notify).visibility == View.GONE) View.VISIBLE else View.GONE
            view.findViewById<View>(R.id.blackBackground).visibility = if(view.findViewById<View>(R.id.blackBackground).visibility == View.GONE) View.VISIBLE else View.GONE

            }




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

        val textNotify = view.findViewById<TextView>(R.id.textNotify).editableText

        ///When lick to send a notify
        view.findViewById<Button>(R.id.sendNotify).setOnClickListener {
            // Create the notification
            val builder = NotificationCompat.Builder(it.context,"ID")
                .setSmallIcon(R.drawable.filter_icon)
                .setContentTitle("MyApp notification")
                .setContentText(textNotify.toString())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("Quy khach vui long dong tien cuoc thang nay, Neu khong dong se phat sinh them phi cuoc"))


            with(NotificationManagerCompat.from(it.context)) {
                // notificationId is a unique int for each notification that you must define
                notify(1, builder.build())

                view.findViewById<View>(R.id.notify).visibility = if(view.findViewById<View>(R.id.notify).visibility == View.VISIBLE) View.GONE else View.VISIBLE
                view.findViewById<View>(R.id.blackBackground).visibility = if(view.findViewById<View>(R.id.blackBackground).visibility == View.VISIBLE) View.GONE else View.VISIBLE

            }
        }
        return view
    }

//    private fun myWorkManager(){
//        val constraints = Constraints.Builder()
//            .setRequiresCharging(false)
//            .setRequiredNetworkType((NetworkType.NOT_REQUIRED))
//            .setRequiresCharging(false)
//            .setRequiresBatteryNotLow(true)
//            .build()
//
//        val myRequest = PeriodicWorkRequest.Builder(
//            MyWorker::class.java,
//            15,
//            TimeUnit.MINUTES
//        ).setConstraints(constraints).build()
//
//        WorkManager.getInstance(requireContext())
//            .enqueueUniquePeriodicWork(
//                "ID",
//                ExistingPeriodicWorkPolicy.KEEP,
//                myRequest
//            )
//
//    }

    }