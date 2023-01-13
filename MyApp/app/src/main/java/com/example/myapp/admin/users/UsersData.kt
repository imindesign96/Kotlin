package com.example.myapp.admin.users
import android.os.Parcelable
import com.example.myapp.Users
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsersData(
    val key: Long? = null,
    val userName : String ? = null,
    val userAddress : String ? = null,
    val startDay: String? = null,
    val simCode:String? = null,
    var status: String? = null,
    val role: Users? = null,
   ) : Parcelable