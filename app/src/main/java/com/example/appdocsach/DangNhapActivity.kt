package com.example.appdocsach

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DangNhapActivity : AppCompatActivity() {

    lateinit var edtEmail: EditText
    lateinit var edtPass: EditText
    lateinit var btnLogin: Button
    lateinit var tvChuyenDK: TextView
    lateinit var btnTogglePassword: ImageButton
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dang_nhap)

        edtEmail = findViewById(R.id.edtEmailDN)
        edtPass = findViewById(R.id.edtPassDN)
        btnLogin = findViewById(R.id.btnDangNhap)
        tvChuyenDK = findViewById(R.id.tvChuyenDangKy)
        btnTogglePassword = findViewById(R.id.btnTogglePasswordDN)

        btnTogglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                edtPass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                btnTogglePassword.setImageResource(R.drawable.ic_eye_show)
            } else {
                edtPass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                btnTogglePassword.setImageResource(R.drawable.ic_eye_hide)
            }
            edtPass.setSelection(edtPass.text.length)
        }

        tvChuyenDK.setOnClickListener {
            val i = Intent(this, DangKyActivity::class.java)
            startActivity(i)
        }

        btnLogin.setOnClickListener {
            val emailNhap = edtEmail.text.toString().trim()
            val passNhap = edtPass.text.toString().trim()

            if (emailNhap == "" || passNhap == "") {
                Toast.makeText(this, "Nhập thiếu thông tin", Toast.LENGTH_SHORT).show()
            } else {
                // Kiểm tra tài khoản Admin cố định
                if ((emailNhap == "MinhTri@gmail.com" && passNhap == "25082006") ||
                    (emailNhap == "huy@gmail.com" && passNhap == "08112006")) {
                    
                    Toast.makeText(this, "Đăng nhập ADMIN thành công", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, AdminActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnClickListener
                }

                // Kiểm tra tài khoản trên Firebase Database
                val database = FirebaseDatabase.getInstance("https://book-a8796-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
                database.child("users").orderByChild("gmail").equalTo(emailNhap)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                var found = false
                                for (userSnapshot in snapshot.children) {
                                    val user = userSnapshot.getValue(User::class.java)
                                    if (user != null && user.password == passNhap) {
                                        found = true
                                        // Lưu thông tin vào local SharedPreferences để đồng bộ các trang khác
                                        val pref = getSharedPreferences("DuLieuTaiKhoan", Context.MODE_PRIVATE)
                                        val editor = pref.edit()
                                        editor.putString("user_id", user.id)
                                        editor.putString("saved_email", user.gmail)
                                        editor.putString("saved_pass", user.password)
                                        editor.apply()

                                        Toast.makeText(this@DangNhapActivity, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this@DangNhapActivity, MainActivity::class.java)
                                        intent.putExtra("gui_ten_user", user.name)
                                        startActivity(intent)
                                        finish()
                                        break
                                    }
                                }
                                if (!found) {
                                    Toast.makeText(this@DangNhapActivity, "Sai mật khẩu!", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(this@DangNhapActivity, "Tài khoản không tồn tại!", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(this@DangNhapActivity, "Lỗi kết nối: ${error.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }
    }
}
