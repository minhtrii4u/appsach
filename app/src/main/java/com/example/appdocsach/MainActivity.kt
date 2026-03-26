package com.example.appdocsach

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var rcvSach: RecyclerView
    lateinit var adapterSach: SachAdapter
    var mangSach: ArrayList<Sach> = ArrayList()

    lateinit var btnTatCa: TextView
    lateinit var btnVanHoc: TextView
    lateinit var btnKyNang: TextView
    lateinit var btnKinhTe: TextView
    lateinit var btnThieuNhi: TextView
    lateinit var btnCongNgheThongTin: TextView
    lateinit var btnKhoaHoc: TextView
    lateinit var btnTamLyHoc: TextView
    lateinit var btnLichSu: TextView
    lateinit var btnTrietHoc: TextView
    lateinit var btnTruyenTranhManga: TextView
    lateinit var btnTieuThuyet: TextView
    lateinit var btnGiaoDucHocTap: TextView
    lateinit var btnSucKhoeDoiSong: TextView
    lateinit var btnMarketingKhoiNghiep: TextView
    lateinit var btnTaiChinhCaNhan: TextView
    lateinit var btnDuLich: TextView
    lateinit var btnTonGiaoTamLinh: TextView
    lateinit var btnNgoaiNgu: TextView

    var selectedButton: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BookManager.loadBooks(this)

        val tvXinChao: TextView = findViewById(R.id.tvXinChao)
        val tenUser = intent.getStringExtra("gui_ten_user")

        if (tenUser != null && tenUser.isNotEmpty()) {
            tvXinChao.text = "Xin chào, $tenUser "
        } else {
            // Lấy từ bộ nhớ nếu intent bị mất
            val pref = getSharedPreferences("DuLieuTaiKhoan", MODE_PRIVATE)
            val emailLuu = pref.getString("saved_email", "Khách")
            val ten = emailLuu?.substringBefore("@") ?: "Khách"
            tvXinChao.text = "Xin chào, $ten"
        }

        btnTatCa = findViewById(R.id.btnTatCa)
        btnVanHoc = findViewById(R.id.btnVanHoc)
        btnKyNang = findViewById(R.id.btnKyNang)
        btnKinhTe = findViewById(R.id.btnKinhTe)
        btnThieuNhi = findViewById(R.id.btnThieuNhi)
        btnCongNgheThongTin = findViewById(R.id.btnCongNgheThongTin)
        btnKhoaHoc = findViewById(R.id.btnKhoaHoc)
        btnTamLyHoc = findViewById(R.id.btnTamLyHoc)
        btnLichSu = findViewById(R.id.btnLichSu)
        btnTrietHoc = findViewById(R.id.btnTrietHoc)
        btnTruyenTranhManga = findViewById(R.id.btnTruyenTranhManga)
        btnTieuThuyet = findViewById(R.id.btnTieuThuyet)
        btnGiaoDucHocTap = findViewById(R.id.btnGiaoDucHocTap)
        btnSucKhoeDoiSong = findViewById(R.id.btnSucKhoeDoiSong)
        btnMarketingKhoiNghiep = findViewById(R.id.btnMarketingKhoiNghiep)
        btnTaiChinhCaNhan = findViewById(R.id.btnTaiChinhCaNhan)
        btnDuLich = findViewById(R.id.btnDuLich)
        btnTonGiaoTamLinh = findViewById(R.id.btnTonGiaoTamLinh)
        btnNgoaiNgu = findViewById(R.id.btnNgoaiNgu)

        btnTatCa.setOnClickListener { selectCategory(btnTatCa, "Tất cả") }
        btnVanHoc.setOnClickListener { selectCategory(btnVanHoc, "Văn học") }
        btnKyNang.setOnClickListener { selectCategory(btnKyNang, "Kỹ năng") }
        btnKinhTe.setOnClickListener { selectCategory(btnKinhTe, "Kinh tế") }
        btnThieuNhi.setOnClickListener { selectCategory(btnThieuNhi, "Thiếu nhi") }
        btnCongNgheThongTin.setOnClickListener { selectCategory(btnCongNgheThongTin, "Công nghệ thông tin") }
        btnKhoaHoc.setOnClickListener { selectCategory(btnKhoaHoc, "Khoa học") }
        btnTamLyHoc.setOnClickListener { selectCategory(btnTamLyHoc, "Tâm lý học") }
        btnLichSu.setOnClickListener { selectCategory(btnLichSu, "Lịch sử") }
        btnTrietHoc.setOnClickListener { selectCategory(btnTrietHoc, "Triết học") }
        btnTruyenTranhManga.setOnClickListener { selectCategory(btnTruyenTranhManga, "Truyện tranh / Manga") }
        btnTieuThuyet.setOnClickListener { selectCategory(btnTieuThuyet, "Tiểu thuyết") }
        btnGiaoDucHocTap.setOnClickListener { selectCategory(btnGiaoDucHocTap, "Giáo dục / Học tập") }
        btnSucKhoeDoiSong.setOnClickListener { selectCategory(btnSucKhoeDoiSong, "Sức khỏe & Đời sống") }
        btnMarketingKhoiNghiep.setOnClickListener { selectCategory(btnMarketingKhoiNghiep, "Marketing & Khởi nghiệp") }
        btnTaiChinhCaNhan.setOnClickListener { selectCategory(btnTaiChinhCaNhan, "Tài chính cá nhân") }
        btnDuLich.setOnClickListener { selectCategory(btnDuLich, "Du lịch") }
        btnTonGiaoTamLinh.setOnClickListener { selectCategory(btnTonGiaoTamLinh, "Tôn giáo / Tâm linh") }
        btnNgoaiNgu.setOnClickListener { selectCategory(btnNgoaiNgu, "Ngoại ngữ") }

        rcvSach = findViewById(R.id.rcvDanhSach)
        mangSach.addAll(BookManager.mangSachFull)

        adapterSach = SachAdapter(mangSach)
        rcvSach.adapter = adapterSach
        rcvSach.layoutManager = GridLayoutManager(this, 2)

        selectCategory(btnTatCa, "Tất cả")

        val bottomNavigationView = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.nav_home
        bottomNavigationView.setOnItemSelectedListener { item ->
            val currentTenUser = intent.getStringExtra("gui_ten_user") ?: getSharedPreferences("DuLieuTaiKhoan", MODE_PRIVATE).getString("saved_email", "Khách")?.substringBefore("@")
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_search -> {
                    val intent = Intent(this, SearchActivity::class.java)
                    intent.putExtra("mangSachFull", BookManager.mangSachFull)
                    intent.putExtra("gui_ten_user", currentTenUser)
                    startActivity(intent)
                    true
                }
                R.id.nav_library -> {
                    val intent = Intent(this, FavoriteBooksActivity::class.java)
                    intent.putExtra("gui_ten_user", currentTenUser)
                    startActivity(intent)
                    true
                }
                R.id.nav_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("gui_ten_user", currentTenUser)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        BookManager.loadBooks(this)
        selectCategory(selectedButton ?: btnTatCa, (selectedButton?.text ?: "Tất cả").toString())
    }

    private fun selectCategory(button: TextView, category: String) {
        selectedButton?.apply {
            setBackgroundResource(R.drawable.bg_category_unselected)
            setTextColor(ContextCompat.getColor(this@MainActivity, R.color.category_text_unselected))
            setTypeface(null, android.graphics.Typeface.NORMAL)
        }

        button.apply {
            setBackgroundResource(R.drawable.bg_category_selected)
            setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.white))
            setTypeface(null, android.graphics.Typeface.BOLD)
        }

        selectedButton = button

        mangSach.clear()
        if (category == "Tất cả") {
            mangSach.addAll(BookManager.mangSachFull)
        } else {
            mangSach.addAll(BookManager.mangSachFull.filter { it.theLoai == category })
        }

        if (::adapterSach.isInitialized) {
            adapterSach.notifyDataSetChanged()
        }
    }
}