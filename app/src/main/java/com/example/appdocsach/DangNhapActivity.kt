package com.example.appdocsach

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DangNhapActivity : AppCompatActivity() {

    lateinit var edtEmail: EditText
    lateinit var edtPass: EditText
    lateinit var btnLogin: Button
    lateinit var tvChuyenDK: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dang_nhap)

        edtEmail = findViewById(R.id.edtEmailDN)
        edtPass = findViewById(R.id.edtPassDN)
        btnLogin = findViewById(R.id.btnDangNhap)
        tvChuyenDK = findViewById(R.id.tvChuyenDangKy)
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
                val boNho = getSharedPreferences("DuLieuTaiKhoan", Context.MODE_PRIVATE)
                val emailLuu = boNho.getString("saved_email", "")
                val passLuu = boNho.getString("saved_pass", "")

                if ((emailNhap == emailLuu && passNhap == passLuu) ||

                    (emailNhap == "MinhTri@gmail.com" && passNhap == "25082006") ||
                    (emailNhap == "huy@gmail.com" && passNhap == "08112006")) {
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}