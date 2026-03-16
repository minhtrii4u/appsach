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
    var mangSachFull: ArrayList<Sach> = ArrayList()

    // Category buttons
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

        val tvXinChao: TextView = findViewById(R.id.tvXinChao)
        val tenUser = intent.getStringExtra("gui_ten_user")

        if (tenUser != null && tenUser.isNotEmpty()) {
            tvXinChao.text = "Xin chào, $tenUser "
        }

        // Initialize buttons
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

        // Set click listeners
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

        // Add books with categories
        mangSachFull.add(Sach("Ông già và biển cả", "Ernest Hemingway", R.drawable.sach1,
            "Câu chuyện kể về cuộc chiến đấu không cân sức giữa ông già Santiago và con cá kiếm khổng lồ...", "Văn học"))

        mangSachFull.add(Sach("Dế mèn phiêu lưu ký", "Tô Hoài", R.drawable.sach2,
            "Tác phẩm kể về cuộc phiêu lưu của chú Dế Mèn qua nhiều vùng đất, học được nhiều bài học đường đời...", "Thiếu nhi"))

        mangSachFull.add(Sach("Yêu trên từng ngón tay", "Trần Trà My", R.drawable.sach3,
            "Những câu chuyện nhẹ nhàng về tình yêu, cuộc sống của một cô gái trẻ đầy nghị lực...", "Tâm lý học"))

        mangSachFull.add(Sach("Sóc sợ sệt", "Milano Walt", R.drawable.sach4,
            "Chú sóc nhỏ luôn lo lắng về mọi thứ xung quanh, nhưng rồi cậu học được cách dũng cảm đối mặt...", "Thiếu nhi"))

        mangSachFull.add(Sach("Nơi nào có mẹ là nhà", "Hạ Mer", R.drawable.sach5,
            "Tuyển tập tản văn xúc động về tình mẫu tử, về những bữa cơm nhà và sự bình yên bên mẹ...", "Tâm lý học"))

        mangSachFull.add(Sach("Tắt đèn", "Ngô Tất Tố", R.drawable.sach6,
            "Bức tranh hiện thực về cuộc sống khốn cùng của người nông dân Việt Nam dưới chế độ thực dân phong kiến...", "Văn học"))

        // Initially show all
        mangSach.addAll(mangSachFull)

        adapterSach = SachAdapter(mangSach)
        rcvSach.adapter = adapterSach
        rcvSach.layoutManager = GridLayoutManager(this, 2)

        // Select "Tất cả" by default
        selectCategory(btnTatCa, "Tất cả")

        val bottomNavigationView = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.nav_home
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_search -> {
                    val intent = Intent(this, SearchActivity::class.java)
                    intent.putExtra("mangSachFull", mangSachFull)
                    startActivity(intent)
                    true
                }
                R.id.nav_library -> {
                    // TODO: Chuyển sang activity tủ sách
                    true
                }
                R.id.nav_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun selectCategory(button: TextView, category: String) {
        // Reset previous selected button
        selectedButton?.apply {
            setBackgroundResource(R.drawable.bg_category_unselected)
            setTextColor(ContextCompat.getColor(this@MainActivity, R.color.category_text_unselected))
            setTypeface(null, android.graphics.Typeface.NORMAL)
        }

        // Set new selected button
        button.apply {
            setBackgroundResource(R.drawable.bg_category_selected)
            setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.white))
            setTypeface(null, android.graphics.Typeface.BOLD)
        }

        selectedButton = button

        // Filter books
        mangSach.clear()
        if (category == "Tất cả") {
            mangSach.addAll(mangSachFull)
        } else {
            mangSach.addAll(mangSachFull.filter { it.theLoai == category })
        }

        adapterSach.notifyDataSetChanged()
    }
}