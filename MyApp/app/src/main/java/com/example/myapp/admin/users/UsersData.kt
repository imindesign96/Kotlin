package com.example.myapp.admin.users
import android.os.Parcelable
import com.example.myapp.Users
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsersData(
    val fullName: String ? =null,
    val userName: String ? = null,
    val userAddress: String ? = null,
    val simPrice: String ? = null,
    val startDay: String? = null,
    val simCode:String? = null,
    var status: String ?= null,
    val role: Users ?=null,
    val email: String? = null,
    val phoneNumber: String? = null
) : Parcelable