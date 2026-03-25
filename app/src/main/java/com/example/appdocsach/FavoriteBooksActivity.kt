package com.example.appdocsach

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoriteBooksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_books)

        val rcvFavoriteBooks = findViewById<RecyclerView>(R.id.rcvFavoriteBooks)
        

        val sharedPreferences = getSharedPreferences("favorites", MODE_PRIVATE)
        val favoriteBooksSet = sharedPreferences.getStringSet("favorite_books", emptySet()) ?: emptySet()
        

        val dsSach = arrayListOf<Sach>()
        

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
        

        for (book in allBooks) {
            if (favoriteBooksSet.contains(book.tenSach)) {
                dsSach.add(book)
            }
        }
        
        val adapter = SachAdapter(dsSach)
        rcvFavoriteBooks.adapter = adapter
        rcvFavoriteBooks.layoutManager = GridLayoutManager(this, 2)
    }
}
