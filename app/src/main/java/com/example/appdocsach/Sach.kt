package com.example.appdocsach

import java.io.Serializable
class Sach : Serializable {
    var tenSach: String = ""
    var tacGia: String = ""
    var hinhAnh: Int = 0

    constructor(ten: String, tg: String, hinh: Int) {
        this.tenSach = ten
        this.tacGia = tg
        this.hinhAnh = hinh
    }
}