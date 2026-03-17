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

class DangKyActivity : AppCompatActivity() {

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

        edtEmail = findViewById(R.id.edtEmailDK)
        edtPass = findViewById(R.id.edtPassDK)
        edtPass2 = findViewById(R.id.edtRePassDK)
        btnDK = findViewById(R.id.btnXacNhanDK)
        tvBack = findViewById(R.id.tvDaCoTK)
        btnTogglePassword = findViewById(R.id.btnTogglePasswordDK)
        btnToggleConfirmPassword = findViewById(R.id.btnToggleConfirmPasswordDK)

        // Handle password visibility toggle for password field
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

        // Handle password visibility toggle for confirm password field
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