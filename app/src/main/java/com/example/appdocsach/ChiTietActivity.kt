package com.example.appdocsach

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton

class ChiTietActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private var currentPlayingPartIndex = -1
    private lateinit var layoutMucLuc: LinearLayout
    private lateinit var tvSoPhan: TextView

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
        layoutMucLuc = findViewById(R.id.layoutMụcLuc)
        tvSoPhan = findViewById(R.id.tvSoPhan)

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

        // Tạo mục lục động dựa trên danh sách audio
        setupMucLuc(danhSachAudio, btnNgheAudio)

        btnNgheAudio.setOnClickListener {
            if (danhSachAudio.isNotEmpty()) {
                if (currentPlayingPartIndex == -1) {
                    playAudioAtIndex(0, danhSachAudio, btnNgheAudio)
                } else {
                    togglePlayPause(btnNgheAudio)
                }
            } else {
                Toast.makeText(this, "Không có audio cho sách này", Toast.LENGTH_SHORT).show()
            }
        }

        btnDocSach.setOnClickListener {
            val intent = Intent(this, DocSachActivity::class.java)
            intent.putExtra("gui_ten_truyen", ten)
            startActivity(intent)
        }

        btnYeuThich.setOnClickListener {
            val sharedPreferences = getSharedPreferences("favorites", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val favoritesSet = sharedPreferences.getStringSet("favorite_books", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
            favoritesSet.add(ten)
            editor.putStringSet("favorite_books", favoritesSet)
            editor.apply()
            Toast.makeText(this, "Đã thêm \"$ten\" vào sách yêu thích", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupMucLuc(danhSachAudio: ArrayList<Int>, mainBtnAudio: MaterialButton) {
        layoutMucLuc.removeAllViews()
        tvSoPhan.text = "${danhSachAudio.size} phần"

        for (i in danhSachAudio.indices) {
            val itemView = LayoutInflater.from(this).inflate(R.layout.item_chapter, layoutMucLuc, false)
            val tvTitle = itemView.findViewById<TextView>(R.id.tvChapterTitle)
            val tvDuration = itemView.findViewById<TextView>(R.id.tvChapterDuration)

            tvTitle.text = "Phần ${i + 1}"
            tvDuration.text = "Audio - Sẵn sàng phát"

            itemView.setOnClickListener {
                playAudioAtIndex(i, danhSachAudio, mainBtnAudio)
            }

            layoutMucLuc.addView(itemView)
        }
    }

    private fun playAudioAtIndex(index: Int, danhSachAudio: ArrayList<Int>, mainBtnAudio: MaterialButton) {
        try {
            if (currentPlayingPartIndex == index && mediaPlayer != null) {
                togglePlayPause(mainBtnAudio)
                return
            }

            // Giải phóng audio cũ
            mediaPlayer?.release()
            
            // Phát audio mới
            mediaPlayer = MediaPlayer.create(this, danhSachAudio[index])?.apply {
                start()
                this@ChiTietActivity.isPlaying = true
                this@ChiTietActivity.currentPlayingPartIndex = index
                mainBtnAudio.text = "Đang phát Phần ${index + 1}"
                
                // Cập nhật giao diện mục lục
                updateMucLucUI(index)

                setOnCompletionListener {
                    this@ChiTietActivity.isPlaying = false
                    mainBtnAudio.text = "Nghe tiếp"
                    updateMucLucUI(-1)
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Lỗi phát audio: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun togglePlayPause(mainBtnAudio: MaterialButton) {
        if (mediaPlayer == null) return

        if (isPlaying) {
            mediaPlayer?.pause()
            isPlaying = false
            mainBtnAudio.text = "Tiếp tục Phần ${currentPlayingPartIndex + 1}"
        } else {
            mediaPlayer?.start()
            isPlaying = true
            mainBtnAudio.text = "Đang phát Phần ${currentPlayingPartIndex + 1}"
        }
        updateMucLucUI(currentPlayingPartIndex)
    }

    private fun updateMucLucUI(activeIndex: Int) {
        for (i in 0 until layoutMucLuc.childCount) {
            val itemView = layoutMucLuc.getChildAt(i)
            val tvTitle = itemView.findViewById<TextView>(R.id.tvChapterTitle)
            val imgPlay = itemView.findViewById<ImageView>(R.id.imgChapterPlay)

            if (i == activeIndex) {
                tvTitle.setTextColor(ContextCompat.getColor(this, R.color.bottom_nav_icon_color_selector))
                imgPlay.setImageResource(if (isPlaying) android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play)
            } else {
                tvTitle.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))
                imgPlay.setImageResource(android.R.drawable.ic_media_play)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}