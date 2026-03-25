package com.example.appdocsach

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ChiTietActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chi_tiet)


        val img: ImageView = findViewById(R.id.imgChiTiet)
        val tvTen: TextView = findViewById(R.id.tvTenChiTiet)
        val tvTacGia: TextView = findViewById(R.id.tvTacGiaChiTiet)
        val tvTomTat: TextView = findViewById(R.id.tvTomTat)
        val btnBack: LinearLayout = findViewById(R.id.btnBack)
        val btnDocSach: MaterialButton = findViewById(R.id.btnDocSach)
        val btnNgheAudio: MaterialButton = findViewById(R.id.btnNgheAudio)
        val btnYeuThich: MaterialButton = findViewById(R.id.btnYeuThich)

        val ten = intent.getStringExtra("gui_ten") ?: "Tên Sách"
        val tacGia = intent.getStringExtra("gui_tacgia") ?: "Tác giả"
        val hinh = intent.getIntExtra("gui_hinh", R.drawable.sach1)
        val tomTat = intent.getStringExtra("gui_tomtat") ?: "Nội dung tóm tắt sẽ hiện ở đây..."
        val danhSachAudio = intent.getIntegerArrayListExtra("gui_danh_sach_audio") ?: arrayListOf()

        tvTen.text = ten
        tvTacGia.text = tacGia
        tvTomTat.text = tomTat
        img.setImageResource(hinh)

        btnBack.setOnClickListener {
            finish()
        }

        btnNgheAudio.setOnClickListener {
            handleAudio(danhSachAudio, btnNgheAudio)
        }

        btnDocSach.setOnClickListener {
            val intent = Intent(this, DocSachActivity::class.java)
            intent.putExtra("gui_ten_truyen", ten)
            startActivity(intent)
        }

        btnYeuThich.setOnClickListener {
            // Lưu sách yêu thích vào SharedPreferences
            val sharedPreferences = getSharedPreferences("favorites", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            
            // Lấy danh sách yêu thích hiện tại
            val favoritesSet = sharedPreferences.getStringSet("favorite_books", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
            
            // Thêm sách hiện tại vào danh sách
            favoritesSet.add(ten)
            
            // Lưu lại
            editor.putStringSet("favorite_books", favoritesSet)
            editor.apply()
            
            Toast.makeText(this, "Đã thêm \"$ten\" vào sách yêu thích", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleAudio(danhSachAudio: ArrayList<Int>, button: MaterialButton) {
        try {
            if (mediaPlayer == null) {
                if (danhSachAudio.isNotEmpty()) {
                    // Phát audio đầu tiên từ danh sách
                    mediaPlayer = MediaPlayer.create(this, danhSachAudio[0])?.apply {
                        start()
                        this@ChiTietActivity.isPlaying = true
                        button.text = "Tạm dừng"
                    }
                } else {
                    Toast.makeText(this, "Không có audio cho sách này", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (isPlaying) {
                    mediaPlayer?.pause()
                    isPlaying = false
                    button.text = "Tiếp tục nghe"
                } else {
                    mediaPlayer?.start()
                    isPlaying = true
                    button.text = "Tạm dừng"
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Không thể phát Audio: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}