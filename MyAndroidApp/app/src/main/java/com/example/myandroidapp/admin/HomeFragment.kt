package com.example.myandroidapp.admin


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myandroidapp.MainActivity
import com.example.myandroidapp.R
import com.example.myandroidapp.SignInActivity
import com.example.myandroidapp.UserInfo
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var signOutBtn: TextView
    private lateinit var auth: FirebaseAuth

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
                        if (bitMatrix.get(x, y)) BLACK else WHITE
                    )
                }
            }
            barcodeBitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }


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
                    pixels[offset + x] = if (bitMatrix.get(x, y)) BLACK else WHITE
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)


        signOutBtn = view.findViewById(R.id.logOut)
        auth = FirebaseAuth.getInstance()

        signOutBtn.setOnClickListener {
            auth.signOut()
            val intent = Intent(activity, SignInActivity::class.java)
            startActivity(intent)
        }


        val pieChart: PieChart = view.findViewById(R.id.chart)

        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(PieEntry(90f, "GB"))

        val colors: ArrayList<Int> = ArrayList()
        val pieDataSet = PieDataSet(pieEntries, "データ")

        colors.add(ColorTemplate.COLORFUL_COLORS[0])
        colors.add(ColorTemplate.COLORFUL_COLORS[1])
        colors.add(ColorTemplate.COLORFUL_COLORS[2])
        colors.add(ColorTemplate.COLORFUL_COLORS[3])
        pieDataSet.setColors(Color.parseColor("#2979FF"))

        val pieData = PieData(pieDataSet)

        pieChart.data = pieData

        pieData.setValueTextColor(WHITE)
        pieData.setValueTextSize(14f)


        val barcodeBitmap = createBarcode("1234567891234567891234567")

        val imageViewBarcode = view.findViewById<ImageView>(R.id.imageViewBarcode)
        imageViewBarcode.setImageBitmap(barcodeBitmap)

        val imageViewQRcode = view.findViewById<ImageView>(R.id.imageViewQRcode)
       val bitmapQR =  generateQRCode("1234567891234567891234567")
        imageViewQRcode.setImageBitmap(bitmapQR)

        val barcodeNumber = view.findViewById<TextView>(R.id.barcode_number)
        barcodeNumber.text = "1234 56789 1234 5678 9123 4567"

        val userInfo = view.findViewById<ImageView>(R.id.userInf)
        userInfo.setOnClickListener {
            startActivity(Intent(activity, UserInfo::class.java))

        }



        return view

    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null) {
            startActivity(Intent(activity, MainActivity::class.java))
        }
    }

}

