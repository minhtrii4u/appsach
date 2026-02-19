package com.example.appdocsach

import java.io.Serializable

class Sach : Serializable {
    var tenSach: String = ""
    var tacGia: String = ""
    var hinhAnh: Int = 0
    var tomTat: String = ""

    constructor(ten: String, tg: String, hinh: Int, tt: String) {
        this.tenSach = ten
        this.tacGia = tg
        this.hinhAnh = hinh
        this.tomTat = tt
    }
}