package com.example.myapp.admin.users

import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.google.firebase.database.FirebaseDatabase



class UsersAdapter(private var usersList : ArrayList<UsersData>) : RecyclerView.Adapter<UsersAdapter.MyViewHolder> () {
    var database = FirebaseDatabase.getInstance()
    var databaseReference = database.getReference("User").child("Employees")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.users_item,
            parent, false
        )

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //Get data from firebase
        val currentItem = usersList[position]

        holder.usersName.text = currentItem.userName
        holder.simCode.text = currentItem.simCode
        holder.status.text = currentItem.status.toString()
        holder.startDay.text = currentItem.startDay
        val key = currentItem.key.toString()

        //Update information
//        holder.itemView.findViewById<Button>(R.id.UpdateSimDetail).setOnClickListener {
//
//
//            val phone = holder.itemView.findViewById<EditText>(R.id.tvPhoneNumber).text.toString().toLong()
//            val code = holder.itemView.findViewById<EditText>(R.id.tvCodeSim).text.toString()
//            val price = holder.itemView.findViewById<EditText>(R.id.tvPriceSim).text.toString().toLong()
//            val getKey = key.toLong()
//            val data = SimData(getKey,phone,code,price)
//
//            val bitmapQr = generateQRCode(code)
//            imageViewQRcode.setImageBitmap(bitmapQr)
//
//            databaseReference.child(key).setValue(data)
//
//        }

//        holder.itemView.findViewById<Button>(R.id.deleteSimDetail).setOnClickListener {
//            deleteRecord(key.toLong())
//        }
        }


//    searchView
    fun setFilteredList(usersList: ArrayList<UsersData>){
        this.usersList = usersList
    }
//
//    private fun deleteRecord(key: Long) {
//        val dbRef = FirebaseDatabase.getInstance().getReference("User").child(key.toString())
//        dbRef.removeValue()
//    }


    override fun getItemCount(): Int {

        return usersList.size
    }

    inner  class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val usersName : TextView = itemView.findViewById(R.id.tvUserName)
        val startDay : TextView= itemView.findViewById(R.id.tvUserStart)
        val simCode: TextView = itemView.findViewById(R.id.tvUserSimCode)
        val status : Button = itemView.findViewById(R.id.tvUserStatus)

    }

}


