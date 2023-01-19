package com.example.myapp.admin.total

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimService(
    val sim: SimData?= null,
    val data: Data? = null,
    val service: DataService? = null,
    val totalPrice: Double? = null,
    val email: String?= null
): Parcelable
