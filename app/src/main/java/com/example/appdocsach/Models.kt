package com.example.appdocsach


data class Account(
    val email: String = "",
    val password: String = "",
    val isAdmin: Boolean = false
)

data class Book(
    val tenSach: String = "",
    val tacGia: String = "",
    val moTa: String = "",
    val theLoai: String = "",
    val imageUrl: String = "",
    val danhSachAudio: ArrayList<Int> = ArrayList()
)

data class Favorite(
    val userEmail: String = "",
    val bookId: String = ""
)