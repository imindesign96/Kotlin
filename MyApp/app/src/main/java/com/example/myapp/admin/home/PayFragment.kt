package com.example.myapp.admin.home

import android.graphics.Bitmap
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapp.FirebaseConnection
import com.example.myapp.R
import com.example.myapp.Users
import com.example.myapp.admin.users.UsersData
import com.example.myapp.admin.users.UsersViewModel
import com.example.myapp.databinding.FragmentPayBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import java.util.*


class PayFragment : Fragment(R.layout.fragment_pay) {

    private val firebaseConnection: FirebaseConnection by activityViewModels()
    private val userViewModel : UsersViewModel by activityViewModels()
    private lateinit var dbRef: DatabaseReference
    private var user = MutableLiveData<UsersData>()
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var simPrice: String
    private lateinit var binding: FragmentPayBinding
    private lateinit var saveEmail: String
    private lateinit var email: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPayBinding.inflate(inflater, container, false)
        val view: View = binding.root

        val cancelBtn = view.findViewById<Button>(R.id.cancelBtn)
        val payBtn = view.findViewById<Button>(R.id.payBtn)
        val backToHomeBtn = view.findViewById<Button>(R.id.backToHomeBtn)
        val kindaku = view.findViewById<TextView>(R.id.kingaku)

        firebaseAuth = FirebaseAuth.getInstance()
        email = firebaseAuth.currentUser?.email.toString()

        saveEmail = email?.replace(".", ",").toString()

        dbRef = FirebaseDatabase.getInstance().getReference("User").child("UsersData")

        userViewModel.totalPrice.observe(viewLifecycleOwner) {
            kindaku.setText(it.toString()+ "JPY")
        }

        // create barcode
        val code = "${kindaku.text} + "
        val barcodeBitmap = email?.let { createBarcode(it) }

        val imageViewBarcode = view.findViewById<ImageView>(R.id.imageViewBarcode)
        imageViewBarcode.setImageBitmap(barcodeBitmap)

        val imageViewQRcode = view.findViewById<ImageView>(R.id.imageViewQRcode)
        val bitmapQR = email?.let { generateQRCode(it) }
        imageViewQRcode.setImageBitmap(bitmapQR)

        val barcodeNumber = view.findViewById<TextView>(R.id.barcode_number)
        barcodeNumber.text = "1234 56789 1234 5678 9123 4567"


        //setdealine

        val currentCalendar = Calendar.getInstance()
        val deadlineCalendar = Calendar.getInstance()
        deadlineCalendar.add(Calendar.DATE, 3)
        val currentDate = currentCalendar.time
        val deadline = deadlineCalendar.time

        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN)
        val formattedDate = dateFormat.format(deadline)

        view.findViewById<TextView>(R.id.deadline).text = formattedDate.toString()
       val name = UsersData().fullName
        val address = UsersData().userAddress

        cancelBtn.setOnClickListener {
            userViewModel.cancel()
            userViewModel.simInfo.observe(viewLifecycleOwner) {
                firebaseConnection.cancel(it)
            }
            findNavController().navigate(R.id.action_payFragment_to_homeFragment)

        }

        backToHomeBtn.setOnClickListener {
            findNavController().navigate(R.id.action_payFragment_to_confirmationFragment)
        }

        payBtn.setOnClickListener {
            userViewModel.userInfo.observe(viewLifecycleOwner) {usersData->
                usersData.email?.let { it1 -> firebaseConnection.upgradeUser(it1) }
                userViewModel.chosenSimService.observe(viewLifecycleOwner) {
                    firebaseConnection.setSim(usersData.email!!, it)
                }
            }
            findNavController().navigate(R.id.action_payFragment_to_homeFragment)
        }

        val activity = activity as AppCompatActivity?

        // Get the bottom navigation view
        val bottomNav = activity!!.findViewById<BottomNavigationView>(R.id.potentialBottomNav)
        val bottomNav2 = activity!!.findViewById<BottomNavigationView>(R.id.presentBottomNav)
        // Hide the bottom navigation view
        bottomNav.visibility = View.GONE
        bottomNav2.visibility = View.GONE

        return view

    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() === android.R.id.home) {

            return true
        }
        return super.onOptionsItemSelected(item)
    }





    //create barcode
    private fun createBarcode(
        contents: String
    ): Bitmap? {
        val writer = MultiFormatWriter()
        return try {
            val bitMatrix: BitMatrix = writer.encode(contents, BarcodeFormat.CODE_128, 1000, 190)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val barcodeBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    barcodeBitmap.setPixel(
                        x,
                        y,
                        if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE
                    )
                }
            }
            barcodeBitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }


    //Update lai data khi cancel


    // crate qr code
    private fun generateQRCode(data: String): Bitmap? {
        val qrCodeWriter = QRCodeWriter()
        try {
            val bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 300, 300)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val pixels = IntArray(width * height)
            for (y in 0 until height) {
                val offset = y * width
                for (x in 0 until width) {
                    pixels[offset + x] = if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE
                }
            }
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
            return bitmap
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


}