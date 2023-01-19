package com.example.myapp.admin.total

import android.os.Parcelable
import com.example.myapp.Users
import com.example.myapp.admin.users.UsersData
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimData(
    val key: Long? = null,
    val phoneNumber: Long? = null,
    val simCode:String? = null,
    val simPrice: Long? = null,
    val status: String? = null,
):Parcelable