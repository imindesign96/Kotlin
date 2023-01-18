package com.example.myapp.admin.users


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R


class UsersAdapter(private var usersList : ArrayList<UsersData>) : RecyclerView.Adapter<UsersAdapter.MyViewHolder> () {


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
        holder.startDay.text = currentItem.email
        holder.address.text = currentItem.userAddress

        holder.button.setOnClickListener {
            holder.avatarImage.visibility = if(holder.avatarImage.visibility == View.GONE) View.VISIBLE else View.GONE
            holder.rowAddress.visibility = if (holder.rowAddress.visibility == View.GONE) View.VISIBLE else View.GONE
            holder.rowPayInfo.visibility = if (holder.rowPayInfo.visibility == View.GONE) View.VISIBLE else View.GONE
            if(holder.rowPayInfo.visibility == View.VISIBLE)
                holder.button.text = "閉じる"
            else
                holder.button.text = "詳細情報"
        }

    }

        //    searchView
        fun setFilteredList(usersList: ArrayList<UsersData>) {
            this.usersList = usersList
            notifyDataSetChanged()
        }


        override fun getItemCount(): Int {

            return usersList.size
        }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val usersName: TextView = itemView.findViewById(R.id.tvUserName)
        val startDay: TextView = itemView.findViewById(R.id.tvUserStart)
        val simCode: TextView = itemView.findViewById(R.id.tvUserSimCode)
        val status: Button = itemView.findViewById(R.id.tvUserStatus)
        val address : TextView = itemView.findViewById(R.id.tvUserAddress)
        val button = itemView.findViewById<Button>(R.id.updateUserDetail)
        val rowAddress = itemView.findViewById<View>(R.id.rowAddress)
        val rowPayInfo = itemView.findViewById<View>(R.id.rowPayInfo)
        val avatarImage = itemView.findViewById<ImageView>(R.id.avatar_image_view)

    }


}

