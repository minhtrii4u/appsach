package com.example.appdocsach

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var rcvSach: RecyclerView
    lateinit var adapterSach: SachAdapter
    var mangSach: ArrayList<Sach> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rcvSach = findViewById(R.id.rcvDanhSach)


        mangSach.add(Sach("Ông già và biển cả", "Ernest Hemingway", R.drawable.sach1))
        mangSach.add(Sach("Dế mèn phiêu lưu ký", "Tô Hoài", R.drawable.sach2))
        mangSach.add(Sach("Yêu trên từng ngón tay", "Trần Trà My", R.drawable.sach3))
        mangSach.add(Sach(ten = "Sóc sợ sệt", tg = "Milano Walt", hinh = R.drawable.sach4))
        mangSach.add(Sach(ten = "Nơi nào có mẹ là nhà", tg = "Hạ Mer", hinh = R.drawable.sach5))
        mangSach.add(Sach("Tắt đèn", "Ngô Tất Tố", R.drawable.sach6))

        adapterSach = SachAdapter(mangSach)
        rcvSach.adapter = adapterSach
        rcvSach.layoutManager = GridLayoutManager(this, 2)
    }
}