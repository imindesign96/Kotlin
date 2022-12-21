package com.example.myapp.admin.total

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R

class SimAdapter(private val simList : ArrayList<SimData>) : RecyclerView.Adapter<SimAdapter.MyViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_item,
            parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = simList[position]

        holder.phoneNumber.text = currentItem.phoneNumber
        holder.simCode.text = currentItem.simCode
        holder.simPrice.text = currentItem.simPrice.toString()
    }

    override fun getItemCount(): Int {

        return simList.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val phoneNumber : TextView = itemView.findViewById(R.id.tvPhoneNumber)
        val simCode : TextView = itemView.findViewById(R.id.tvCodeSim)
        val simPrice : TextView = itemView.findViewById(R.id.tvPriceSim)


    }


}


