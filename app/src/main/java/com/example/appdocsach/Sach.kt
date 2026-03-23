package com.example.appdocsach

import java.io.Serializable



class Sach : Serializable {
    var tenSach: String = ""
    var tacGia: String = ""
    var hinhAnh: Int = 0
    var tomTat: String = ""
    var theLoai: String = ""

    var danhSachAudio: ArrayList<Int> = ArrayList()

    constructor(ten: String, tg: String, hinh: Int, tt: String, tl: String, audio: ArrayList<Int> = ArrayList()) {
        this.tenSach = ten
        this.tacGia = tg
        this.hinhAnh = hinh
        this.tomTat = tt
        this.theLoai = tl
        this.danhSachAudio = audio
    }
}