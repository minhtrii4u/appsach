package com.example.appdocsach

import com.google.firebase.database.FirebaseDatabase

class FirebaseHelper {
    private val database = FirebaseDatabase.getInstance("https://book-a8796-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

    fun addSampleData() {
        val adminsRef = database.child("admins")
        val usersRef = database.child("users")
        val authorsRef = database.child("authors")
        val booksRef = database.child("books")
        val bookPartsRef = database.child("book_parts")
        val bookContentsRef = database.child("book_contents")
        val favoriteBooksRef = database.child("favorite_books")

        // 2.1. Bảng admins
        val adminId = "admin1"
        val admin = Admin(adminId, "huy@gmail.com", "08112006")
        adminsRef.child(adminId).setValue(admin)

        // 2.2. Bảng users
        val userId = "user1"
        val user = User(userId, "MinhTri@gmail.com", "25082006", "Minh Trí")
        usersRef.child(userId).setValue(user)

        // 2.3. Bảng authors
        val authorId = "author1"
        val author = Author(authorId, "Ernest Hemingway", "Ernest Miller Hemingway là một tiểu thuyết gia người Mỹ, nhà văn viết truyện ngắn và là một nhà báo.")
        authorsRef.child(authorId).setValue(author)

        // 2.4. Bảng books
        val bookId = "book1"
        val book = Book(
            id = bookId,
            title = "Ông già và biển cả",
            authorId = authorId,
            coverImageRes = R.drawable.sach1,
            description = "Câu chuyện kể về cuộc chiến đấu không cân sức giữa ông già Santiago và con cá kiếm khổng lồ...",
            category = "Văn học"
        )
        booksRef.child(bookId).setValue(book)

        // 2.5. Bảng book_parts
        val partId = "part1"
        val bookPart = BookPart(
            id = partId,
            bookId = bookId,
            partNumber = 1,
            title = "Chương 1: Ra khơi"
        )
        bookPartsRef.child(partId).setValue(bookPart)

        // 2.6. Bảng book_contents
        val contentId = "content1"
        val bookContent = BookContent(
            id = contentId,
            bookPartId = partId,
            content = "Ông là một ông già đánh cá một mình trên một chiếc thuyền nhỏ giữa dòng Gulf Stream..."
        )
        bookContentsRef.child(contentId).setValue(bookContent)

        // 2.7. Bảng favorite_books
        val favId = "fav1"
        val favorite = FavoriteBook(favId, userId, bookId)
        favoriteBooksRef.child(favId).setValue(favorite)
    }
}
