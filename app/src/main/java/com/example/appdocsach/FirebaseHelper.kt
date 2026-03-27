package com.example.appdocsach

import com.google.firebase.database.FirebaseDatabase

class FirebaseHelper {
    private val database = FirebaseDatabase.getInstance("https://book-a8796-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

    fun uploadAllBooksToFirebase(books: ArrayList<Sach>) {
        val booksRef = database.child("books")
        val bookPartsRef = database.child("book_parts")

        books.forEach { sach ->
            // 1. Lưu thông tin sách chính
            val bookId = sach.tenSach.replace(" ", "_").lowercase() // Hoặc dùng push().key
            val book = Book(
                id = bookId,
                title = sach.tenSach,
                authorId = sach.tacGia,
                coverImageRes = sach.hinhAnh,
                description = sach.tomTat,
                category = sach.theLoai,
                ratingScore = sach.diemDanhGia,
                ratingCount = sach.luotDanhGia
            )
            booksRef.child(bookId).setValue(book)

            // 2. Lưu các phần của sách (Mục lục)
            sach.cacPhan.forEachIndexed { index, phan ->
                val partId = "${bookId}_part_${index + 1}"
                val bookPart = BookPart(
                    id = partId,
                    bookId = bookId,
                    partNumber = index + 1,
                    title = phan.tieuDe
                )
                bookPartsRef.child(partId).setValue(bookPart)
                
                // Lưu nội dung chi tiết của phần đó
                val contentId = "${partId}_content"
                val bookContent = BookContent(
                    id = contentId,
                    bookPartId = partId,
                    content = phan.noiDung
                )
                database.child("book_contents").child(contentId).setValue(bookContent)
            }
        }
    }
}
