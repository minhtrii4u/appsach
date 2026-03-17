package com.example.appdocsach

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.appcompat.widget.SearchView
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.Intent

class SearchActivity : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private lateinit var rcvSearchResult: RecyclerView
    private lateinit var adapterSach: SachAdapter
    private lateinit var bottomNavigationView: BottomNavigationView
    private var mangSach: ArrayList<Sach> = ArrayList()
    private var mangSachFull: ArrayList<Sach> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchView = findViewById(R.id.searchView)
        rcvSearchResult = findViewById(R.id.rcvSearchResult)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        // Nhận danh sách sách từ MainActivity (hoặc load lại từ nguồn dữ liệu)
        mangSachFull = intent.getSerializableExtra("mangSachFull") as? ArrayList<Sach> ?: ArrayList()
        val tenUser = intent.getStringExtra("gui_ten_user")
        mangSach.addAll(mangSachFull)

        adapterSach = SachAdapter(mangSach)
        rcvSearchResult.adapter = adapterSach
        rcvSearchResult.layoutManager = GridLayoutManager(this, 2)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterBooks(query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterBooks(newText)
                return true
            }
        })

        // Bottom navigation
        bottomNavigationView.selectedItemId = R.id.nav_search
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("gui_ten_user", tenUser)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    true
                }
                R.id.nav_search -> true
                R.id.nav_library -> {
                    // TODO: Chuyển sang activity tủ sách
                    true
                }
                R.id.nav_profile -> {
                  val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("gui_ten_user", tenUser)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun filterBooks(query: String?) {
        mangSach.clear()
        if (query.isNullOrBlank()) {
            mangSach.addAll(mangSachFull)
        } else {
            val lower = query.lowercase()
            mangSach.addAll(mangSachFull.filter {
                it.tenSach.lowercase().contains(lower) ||
                it.tacGia.lowercase().contains(lower) ||
                it.theLoai.lowercase().contains(lower)
            })
        }
        adapterSach.notifyDataSetChanged()
    }
}
