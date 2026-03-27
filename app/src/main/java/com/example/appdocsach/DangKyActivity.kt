package com.example.appdocsach

import android.content.Context
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

class DangKyActivity : AppCompatActivity() {

    lateinit var edtTenDangNhap: EditText
    lateinit var edtEmail: EditText
    lateinit var edtPass: EditText
    lateinit var edtPass2: EditText
    lateinit var btnDK: Button
    lateinit var tvBack: TextView
    lateinit var btnTogglePassword: ImageButton
    lateinit var btnToggleConfirmPassword: ImageButton
    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dang_ky)

        edtTenDangNhap = findViewById(R.id.edtTenDangNhap)
        edtEmail = findViewById(R.id.edtEmailDK)
        edtPass = findViewById(R.id.edtPassDK)
        edtPass2 = findViewById(R.id.edtRePassDK)
        btnDK = findViewById(R.id.btnXacNhanDK)
        tvBack = findViewById(R.id.tvDaCoTK)
        btnTogglePassword = findViewById(R.id.btnTogglePasswordDK)
        btnToggleConfirmPassword = findViewById(R.id.btnToggleConfirmPasswordDK)

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

        btnToggleConfirmPassword.setOnClickListener {
            isConfirmPasswordVisible = !isConfirmPasswordVisible
            if (isConfirmPasswordVisible) {
                edtPass2.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                btnToggleConfirmPassword.setImageResource(R.drawable.ic_eye_show)
            } else {
                edtPass2.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                btnToggleConfirmPassword.setImageResource(R.drawable.ic_eye_hide)
            }
            edtPass2.setSelection(edtPass2.text.length)
        }

        tvBack.setOnClickListener {
            finish()
        }

        btnDK.setOnClickListener {
            val username = edtTenDangNhap.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val pass = edtPass.text.toString().trim()
            val pass2 = edtPass2.text.toString().trim()

            if (username == "" || email == "" || pass == "" || pass2 == "") {
                Toast.makeText(this, "Nhập thiếu thông tin rồi!", Toast.LENGTH_SHORT).show()
            } else {
                if (pass == pass2) {
                    val database = FirebaseDatabase.getInstance("https://book-a8796-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
                    
                    // Kiểm tra email đã tồn tại hay chưa
                    database.child("users").orderByChild("gmail").equalTo(email)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()) {
                                    // Email đã tồn tại
                                    Toast.makeText(this@DangKyActivity, "Email này đã được đăng ký!", Toast.LENGTH_SHORT).show()
                                } else {
                                    // Email chưa tồn tại, tiến hành đăng ký
                                    val userId = database.child("users").push().key ?: email.replace(".", "_")
                                    
                                    // 1. Lưu local để dùng ngay
                                    val pref = getSharedPreferences("DuLieuTaiKhoan", Context.MODE_PRIVATE)
                                    val editor = pref.edit()
                                    editor.putString("user_id", userId)
                                    editor.putString("saved_email", email)
                                    editor.putString("saved_pass", pass)
                                    editor.apply()

                                    // 2. Lưu lên Firebase Database
                                    val newUser = User(
                                        id = userId,
                                        gmail = email,
                                        password = pass,
                                        name = username
                                    )
                                    
                                    database.child("users").child(userId).setValue(newUser)
                                        .addOnSuccessListener {
                                            Toast.makeText(this@DangKyActivity, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                                            finish()
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(this@DangKyActivity, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Toast.makeText(this@DangKyActivity, "Lỗi kiểm tra dữ liệu!", Toast.LENGTH_SHORT).show()
                            }
                        })
                } else {
                    Toast.makeText(this, "Mật khẩu nhập lại không khớp", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}