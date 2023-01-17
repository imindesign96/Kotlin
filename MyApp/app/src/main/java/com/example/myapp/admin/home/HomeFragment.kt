package com.example.myapp.admin.home


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapp.R
import com.example.myapp.SignInActivity
import com.example.myapp.UserInfo
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var signOutBtn: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var detailData1 : Button
    private lateinit var detailData2 : Button
    private lateinit var detailData3 : Button
    private lateinit var buy3GbBtn : Button
    private lateinit var buy6GbBtn : Button
    private lateinit var buy12GbBtn : Button



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val bundle = Bundle()

        val sharedViewModel = activity?.let { ViewModelProvider(it).get(SharedViewModel::class.java) }


        val radioDataOnlyBtn = view.findViewById<RadioButton>(R.id.DataOnlyBtn1)
        val radioDataAndCallBtn = view.findViewById<RadioButton>(R.id.DataAndCallBtn1)
        val radioDataOnlyBtn2 = view.findViewById<RadioButton>(R.id.DataOnlyBtn2)
        val radioDataAndCallBtn2 = view.findViewById<RadioButton>(R.id.DataAndCallBtn2)
        val radioDataOnlyBtn3 = view.findViewById<RadioButton>(R.id.DataOnlyBtn3)
        val radioDataAndCallBtn3 = view.findViewById<RadioButton>(R.id.DataAndCallBtn3)




        val tvOnlyData1 = view.findViewById<TextView>(R.id.tvPriceData1)
        val tvDataAndView1 = view.findViewById<TextView>(R.id.tvPriceDataAndCall1)
        val tvOnlyData2 = view.findViewById<TextView>(R.id.tvPriceData2)
        val tvDataAndView2 = view.findViewById<TextView>(R.id.tvPriceDataAndCall2)
        val tvOnlyData3 = view.findViewById<TextView>(R.id.tvPriceData3)
        val tvDataAndView3 = view.findViewById<TextView>(R.id.tvPriceDataAndCall3)

        detailData1 = view.findViewById(R.id.detailData1Btn)
        detailData2 = view.findViewById(R.id.detailData2Btn)
        detailData3 = view.findViewById(R.id.detailData3Btn)
        buy3GbBtn = view.findViewById(R.id.buy3GbBtn)
        buy6GbBtn = view.findViewById(R.id.buy6GbBtn)
        buy12GbBtn = view.findViewById(R.id.buy12GbBtn)

        signOutBtn = view.findViewById(R.id.logOut)
        auth = FirebaseAuth.getInstance()


        val activity = activity as AppCompatActivity?
        if (activity != null) {
            activity.supportActionBar!!.setDisplayUseLogoEnabled(true)
        }

        //sign Out
        signOutBtn.setOnClickListener {
            auth.signOut()
            val intent = Intent(activity, SignInActivity::class.java)
            startActivity(intent)
        }


        //Detail
        detailData1.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_fragmentDetailData)

        }
        detailData2.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_fragmentDetailData6gb)
        }
        detailData3.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_fragmentDetailData12gb)
        }
        buy3GbBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_fragmentHomeBuy)
        }
        buy6GbBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_fragmentHomeBuy)
        }
        buy12GbBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_fragmentHomeBuy)
        }


        val radioGroup = view.findViewById<RadioGroup>(R.id.radioBtnGr1)
        val radioGroup2 = view.findViewById<RadioGroup>(R.id.radioBtnGr2)
        val radioGroup3 = view.findViewById<RadioGroup>(R.id.radioBtnGr3)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton = view.findViewById<RadioButton>(checkedId)
            val selectedValue = selectedRadioButton.text
            sharedViewModel?.selected?.value = selectedValue as String?
        }
        radioGroup2.setOnCheckedChangeListener { _, checkedId2 ->
            val selectedRadioButton2 = view.findViewById<RadioButton>(checkedId2)
            val selectedValue2 = selectedRadioButton2.text
            sharedViewModel?.selected?.value = selectedValue2 as String?
        }

        radioGroup3.setOnCheckedChangeListener { _, checkedId3 ->
            val selectedRadioButton3 = view.findViewById<RadioButton>(checkedId3)
            val selectedValue3 = selectedRadioButton3.text
            sharedViewModel?.selected?.value = selectedValue3 as String?
        }
        sharedViewModel?.selected?.observe(viewLifecycleOwner, Observer { selected ->

            if (radioDataOnlyBtn.text == selected) {
                radioDataOnlyBtn.isChecked = true
                radioDataAndCallBtn.isChecked = false
                val selectedValue = tvOnlyData1.text
                tvOnlyData1.visibility = View.VISIBLE
                tvDataAndView1.visibility = View.GONE
                sharedViewModel?.data?.value = selectedValue as String?

            }
            if (radioDataAndCallBtn.text == selected) {
                radioDataAndCallBtn.isChecked = true
                val selectedValue = tvDataAndView1.text
                tvOnlyData1.visibility = View.GONE
                tvDataAndView1.visibility = View.VISIBLE
                sharedViewModel?.data?.value = selectedValue as String?
            }
            if (radioDataOnlyBtn2.text == selected) {
                radioDataOnlyBtn2.isChecked = true
                radioDataAndCallBtn2.isChecked = false
                val selectedValue = tvOnlyData2.text
                tvOnlyData2.visibility = View.VISIBLE
                tvDataAndView2.visibility = View.GONE
                sharedViewModel?.data?.value = selectedValue as String?

            }
            if (radioDataAndCallBtn2.text == selected) {
                radioDataOnlyBtn2.isChecked = false
                radioDataAndCallBtn2.isChecked = true
                val selectedValue = tvDataAndView2.text
                tvOnlyData2.visibility = View.GONE
                tvDataAndView2.visibility = View.VISIBLE
                sharedViewModel?.data?.value = selectedValue as String?
            }
             if (radioDataOnlyBtn3.text == selected) {
                radioDataOnlyBtn3.isChecked = true
                val selectedValue = tvOnlyData3.text
                tvOnlyData3.visibility = View.VISIBLE
                tvDataAndView3.visibility = View.GONE
                sharedViewModel?.data?.value = selectedValue as String?

            }
            if (radioDataAndCallBtn3.text == selected) {
                radioDataAndCallBtn3.isChecked = true
                val selectedValue = tvDataAndView3.text
                tvOnlyData3.visibility = View.GONE
                tvDataAndView3.visibility = View.VISIBLE
                sharedViewModel?.data?.value = selectedValue as String?
            }

        })

//        sharedViewModel?.selected?.observe(viewLifecycleOwner, Observer { selected ->
//

//        })
//
//        sharedViewModel?.selected?.observe(viewLifecycleOwner, Observer { selected ->
//

//        })




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


        // Get the bottom navigation view
        val bottomNav = activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationViewMain)
        // Hide the bottom navigation view
        bottomNav.visibility = View.VISIBLE

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("DataPackage")
        sharedViewModel?.data?.observe(viewLifecycleOwner, Observer { data ->
            myRef.setValue(data)
        })

        return view

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


}

