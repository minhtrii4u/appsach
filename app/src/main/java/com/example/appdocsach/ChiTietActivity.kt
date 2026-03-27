package com.example.appdocsach

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChiTietActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private var currentPlayingPartIndex = -1
    private lateinit var layoutMucLuc: LinearLayout
    private lateinit var tvSoPhan: TextView
    private lateinit var btnYeuThich: MaterialButton
    private lateinit var tvTheLoaiChiTiet: TextView
    
    private lateinit var tvDiemRating: TextView
    private lateinit var tvLuotRating: TextView
    private lateinit var ratingBarUser: RatingBar
    
    private var tenSach: String = ""
    private var isFavorite: Boolean = false
    
    private val database = FirebaseDatabase.getInstance("https://book-a8796-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

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
        btnYeuThich = findViewById(R.id.btnYeuThich)
        layoutMucLuc = findViewById(R.id.layoutMụcLuc)
        tvSoPhan = findViewById(R.id.tvSoPhan)
        tvTheLoaiChiTiet = findViewById(R.id.tvTheLoaiChiTiet)
        
        tvDiemRating = findViewById(R.id.tvDiemRating)
        tvLuotRating = findViewById(R.id.tvLuotRating)
        ratingBarUser = findViewById(R.id.ratingBarUser)

        tenSach = intent.getStringExtra("gui_ten") ?: "Tên Sách"
        val tacGia = intent.getStringExtra("gui_tacgia") ?: "Tác giả"
        val hinh = intent.getIntExtra("gui_hinh", R.drawable.sach1)
        val tomTat = intent.getStringExtra("gui_tomtat") ?: "Nội dung tóm tắt sẽ hiện ở đây..."
        val theLoai = intent.getStringExtra("gui_theloai") ?: "Kinh điển"
        val danhSachAudio = intent.getIntegerArrayListExtra("gui_danh_sach_audio") ?: arrayListOf()
        
        val currentBook = BookManager.mangSachFull.find { it.tenSach == tenSach }
        val cacPhan = currentBook?.cacPhan ?: arrayListOf()
        val diemDG = currentBook?.diemDanhGia ?: 0.0
        val luotDG = currentBook?.luotDanhGia ?: 0

        tvTen.text = tenSach
        tvTacGia.text = tacGia
        tvTomTat.text = tomTat
        tvTheLoaiChiTiet.text = theLoai
        img.setImageResource(hinh)
        
        updateRatingUI(diemDG, luotDG)
        
        btnBack.setOnClickListener {
            finish()
        }

        checkFavoriteStatus()
        setupMucLuc(cacPhan, danhSachAudio, btnNgheAudio)

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
            intent.putExtra("gui_ten_truyen", tenSach)
            startActivity(intent)
        }

        btnYeuThich.setOnClickListener {
            toggleFavorite()
        }
        
        // Logic đánh giá
        val prefUser = getSharedPreferences("DuLieuTaiKhoan", MODE_PRIVATE)
        val currentUserId = prefUser.getString("user_id", "user1") ?: "guest"
        val ratingPref = getSharedPreferences("UserRatings", MODE_PRIVATE)
        
        var userOldRating = ratingPref.getFloat("${currentUserId}_${tenSach}_score", 0f)
        if (userOldRating > 0) {
            ratingBarUser.rating = userOldRating
        }

        ratingBarUser.setOnRatingBarChangeListener { _, rating, fromUser ->
            if (fromUser) {
                currentBook?.let { book ->
                    val oldLuot = book.luotDanhGia
                    val oldDiem = book.diemDanhGia
                    
                    if (userOldRating == 0f) {
                        book.luotDanhGia += 1
                        val totalScore = (oldDiem * oldLuot) + rating
                        book.diemDanhGia = Math.round((totalScore / book.luotDanhGia) * 10.0) / 10.0
                    } else {
                        val totalScore = (oldDiem * oldLuot) - userOldRating + rating
                        book.diemDanhGia = Math.round((totalScore / oldLuot) * 10.0) / 10.0
                    }
                    
                    userOldRating = rating
                    ratingPref.edit().putFloat("${currentUserId}_${tenSach}_score", rating).apply()
                    
                    updateRatingUI(book.diemDanhGia, book.luotDanhGia)
                    BookManager.updateBook(this, tenSach, book)
                    updateInteractionsInFirebase(currentUserId)
                    Toast.makeText(this, "Bạn đã cập nhật đánh giá: $rating sao", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateInteractionsInFirebase(userId: String) {
        val userRef = database.child("users").child(userId)
        userRef.child("interactions").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentInteractions = snapshot.getValue(Int::class.java) ?: 0
                userRef.child("interactions").setValue(currentInteractions + 1)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun updateRatingUI(diem: Double, luot: Int) {
        tvDiemRating.text = String.format("%.1f", diem)
        tvLuotRating.text = if (luot >= 1000) String.format("(%.1fk lượt đánh giá)", luot / 1000.0) else "($luot lượt đánh giá)"
    }

    private fun checkFavoriteStatus() {
        val sharedPreferences = getSharedPreferences("favorites", MODE_PRIVATE)
        val favoritesSet = sharedPreferences.getStringSet("favorite_books", mutableSetOf())
        isFavorite = favoritesSet?.contains(tenSach) == true
        updateFavoriteButtonUI()
    }

    private fun updateFavoriteButtonUI() {
        if (isFavorite) {
            btnYeuThich.setIconTintResource(R.color.pink)
            btnYeuThich.setTextColor(ContextCompat.getColor(this, R.color.pink))
            btnYeuThich.text = "Đã thích"
        } else {
            btnYeuThich.setIconTint(ContextCompat.getColorStateList(this, android.R.color.darker_gray))
            btnYeuThich.setTextColor(ContextCompat.getColor(this, android.R.color.white))
            btnYeuThich.text = "Yêu thích"
        }
    }

    private fun toggleFavorite() {
        val sharedPreferences = getSharedPreferences("favorites", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val favoritesSet = sharedPreferences.getStringSet("favorite_books", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        
        val prefUser = getSharedPreferences("DuLieuTaiKhoan", MODE_PRIVATE)
        val userId = prefUser.getString("user_id", "user1") ?: "guest"

        if (!isFavorite) {
            favoritesSet.add(tenSach)
            isFavorite = true
            updateFavoriteCountInFirebase(1, userId)
            Toast.makeText(this, "Đã thêm \"$tenSach\" vào sách yêu thích", Toast.LENGTH_SHORT).show()
        } else {
            favoritesSet.remove(tenSach)
            isFavorite = false
            updateFavoriteCountInFirebase(-1, userId)
            Toast.makeText(this, "Đã xóa \"$tenSach\" khỏi sách yêu thích", Toast.LENGTH_SHORT).show()
        }
        
        editor.putStringSet("favorite_books", favoritesSet)
        editor.apply()
        updateFavoriteButtonUI()
    }

    private fun updateFavoriteCountInFirebase(delta: Int, userId: String) {
        val userRef = database.child("users").child(userId)
        userRef.child("favoriteCount").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentCount = snapshot.getValue(Int::class.java) ?: 0
                val newCount = if (currentCount + delta < 0) 0 else currentCount + delta
                userRef.child("favoriteCount").setValue(newCount)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
        
        if (delta > 0) {
            updateInteractionsInFirebase(userId)
        }
    }

    private fun setupMucLuc(parts: ArrayList<PhanSach>, audioList: ArrayList<Int>, mainBtnAudio: MaterialButton) {
        layoutMucLuc.removeAllViews()
        tvSoPhan.text = "${parts.size} phần"

        for (i in parts.indices) {
            val itemView = LayoutInflater.from(this).inflate(R.layout.item_chapter, layoutMucLuc, false)
            val tvIndex = itemView.findViewById<TextView>(R.id.tvChapterIndex)
            val tvTitle = itemView.findViewById<TextView>(R.id.tvChapterTitle)
            val layoutHeader = itemView.findViewById<View>(R.id.layoutChapterHeader)
            val layoutActions = itemView.findViewById<View>(R.id.layoutChapterActions)
            val imgArrow = itemView.findViewById<ImageView>(R.id.imgChapterArrow)
            
            val btnDocPhan = itemView.findViewById<MaterialButton>(R.id.btnDocPhan)
            val btnNgheAudioPhan = itemView.findViewById<MaterialButton>(R.id.btnNgheAudioPhan)

            tvIndex.text = String.format("%02d", i + 1)
            tvTitle.text = parts[i].tieuDe
            btnDocPhan.text = "Đọc phần ${i + 1}"

            layoutHeader.setOnClickListener {
                // Toggle hiện/ẩn phần nút bấm
                if (layoutActions.visibility == View.GONE) {
                    layoutActions.visibility = View.VISIBLE
                    imgArrow.rotation = 90f // Xoay mũi tên xuống
                } else {
                    layoutActions.visibility = View.GONE
                    imgArrow.rotation = 0f // Xoay mũi tên lại bình thường
                }
            }

            btnDocPhan.setOnClickListener {
                val intent = Intent(this, DocSachActivity::class.java)
                intent.putExtra("gui_ten_truyen", tenSach)
                intent.putExtra("gui_index_phan", i)
                startActivity(intent)
            }

            btnNgheAudioPhan.setOnClickListener {
                if (audioList.isNotEmpty() && i < audioList.size) {
                    playAudioAtIndex(i, audioList, mainBtnAudio)
                } else {
                    Toast.makeText(this, "Phần này chưa có Audio", Toast.LENGTH_SHORT).show()
                }
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

            mediaPlayer?.release()
            
            mediaPlayer = MediaPlayer.create(this, danhSachAudio[index])?.apply {
                start()
                this@ChiTietActivity.isPlaying = true
                this@ChiTietActivity.currentPlayingPartIndex = index
                mainBtnAudio.text = "Đang phát Phần ${index + 1}"
                
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
            val tvIndex = itemView.findViewById<TextView>(R.id.tvChapterIndex)
            val tvTitle = itemView.findViewById<TextView>(R.id.tvChapterTitle)

            if (i == activeIndex) {
                tvTitle.setTextColor(ContextCompat.getColor(this, R.color.pink))
                tvIndex.setTextColor(ContextCompat.getColor(this, R.color.pink))
            } else {
                tvTitle.setTextColor(ContextCompat.getColor(this, android.R.color.white))
                tvIndex.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
