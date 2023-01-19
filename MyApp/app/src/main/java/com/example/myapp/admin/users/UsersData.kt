package com.example.myapp.admin.users
import android.os.Parcelable
import com.example.myapp.Users
import com.example.myapp.admin.total.SimData
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsersData(
    val fullName: String ? =null,
    val userName: String ? = null,
    val userAddress: String ? = null,
    val startDay: String? = null,
    var status: String ?= null,
    val role: Users ?=null,
    val email: String? = null,
    val simData: SimData? = null
) : Parcelable {
    constructor(userName: String, role: Users, email: String) : this(null, userName, null, null, null, role, email, null )
}