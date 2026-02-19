package com.example.appdocsach

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ChiTietActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chi_tiet)

        val img: ImageView = findViewById(R.id.imgChiTiet)
        val tvTen: TextView = findViewById(R.id.tvTenChiTiet)
        val tvTacGia: TextView = findViewById(R.id.tvTacGiaChiTiet)
        val tvTomTat: TextView = findViewById(R.id.tvTomTat)
        val tvBack: TextView = findViewById(R.id.tvBack)
        val btnDocSach: TextView = findViewById(R.id.btnDocSach)
        val btnNgheAudio: TextView = findViewById(R.id.btnNgheAudio)
        val ten = intent.getStringExtra("gui_ten")
        val tacGia = intent.getStringExtra("gui_tacgia")
        val hinh = intent.getIntExtra("gui_hinh", 0)
        val tomTat = intent.getStringExtra("gui_tomtat")

        tvTen.text = ten
        tvTacGia.text = tacGia
        tvTomTat.text = tomTat
        img.setImageResource(hinh)

        tvBack.setOnClickListener {
            finish()
        }

        btnNgheAudio.setOnClickListener {
            Toast.makeText(this, "Tính năng đang phát triển!", Toast.LENGTH_SHORT).show()
        }

        btnDocSach.setOnClickListener {
            Toast.makeText(this, "Đang mở truyện: $ten", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, DocSachActivity::class.java)

            intent.putExtra("gui_ten_truyen", ten)

            startActivity(intent)
        }
    }
}