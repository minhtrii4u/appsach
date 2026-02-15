package com.example.appdocsach

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DangKyActivity : AppCompatActivity() {

    lateinit var edtEmail: EditText
    lateinit var edtPass: EditText
    lateinit var edtPass2: EditText
    lateinit var btnDK: Button
    lateinit var tvBack: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dang_ky)

        edtEmail = findViewById(R.id.edtEmailDK)
        edtPass = findViewById(R.id.edtPassDK)
        edtPass2 = findViewById(R.id.edtRePassDK)
        btnDK = findViewById(R.id.btnXacNhanDK)
        tvBack = findViewById(R.id.tvDaCoTK)

        tvBack.setOnClickListener {
            finish()
        }

        btnDK.setOnClickListener {
            val email = edtEmail.text.toString()
            val pass = edtPass.text.toString()
            val pass2 = edtPass2.text.toString()

            if (email == "" || pass == "" || pass2 == "") {
                Toast.makeText(this, "Nhập thiếu thông tin rồi!", Toast.LENGTH_SHORT).show()
            } else {

                if (pass == pass2) {

                    val pref = getSharedPreferences("TaiKhoanLuu", Context.MODE_PRIVATE)
                    val editor = pref.edit()

                    editor.putString("email", email)
                    editor.putString("pass", pass)
                    editor.apply() // Lưu xuống

                    Toast.makeText(this, "Đăng ký xong! Về đăng nhập nhé", Toast.LENGTH_SHORT).show()
                    finish() // Đóng màn hình
                } else {
                    Toast.makeText(this, "Mật khẩu nhập lại không khớp", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}