package com.example.appdocsach

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object BookManager {
    private const val PREFS_NAME = "BookPrefs"
    private const val KEY_BOOKS = "all_books"
    var mangSachFull: ArrayList<Sach> = ArrayList()

    fun loadBooks(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(KEY_BOOKS, null)
        if (json != null) {
            val type = object : TypeToken<ArrayList<Sach>>() {}.type
            mangSachFull = Gson().fromJson(json, type)
        } else {
            // Initial data if none exists
            mangSachFull = arrayListOf(
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
            saveBooks(context)
        }
    }

    fun saveBooks(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(mangSachFull)
        editor.putString(KEY_BOOKS, json)
        editor.apply()
    }

    fun updateBook(context: Context, oldTitle: String, updatedBook: Sach) {
        val index = mangSachFull.indexOfFirst { it.tenSach == oldTitle }
        if (index != -1) {
            mangSachFull[index] = updatedBook
            saveBooks(context)
        }
    }

    fun deleteBook(context: Context, title: String) {
        mangSachFull.removeAll { it.tenSach == title }
        saveBooks(context)
    }

    fun addBook(context: Context, book: Sach) {
        mangSachFull.add(book)
        saveBooks(context)
    }
}