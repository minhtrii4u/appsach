package com.example.appdocsach

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.Intent
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileActivity : AppCompatActivity() {
    private val database = FirebaseDatabase.getInstance("https://book-a8796-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val tenUser = intent.getStringExtra("gui_ten_user")
        val tvName = findViewById<TextView>(R.id.tvName)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val tvSoSach = findViewById<TextView>(R.id.tvSoSach)
        val tvThoiGianDoc = findViewById<TextView>(R.id.tvThoiGianDoc)
        val tvSoSachYeuThich = findViewById<TextView>(R.id.tvSoSachYeuThich)
        val tvTuongTac = findViewById<TextView>(R.id.tvTuongTac)


        if (tenUser != null) {
            tvName.text = tenUser
            // Giả sử email được lưu trong SharedPreferences khi đăng nhập
            val pref = getSharedPreferences("DuLieuTaiKhoan", MODE_PRIVATE)
            val email = pref.getString("saved_email", "$tenUser@example.com")
            tvEmail.text = email

            // Lấy userId dựa trên email (trong thực tế nên dùng FirebaseAuth.uid)
            val userId = pref.getString("user_id", "user1") // Fallback test user1
            
            if (userId != null) {
                database.child("users").child(userId).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(User::class.java)
                        if (user != null) {
                            tvSoSach.text = "${user.totalBooksRead} cuốn"
                            tvThoiGianDoc.text = "${user.totalReadingTime} giờ"
                            tvSoSachYeuThich.text = "${user.favoriteCount} cuốn"
                            
                            // Format tương tác (ví dụ 1200 -> 1.2k)
                            val interactions = user.interactions
                            tvTuongTac.text = if (interactions >= 1000) {
                                String.format("%.1fk", interactions / 1000.0)
                            } else {
                                interactions.toString()
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            }
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.nav_profile
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("gui_ten_user", tenUser)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    true
                }
                R.id.nav_search -> {
                    val intent = Intent(this, SearchActivity::class.java)
                    intent.putExtra("gui_ten_user", tenUser)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    true
                }
                R.id.nav_library -> {
                    val intent = Intent(this, FavoriteBooksActivity::class.java)
                    intent.putExtra("gui_ten_user", tenUser)
                    startActivity(intent)
                    true
                }
                R.id.nav_profile -> true
                else -> false
            }
        }
    }
}
