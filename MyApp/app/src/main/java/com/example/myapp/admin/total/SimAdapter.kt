package com.example.myapp.admin.total

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter



class SimAdapter(private var simList : ArrayList<SimData>) : RecyclerView.Adapter<SimAdapter.MyViewHolder> () {
    var database = FirebaseDatabase.getInstance()
    var databaseReference = database.getReference("User")

    //Create Qrcode
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.card_item,
            parent, false
        )



        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //Get data from firebase
        val currentItem = simList[position]

        holder.phoneNumber.text = currentItem.phoneNumber.toString()
        holder.simCode.text = currentItem.simCode
        holder.simPrice.text = currentItem.simPrice.toString()
        val key = currentItem.key.toString()
        val imageViewQRcode = holder.itemView.findViewById<ImageView>(R.id.imageViewQRcodeSim)
        val bitmapQR = currentItem.simCode?.let { it1 -> generateQRCode(it1) }
        imageViewQRcode.setImageBitmap(bitmapQR)


        imageViewQRcode.visibility = View.GONE

        holder.showQrCode.setOnClickListener {
            imageViewQRcode.visibility = if (imageViewQRcode.visibility == View.GONE) View.VISIBLE else View.GONE
            holder.showQrCode.visibility = if (imageViewQRcode.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            holder.hideQrCode.visibility = if (imageViewQRcode.visibility == View.VISIBLE)  View.VISIBLE else View.GONE
        }


        holder.hideQrCode.setOnClickListener {
            imageViewQRcode.visibility = if (imageViewQRcode.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            holder.showQrCode.visibility = if (imageViewQRcode.visibility == View.GONE) View.VISIBLE else View.GONE
            holder.hideQrCode.visibility = if (imageViewQRcode.visibility == View.VISIBLE) View.VISIBLE else View.GONE
        }


        //Update Sim
        holder.itemView.findViewById<Button>(R.id.UpdateSimDetail).setOnClickListener {


            val phone = holder.itemView.findViewById<EditText>(R.id.tvPhoneNumber).text.toString().toLong()
            val code = holder.itemView.findViewById<EditText>(R.id.tvCodeSim).text.toString()
            val price = holder.itemView.findViewById<EditText>(R.id.tvPriceSim).text.toString().toLong()
            val getKey = key.toLong()
            val data = SimData(getKey,phone,code,price)

            val bitmapQr = generateQRCode(code)
            imageViewQRcode.setImageBitmap(bitmapQr)

            databaseReference.child("SimData").child(key).setValue(data)

        }
        //Delete Sim
        holder.itemView.findViewById<Button>(R.id.deleteSimDetail).setOnClickListener {
            val builder = AlertDialog.Builder(it.context)
            builder.setTitle("確認")
            builder.setMessage("データを削除いたします。よろしいでしょうか。?")
            builder.setPositiveButton("Yes") { _, _ ->
                // Perform the action here
            }
            builder.setNegativeButton("No") { _, _ ->
                // Do nothing
            }
            val dialog = builder.create()
            dialog.show()
            deleteRecord(key.toLong())
        }
        }

    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(simList: ArrayList<SimData>){
        this.simList = simList
        notifyDataSetChanged()
    }

    //Delete function
    private fun deleteRecord(key: Long) {
        val dbRef = FirebaseDatabase.getInstance().getReference("User").child("SimData").child(key.toString())
        dbRef.removeValue()
    }


    override fun getItemCount(): Int {

        return simList.size
    }

    inner  class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val phoneNumber : TextView = itemView.findViewById(R.id.tvPhoneNumber)
        val simCode : TextView= itemView.findViewById(R.id.tvCodeSim)
        val simPrice: TextView = itemView.findViewById(R.id.tvPriceSim)
        val showQrCode : Button = itemView.findViewById(com.example.myapp.R.id.showQrCode)
        val hideQrCode : Button = itemView.findViewById(com.example.myapp.R.id.hideQrCode)


    }

}


