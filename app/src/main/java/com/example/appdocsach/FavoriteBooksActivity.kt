package com.example.appdocsach

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class FavoriteBooksActivity : AppCompatActivity() {

    lateinit var rcvFavoriteBooks: RecyclerView
    lateinit var adapterSach: SachAdapter
    var mangSachYeuThich: ArrayList<Sach> = ArrayList()
    var mangSachFull: ArrayList<Sach> = ArrayList()

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
        setContentView(R.layout.activity_favorite_books)

        initViews()
        setupCategories()
        loadData()
        setupBottomNavigation()
    }

    private fun initViews() {
        rcvFavoriteBooks = findViewById(R.id.rcvFavoriteBooks)
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
    }

    private fun setupCategories() {
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

        selectCategory(btnTatCa, "Tất cả")
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences("favorites", MODE_PRIVATE)
        val favoriteBooksSet = sharedPreferences.getStringSet("favorite_books", emptySet()) ?: emptySet()

        val allBooks = arrayListOf(
            Sach("Ông già và biển cả", "Ernest Hemingway", R.drawable.sach1,
                "Câu chuyện kể về cuộc chiến đấu không cân sức giữa ông già Santiago và con cá kiếm khổng lồ...", "Văn học"),
            Sach("Dế mèn phiêu lưu ký", "Tô Hoài", R.drawable.sach2,
                "Tác phẩm kể về cuộc phiêu lưu của chú Dế Mèn qua nhiều vùng đất, học được nhiều bài học đường đời...", "Thiếu nhi"),
            Sach("Yêu trên từng ngón tay", "Trần Trà My", R.drawable.sach3,
                "Những câu chuyện nhẹ nhàng về tình yêu, cuộc sống của một cô gái trẻ đầy nghị lực...", "Tâm lý học"),
            Sach("Sóc sợ sệt", "Milano Walt", R.drawable.sach4,
                "Chú sóc nhỏ luôn lo lắng về mọi thứ xung quanh, nhưng rồi cậu học được cách dũng cảm đối mặt...", "Thiếu nhi"),
            Sach("Nơi nào có mẹ là nhà", "Hạ Mer", R.drawable.sach5,
                "Tuyển tập tản văn xúc động về tình mẫu tử, về những bữa cơm nhà và sự bình yên bên mẹ...", "Tâm lý học"),
            Sach("Tắt đèn", "Ngô Tất Tố", R.drawable.sach6,
                "Bức tranh hiện thực về cuộc sống khốn cùng của người nông dân Việt Nam dưới chế độ thực dân phong kiến...", "Văn học")
        )

        mangSachFull.clear()
        for (book in allBooks) {
            if (favoriteBooksSet.contains(book.tenSach)) {
                mangSachFull.add(book)
            }
        }

        mangSachYeuThich.clear()
        mangSachYeuThich.addAll(mangSachFull)

        adapterSach = SachAdapter(mangSachYeuThich)
        rcvFavoriteBooks.adapter = adapterSach
        rcvFavoriteBooks.layoutManager = GridLayoutManager(this, 2)
    }

    private fun selectCategory(button: TextView, category: String) {
        selectedButton?.apply {
            setBackgroundResource(R.drawable.bg_category_unselected)
            setTextColor(ContextCompat.getColor(this@FavoriteBooksActivity, R.color.category_text_unselected))
            setTypeface(null, android.graphics.Typeface.NORMAL)
        }

        button.apply {
            setBackgroundResource(R.drawable.bg_category_selected)
            setTextColor(ContextCompat.getColor(this@FavoriteBooksActivity, android.R.color.white))
            setTypeface(null, android.graphics.Typeface.BOLD)
        }

        selectedButton = button

        mangSachYeuThich.clear()
        if (category == "Tất cả") {
            mangSachYeuThich.addAll(mangSachFull)
        } else {
            mangSachYeuThich.addAll(mangSachFull.filter { it.theLoai == category })
        }

        if (::adapterSach.isInitialized) {
            adapterSach.notifyDataSetChanged()
        }
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.nav_library
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_search -> {
                    startActivity(Intent(this, SearchActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_library -> true
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}
