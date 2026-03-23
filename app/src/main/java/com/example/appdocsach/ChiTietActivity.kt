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


        val ten = intent.getStringExtra("gui_ten") ?: "Tên Sách"
        val tacGia = intent.getStringExtra("gui_tacgia") ?: "Tác giả"
        val hinh = intent.getIntExtra("gui_hinh", R.drawable.sach1)
        val tomTat = intent.getStringExtra("gui_tomtat") ?: "Nội dung tóm tắt sẽ hiện ở đây..."
        val audioUrl = intent.getStringExtra("gui_audio_url") ?: ""

        tvTen.text = ten
        tvTacGia.text = tacGia
        tvTomTat.text = tomTat
        img.setImageResource(hinh)

        btnBack.setOnClickListener {
            finish()
        }

        btnNgheAudio.setOnClickListener {
            handleAudio(audioUrl, btnNgheAudio)
        }

        btnDocSach.setOnClickListener {
            val intent = Intent(this, DocSachActivity::class.java)
            intent.putExtra("gui_ten_truyen", ten)
            startActivity(intent)
        }
    }

    private fun handleAudio(url: String, button: MaterialButton) {
        try {
            if (mediaPlayer == null) {
                if (url.isNotEmpty()) {
                    mediaPlayer = MediaPlayer().apply {
                        setDataSource(url)
                        prepareAsync()
                        setOnPreparedListener {
                            it.start()
                            this@ChiTietActivity.isPlaying = true
                            button.text = "Tạm dừng"
                        }
                    }
                } else {
                    mediaPlayer = MediaPlayer.create(this, R.raw.ong_gia_va_bien_ca_1)?.apply {
                        start()
                        this@ChiTietActivity.isPlaying = true
                        button.text = "Tạm dừng"
                    }
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