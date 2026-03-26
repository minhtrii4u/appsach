package com.example.appdocsach

import java.io.Serializable

// 2.1. Bảng admins
data class Admin(
    val id: String = "",
    val gmail: String = "",
    val password: String = "",
    val createdAt: Long = System.currentTimeMillis()
) : Serializable

// 2.2. Bảng users
data class User(
    val id: String = "",
    val gmail: String = "",
    val password: String = "",
    val name: String = "",
    val createdAt: Long = System.currentTimeMillis()
) : Serializable

// 2.3. Bảng authors
data class Author(
    val id: String = "",
    val name: String = "",
    val intro: String = "",
    val createdAt: Long = System.currentTimeMillis()
) : Serializable

// 2.4. Bảng books
data class Book(
    val id: String = "",
    val title: String = "",
    val authorId: String = "",
    val coverImage: String = "", // Path or resource name
    val coverImageRes: Int = 0, // Keep for backward compatibility with local resources
    val audio: String = "",
    val description: String = "",
    val category: String = "", // Added category for filtering
    val createdAt: Long = System.currentTimeMillis()
) : Serializable

// 2.5. Bảng book_parts
data class BookPart(
    val id: String = "",
    val bookId: String = "",
    val partNumber: Int = 0,
    val title: String = "",
    val audio: String = "",
    val createdAt: Long = System.currentTimeMillis()
) : Serializable

// 2.6. Bảng book_contents
data class BookContent(
    val id: String = "",
    val bookPartId: String = "",
    val content: String = "",
    val createdAt: Long = System.currentTimeMillis()
) : Serializable

// 2.7. Bảng favorite_books
data class FavoriteBook(
    val id: String = "",
    val userId: String = "",
    val bookId: String = "",
    val createdAt: Long = System.currentTimeMillis()
) : Serializable
