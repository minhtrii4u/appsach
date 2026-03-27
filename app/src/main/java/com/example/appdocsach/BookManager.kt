package com.example.appdocsach

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object BookManager {
    private const val PREFS_NAME = "BookPrefs"
    private const val KEY_BOOKS = "all_books"
    var mangSachFull: ArrayList<Sach> = ArrayList()
    
    private val database = FirebaseDatabase.getInstance("https://book-a8796-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
    private val booksRef = database.child("books_data")

    fun loadBooks(context: Context, callback: (() -> Unit)? = null) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(KEY_BOOKS, null)
        
        // Load tạm từ local trước
        if (json != null) {
            try {
                val type = object : TypeToken<ArrayList<Sach>>() {}.type
                mangSachFull = Gson().fromJson(json, type)
            } catch (e: Exception) { }
        }

        booksRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fireBooks = ArrayList<Sach>()
                if (snapshot.exists()) {
                    for (child in snapshot.children) {
                        child.getValue(Sach::class.java)?.let { fireBooks.add(it) }
                    }
                }

                // Nếu Firebase có ít hơn 6 cuốn sách mặc định -> Coi như dữ liệu bị thiếu và nạp lại sạch sẽ
                if (fireBooks.size < 6) {
                    mangSachFull = getSampleData()
                    pushAllToFirebase() 
                    saveBooksLocal(context)
                } else {
                    mangSachFull = fireBooks
                    saveBooksLocal(context)
                }
                callback?.invoke()
            }

            override fun onCancelled(error: DatabaseError) {
                if (mangSachFull.isEmpty()) {
                    mangSachFull = getSampleData()
                    saveBooksLocal(context)
                }
                callback?.invoke()
            }
        })
    }

    private fun getSampleData(): ArrayList<Sach> {
        val list = ArrayList<Sach>()

        // 1. Ông già và biển cả - sach1.jpg
        list.add(Sach("Ông già và biển cả", "Ernest Hemingway", R.drawable.sach1,
            "Cuộc chiến giữa con người và thiên nhiên hùng vĩ qua hình ảnh lão ngư Santiago.", "Kinh điển", 4.8, 1200).apply {
            cacPhan = arrayListOf(
                PhanSach("Phần 1", "Nội dung phần 1...", R.raw.ong_gia_va_bien_ca_1),
                PhanSach("Phần 2", "Nội dung phần 2...", R.raw.ong_gia_va_bien_ca_2),
                PhanSach("Phần 3", "Nội dung phần 3...", R.raw.ong_gia_va_bien_ca_3),
                PhanSach("Phần 4", "Nội dung phần 4...", R.raw.ong_gia_va_bien_ca_4)
            )
            danhSachAudio = arrayListOf(R.raw.ong_gia_va_bien_ca_1, R.raw.ong_gia_va_bien_ca_2, R.raw.ong_gia_va_bien_ca_3, R.raw.ong_gia_va_bien_ca_4)
        })

        // 2. Dế mèn phiêu lưu ký - sach2.jpg
        list.add(Sach("Dế mèn phiêu lưu ký", "Tô Hoài", R.drawable.sach2,
            "Hành trình trưởng thành của chú Dế Mèn qua những bài học đường đời đầu tiên.", "Thiếu nhi", 4.5, 850).apply {
            cacPhan = arrayListOf(
                PhanSach("Chương 1", "Tôi ra đời và những bài học đầu tiên...", R.raw.de_men_1),
                PhanSach("Chương 2", "Cuộc phiêu lưu bắt đầu...", R.raw.de_men_2),
                PhanSach("Chương 3", "Những người bạn mới trên đường đi...", R.raw.de_men_3),
                PhanSach("Chương 4", "Bài học về lòng nhân ái...", R.raw.de_men_4)
            )
            danhSachAudio = arrayListOf(R.raw.de_men_1, R.raw.de_men_2, R.raw.de_men_3, R.raw.de_men_4)
        })

        // 3. Tắt đèn - sach3.jpg
        list.add(Sach("Tắt đèn", "Ngô Tất Tố", R.drawable.sach3,
            "Nỗi khổ của người nông dân dưới ách áp bức của chế độ thực dân phong kiến.", "Văn học", 4.7, 500).apply {
            cacPhan = arrayListOf(
                PhanSach("Phần 1", "Gia cảnh chị Dậu...", R.raw.tat_den_1),
                PhanSach("Phần 2", "Sự hống hách của bọn lý dịch...", R.raw.tat_den_2),
                PhanSach("Phần 3", "Cảnh bán con bán chó...", R.raw.tat_den_3),
                PhanSach("Phần 4", "Cuộc đối đầu với cai lệ...", R.raw.tat_den_4),
                PhanSach("Phần 5", "Vùng chạy ra ngoài trời tối đen...", R.raw.tat_den_5)
            )
            danhSachAudio = arrayListOf(R.raw.tat_den_1, R.raw.tat_den_2, R.raw.tat_den_3, R.raw.tat_den_4, R.raw.tat_den_5)
        })

        // 4. Sóc sờ sét - sach4.jpg
        list.add(Sach("Sóc sờ sét", "Khuyết danh", R.drawable.sach4,
            "Những câu chuyện ngụ ngôn và bài học thú vị về thế giới loài vật.", "Thiếu nhi", 4.2, 300).apply {
            cacPhan = arrayListOf(
                PhanSach("Phần 1", "Chú sóc nhỏ ham học hỏi...", R.raw.soc_so_set_1),
                PhanSach("Phần 2", "Thử thách trong khu rừng...", R.raw.soc_so_set_2),
                PhanSach("Phần 3", "Trở về trong vinh quang...", R.raw.soc_so_set_3)
            )
            danhSachAudio = arrayListOf(R.raw.soc_so_set_1, R.raw.soc_so_set_2, R.raw.soc_so_set_3)
        })

        // 5. Nơi nào có mẹ - sach5.jpg
        list.add(Sach("Nơi nào có mẹ", "Khuyết danh", R.drawable.sach5,
            "Câu chuyện cảm động về tình mẫu tử thiêng liêng.", "Văn học", 4.9, 750).apply {
            cacPhan = arrayListOf(
                PhanSach("Phần 1", "Kỷ niệm tuổi thơ bên mẹ...", R.raw.noi_nao_co_me_1),
                PhanSach("Phần 2", "Sức mạnh của tình thương...", R.raw.noi_nao_co_me_2)
            )
            danhSachAudio = arrayListOf(R.raw.noi_nao_co_me_1, R.raw.noi_nao_co_me_2)
        })

        // 6. Đất rừng phương Nam - sach6.jpg
        list.add(Sach("Đất rừng phương Nam", "Đoàn Giỏi", R.drawable.sach6,
            "Bức tranh thiên nhiên và con người vùng đất Nam Bộ hào sảng.", "Văn học", 4.6, 620).apply {
            cacPhan = arrayListOf(
                PhanSach("Chương 1", "An đi tìm cha giữa rừng tràm..."),
                PhanSach("Chương 2", "Cuộc sống cùng gia đình tía nuôi...")
            )
            // Nếu có audio tương ứng hãy thêm vào đây, hiện tại để trống
        })

        return list
    }

    fun saveBooksLocal(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(mangSachFull)
        editor.putString(KEY_BOOKS, json)
        editor.apply()
    }

    fun pushAllToFirebase() {
        // Xóa sạch dữ liệu cũ để loại bỏ các bản ghi lỗi (như quyển "a")
        booksRef.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                mangSachFull.forEach { sach ->
                    val bookKey = sach.tenSach.replace(".", "_")
                        .replace("#", "_")
                        .replace("$", "_")
                        .replace("[", "_")
                        .replace("]", "_")
                    booksRef.child(bookKey).setValue(sach)
                }
            }
        }
    }

    fun updateBook(context: Context, oldTitle: String, updatedBook: Sach) {
        val index = mangSachFull.indexOfFirst { it.tenSach == oldTitle }
        if (index != -1) {
            mangSachFull[index] = updatedBook
            saveBooksLocal(context)
            
            val bookKey = updatedBook.tenSach.replace(".", "_")
            booksRef.child(bookKey).setValue(updatedBook)
            if (oldTitle != updatedBook.tenSach) {
                booksRef.child(oldTitle.replace(".", "_")).removeValue()
            }
        }
    }

    fun deleteBook(context: Context, title: String) {
        mangSachFull.removeAll { it.tenSach == title }
        saveBooksLocal(context)
        booksRef.child(title.replace(".", "_")).removeValue()
    }

    fun addBook(context: Context, book: Sach) {
        mangSachFull.add(book)
        saveBooksLocal(context)
        booksRef.child(book.tenSach.replace(".", "_")).setValue(book)
    }
}
