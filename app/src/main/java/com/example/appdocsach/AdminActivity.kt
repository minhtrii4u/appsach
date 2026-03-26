package com.example.appdocsach

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdminActivity : AppCompatActivity() {

    private lateinit var rcvAdminSach: RecyclerView
    private lateinit var btnAddBook: Button
    private lateinit var adapter: AdminSachAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        rcvAdminSach = findViewById(R.id.rcvAdminSach)
        btnAddBook = findViewById(R.id.btnAddBook)

        BookManager.loadBooks(this)
        
        adapter = AdminSachAdapter(BookManager.mangSachFull) { sach ->
            val intent = Intent(this, EditBookActivity::class.java)
            intent.putExtra("edit_sach", sach)
            startActivity(intent)
        }

        rcvAdminSach.adapter = adapter
        rcvAdminSach.layoutManager = GridLayoutManager(this, 1) // Using 1 column as per typical admin list or 2 if preferred

        btnAddBook.setOnClickListener {
            val intent = Intent(this, EditBookActivity::class.java)
            intent.putExtra("is_add", true)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        BookManager.loadBooks(this)
        adapter.updateData(BookManager.mangSachFull)
    }
}