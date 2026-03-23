package com.example.appdocsach

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.appcompat.widget.SearchView
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.Intent
import android.text.InputType
import android.widget.EditText
import java.io.Serializable

class SearchActivity : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private lateinit var rcvSearchResult: RecyclerView
    private var adapterSach: SachAdapter? = null
    private lateinit var bottomNavigationView: BottomNavigationView
    private var mangSach: ArrayList<Sach> = ArrayList()
    private var mangSachFull: ArrayList<Sach> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchView = findViewById(R.id.searchView)
        rcvSearchResult = findViewById(R.id.rcvSearchResult)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        // Cấu hình SearchView để gõ được tiếng Việt
        val searchEditText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES

        // Nhận danh sách sách từ MainActivity an toàn hơn
        val data = intent.getSerializableExtra("mangSachFull")
        if (data is ArrayList<*>) {
            mangSachFull = data.filterIsInstance<Sach>() as ArrayList<Sach>
        }
        
        val tenUser = intent.getStringExtra("gui_ten_user")
        
        mangSach.clear()
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
                R.id.nav_library -> true
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
        adapterSach?.notifyDataSetChanged()
    }
}
