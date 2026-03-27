package com.example.appdocsach

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditBookActivity : AppCompatActivity() {

    private lateinit var edtTenSach: EditText
    private lateinit var edtTacGia: EditText
    private lateinit var edtGioiThieuTacGia: EditText
    private lateinit var layoutSectionsContainer: LinearLayout
    private lateinit var btnAddSection: Button
    private lateinit var btnSave: Button
    private lateinit var btnDelete: TextView
    private lateinit var btnCancel: TextView
    private lateinit var btnBack: ImageButton
    private lateinit var imgBook: ImageView
    private lateinit var tvTopTitle: TextView

    private var currentSach: Sach? = null
    private var isAddMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_book)

        initViews()

        isAddMode = intent.getBooleanExtra("is_add", false)
        if (!isAddMode) {
            currentSach = intent.getSerializableExtra("edit_sach") as? Sach
            currentSach?.let { fillData(it) }
        } else {
            setupAddMode()
        }

        setupListeners()
    }

    private fun initViews() {
        edtTenSach = findViewById(R.id.edtEditTenSach)
        edtTacGia = findViewById(R.id.edtEditTacGia)
        edtGioiThieuTacGia = findViewById(R.id.edtEditGioiThieuTacGia)
        layoutSectionsContainer = findViewById(R.id.layoutSectionsContainer)
        btnAddSection = findViewById(R.id.btnAddSection)
        btnSave = findViewById(R.id.btnSaveBook)
        btnDelete = findViewById(R.id.btnDeleteBook)
        btnCancel = findViewById(R.id.btnCancelEdit)
        btnBack = findViewById(R.id.btnBack)
        imgBook = findViewById(R.id.imgEditBook)
        tvTopTitle = findViewById(R.id.tvTopTitle)
    }

    private fun fillData(sach: Sach) {
        tvTopTitle.text = "Chỉnh sửa sách"
        edtTenSach.setText(sach.tenSach)
        edtTacGia.setText(sach.tacGia)
        edtGioiThieuTacGia.setText(sach.gioiThieuTacGia)
        imgBook.setImageResource(sach.hinhAnh)
        
        // Duyệt danh sách các phần
        sach.cacPhan.forEach { addSectionView(it.tieuDe, it.noiDung, it.audioResId) }

        if (sach.cacPhan.isEmpty()) {
            addSectionView("", "", 0)
        }
    }

    private fun setupAddMode() {
        tvTopTitle.text = "Thêm sách mới"
        btnDelete.visibility = View.GONE
        addSectionView("", "", 0)
    }

    private fun addSectionView(tieuDe: String, noiDung: String, audioResId: Int) {
        val view = LayoutInflater.from(this).inflate(R.layout.item_edit_section, layoutSectionsContainer, false)
        val edtTieuDe = view.findViewById<EditText>(R.id.edtSectionTitle)
        val edtNoiDung = view.findViewById<EditText>(R.id.edtSectionContent)
        val btnRemove = view.findViewById<ImageButton>(R.id.btnRemoveSection)
        val tvAudioName = view.findViewById<TextView>(R.id.tvAudioFileName)
        val btnSelectAudio = view.findViewById<Button>(R.id.btnSelectAudio)

        edtTieuDe.setText(tieuDe)
        edtNoiDung.setText(noiDung)
        
        // Gán tag để lưu audioResId hiện tại của view này
        view.tag = audioResId
        if (audioResId != 0) {
            tvAudioName.text = "Audio ID: $audioResId"
        }

        btnSelectAudio.setOnClickListener {
            // Logic chọn tệp âm thanh - Tạm thời giả lập bằng cách gán một ID ngẫu nhiên hoặc mở dialog
            // Ở đây tôi giả định bạn sẽ tích hợp trình chọn tệp thực tế sau
            val randomAudioId = (100..999).random() // Giả lập chọn tệp
            view.tag = randomAudioId
            tvAudioName.text = "Đã chọn: audio_$randomAudioId.mp3"
            Toast.makeText(this, "Đã chọn audio cho phần này", Toast.LENGTH_SHORT).show()
        }
        
        btnRemove.setOnClickListener {
            layoutSectionsContainer.removeView(view)
        }

        layoutSectionsContainer.addView(view)
    }

    private fun setupListeners() {
        btnBack.setOnClickListener { finish() }
        btnCancel.setOnClickListener { finish() }
        btnAddSection.setOnClickListener { addSectionView("", "", 0) }

        btnSave.setOnClickListener {
            saveBook()
        }

        btnDelete.setOnClickListener {
            currentSach?.let {
                BookManager.deleteBook(this, it.tenSach)
                Toast.makeText(this, "Đã xóa sách", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun saveBook() {
        val ten = edtTenSach.text.toString().trim()
        val tg = edtTacGia.text.toString().trim()
        val gttg = edtGioiThieuTacGia.text.toString().trim()

        if (ten.isEmpty() || tg.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ tên và tác giả", Toast.LENGTH_SHORT).show()
            return
        }

        val cacPhanMoi = ArrayList<PhanSach>()
        val danhSachAudioMoi = ArrayList<Int>()

        for (i in 0 until layoutSectionsContainer.childCount) {
            val view = layoutSectionsContainer.getChildAt(i)
            if (view != null) {
                val edtTieuDe = view.findViewById<EditText>(R.id.edtSectionTitle)
                val edtNoiDung = view.findViewById<EditText>(R.id.edtSectionContent)
                val audioResId = view.tag as? Int ?: 0
                
                val tieuDe = edtTieuDe.text.toString().trim()
                val noiDung = edtNoiDung.text.toString().trim()
                
                if (tieuDe.isNotEmpty() || noiDung.isNotEmpty()) {
                    cacPhanMoi.add(PhanSach(tieuDe, noiDung, audioResId))
                    if (audioResId != 0) {
                        danhSachAudioMoi.add(audioResId)
                    }
                }
            }
        }

        if (isAddMode) {
            val newBook = Sach(ten, tg, R.drawable.sach1, "", "Văn học")
            newBook.gioiThieuTacGia = gttg
            newBook.cacPhan = cacPhanMoi
            newBook.danhSachAudio = danhSachAudioMoi
            BookManager.addBook(this, newBook)
            Toast.makeText(this, "Thêm sách thành công", Toast.LENGTH_SHORT).show()
        } else {
            currentSach?.let {
                val updatedBook = Sach(
                    ten,
                    tg,
                    it.hinhAnh,
                    it.tomTat,
                    it.theLoai,
                    it.diemDanhGia,
                    it.luotDanhGia,
                    danhSachAudioMoi
                )
                updatedBook.gioiThieuTacGia = gttg
                updatedBook.cacPhan = cacPhanMoi
                BookManager.updateBook(this, it.tenSach, updatedBook)
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
            }
        }
        finish()
    }
}
