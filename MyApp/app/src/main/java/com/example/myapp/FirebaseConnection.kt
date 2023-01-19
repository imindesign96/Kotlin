package com.example.myapp

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.myapp.admin.total.SimAdapter
import com.example.myapp.admin.total.SimData
import com.example.myapp.admin.total.SimService
import com.example.myapp.admin.users.UsersData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FirebaseConnection : ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    val selectedSim = MutableLiveData<SimData>()
    val result = MutableLiveData<String>()
    val getSimResult = MutableLiveData<String>()
    val addSimResult = MutableLiveData<String>()
    val user = MutableLiveData<UsersData>()
    private lateinit var simData: SimData

    private val dbRef =
        FirebaseDatabase.getInstance("https://my-android-app-7f2c4-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("User")

    init {
        result.value = ""
        addSimResult.value = ""
        getSimResult.value = ""
    }


    fun login(email: String, pass: String) {
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                result.value = "Success"
            }
        }
    }

    fun register(email: String) {
        val savedEmail = email.replace(".", ",")
        dbRef.child("UsersData").child(savedEmail)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    user.value = snapshot.getValue(UsersData::class.java)!!
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    fun generateSimCode(phoneNumber: String, price: String) {
        dbRef.child("SimData").get().addOnSuccessListener {
            val childSnapshot = it.children.last()
            val data = childSnapshot.getValue(SimData::class.java)
            val key = (data!!.key!!.toLong() + 1).toString()
            var simCode = ""
            var phoneNumberExisted = false
            var alreadyExisted = true
            while (alreadyExisted) {
                alreadyExisted = false
                simCode = getRandomString(6)
                Log.d("ADDSIM", "Generate Sim Code$simCode")
                for (child in it.children) {
                    val data = child.getValue(SimData::class.java)
                    if (data != null) {
                        Log.d("ADDSIM", "Check data" + data.key)
                        if (simCode == data.simCode) {
                            alreadyExisted = true
                            break
                        }
                        if (phoneNumber == data.phoneNumber.toString()) {
                            phoneNumberExisted = true
                        }
                    }
                }
            }
            if (key.isNotEmpty() && !phoneNumberExisted) {
                simData = SimData(key.toLong(), phoneNumber.toLong(), simCode, price.toLong(), "未購入")
                dbRef.child("SimData").child(key).setValue(simData)
                addSimResult.value = "Success"
                Log.d("ADDSIM", "Add Sim done" + key)
            }

//            dbRef.child("SimData").get().addOnSuccessListener { it ->
//                val childSnapshot = it.children.last()
//                val data = childSnapshot.getValue(SimData::class.java)
//                val key = (data!!.key!!.toLong() + 1).toString()
//                var existed = false
//                for (childSnapshot in it.children) {
//                    val data = childSnapshot.getValue(SimData::class.java)
//                    if (data != null) {
//                        if (phoneNumber == data.phoneNumber.toString()) {
//                            Log.d("ADDSIM", "Check PhoneNumber${data.phoneNumber}")
//                            existed = true
//                            break
//                        }
//                    }
//                }
//                if (key.isNotEmpty() && existed) {
//                    simData = SimData(key.toLong(), phoneNumber.toLong(), simCode, price.toLong())
//                    dbRef.child("SimData").child(key).setValue(simData)
//                    Log.d("ADDSIM", "Add Sim done" + key)
//                }
//            }
        }
    }

    fun getRandomString(length: Int): String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }

    fun getRandomSim() {
        dbRef.child("SimData").get().addOnSuccessListener {
            for (child in it.children) {
                val data = child.getValue(SimData::class.java)!!
                if (data.status.isNullOrEmpty() || data.status == "未購入") {
                    selectedSim.value = data
                    updateSimStatus(data)
                    Log.d("BUYSIM"," SIM SELECTED SUCCESSFULLY${data.key}")
                    break
                }
            }
        }
    }

    fun updateSimStatus(selectedSimData: SimData) {
        val sim = SimData(selectedSimData.key, selectedSimData.phoneNumber, selectedSimData.simCode, selectedSimData.simPrice, "購入中")
        dbRef.child("SimData").child(selectedSimData.key.toString()).setValue(sim).addOnSuccessListener {
            Log.d("BUYSIM","UPDATE SIM STATUS SUCCESSFULLY${selectedSimData.key}")
        }
    }

    fun cancel(selectedSimData: SimData) {
        dbRef.child("SimData").child(selectedSimData.key.toString()).child("status").setValue("未購入").addOnSuccessListener {
            Log.d("BUYSIM","CANCEL SIM SUCCESSFULLY${selectedSimData.key}")
        }
    }

    fun upgradeUser(email : String) {
        val savedEmail = email.replace(".", ",")
        dbRef.child("UsersData").child(savedEmail).child("role").setValue(Users.PRESENT_USER).addOnSuccessListener {
            Log.d("BUYSIM","UPGRADE USER SUCCESSFULLY")
        }
    }

    fun setSim(email: String, simService: SimService) {
        val savedEmail = email.replace(".", ",")
        dbRef.child("SimService").child(savedEmail).setValue(simService).addOnSuccessListener {
            Log.d("BUYSIM","Set SIM SUCCESSFULLY${savedEmail}")
        }
    }
}
