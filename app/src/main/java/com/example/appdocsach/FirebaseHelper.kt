package com.example.appdocsach

import com.google.firebase.database.FirebaseDatabase

class FirebaseHelper {
    private val database = FirebaseDatabase.getInstance("https://book-a8796-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

    fun addSampleData() {
        val accountsRef = database.child("accounts")
        val booksRef = database.child("books")
        val favoritesRef = database.child("favorites")

        // Add admin account
        val admin = Account("admin@book.com", "admin123", true)
        accountsRef.child("admin").setValue(admin)

        // Add sample accounts
        val user1 = Account("MinhTri@gmail.com", "25082006", false)
        accountsRef.child("user1").setValue(user1)

        val user2 = Account("huy@gmail.com", "08112006", false)
        accountsRef.child("user2").setValue(user2)

        // Add books
        val books = listOf(
            Book("Ông già và biển cả", "Ernest Hemingway", "Câu chuyện kể về cuộc chiến đấu không cân sức giữa ông già Santiago và con cá kiếm khổng lồ...", "Văn học", "sach1.jpg"),
            Book("Dế mèn phiêu lưu ký", "Tô Hoài", "Tác phẩm kể về cuộc phiêu lưu của chú Dế Mèn qua nhiều vùng đất, học được nhiều bài học đường đời...", "Thiếu nhi", "sach2.jpg"),
            Book("Yêu trên từng ngón tay", "Trần Trà My", "Những câu chuyện nhẹ nhàng về tình yêu, cuộc sống của một cô gái trẻ đầy nghị lực...", "Tâm lý học", "sach3.jpg"),
            Book("Sóc sợ sệt", "Milano Walt", "Chú sóc nhỏ luôn lo lắng về mọi thứ xung quanh, nhưng rồi cậu học được cách dũng cảm đối mặt...", "Thiếu nhi", "sach4.jpg"),
            Book("Nơi nào có mẹ là nhà", "Hạ Mer", "Tuyển tập tản văn xúc động về tình mẫu tử, về những bữa cơm nhà và sự bình yên bên mẹ...", "Tâm lý học", "sach5.jpg"),
            Book("Tắt đèn", "Ngô Tất Tố", "Bức tranh hiện thực về cuộc sống khốn cùng của người nông dân Việt Nam dưới chế độ thực dân phong kiến...", "Văn học", "sach6.jpg")
        )

        books.forEachIndexed { index, book ->
            booksRef.child("book${index + 1}").setValue(book)
        }

        // Add sample favorites
        val favorite1 = Favorite("MinhTri@gmail.com", "book1")
        favoritesRef.child("fav1").setValue(favorite1)
    }
}
