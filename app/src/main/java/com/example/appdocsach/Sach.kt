package com.example.appdocsach

import java.io.Serializable

class Sach : Serializable {
    var tenSach: String = ""
    var tacGia: String = ""
    var hinhAnh: Int = 0
    var tomTat: String = ""
    var theLoai: String = ""
    var gioiThieuTacGia: String = ""
    var diemDanhGia: Double = 0.0
    var luotDanhGia: Int = 0
    var cacPhan: ArrayList<PhanSach> = ArrayList()
    // Giữ lại danh sách audio cũ nếu cần, nhưng logic mới sẽ ưu tiên audio trong từng phần
    var danhSachAudio: ArrayList<Int> = ArrayList()

    constructor() // Cần cho Firebase

    constructor(ten: String, tg: String, hinh: Int, tt: String, tl: String, diem: Double = 0.0, luot: Int = 0, audio: ArrayList<Int> = ArrayList()) {
        this.tenSach = ten
        this.tacGia = tg
        this.hinhAnh = hinh
        this.tomTat = tt
        this.theLoai = tl
        this.diemDanhGia = diem
        this.luotDanhGia = luot
        this.danhSachAudio = audio
    }
}

class PhanSach : Serializable {
    var tieuDe: String = ""
    var noiDung: String = ""
    var audioResId: Int = 0 // Resource ID của audio cho phần này
    
    constructor() // Cần cho Firebase

    constructor(tieuDe: String, noiDung: String, audioResId: Int = 0) {
        this.tieuDe = tieuDe
        this.noiDung = noiDung
        this.audioResId = audioResId
    }
}
