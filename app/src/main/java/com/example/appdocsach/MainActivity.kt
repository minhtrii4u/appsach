package com.example.appdocsach

import android.os.Bundle
import android.widget.TextView
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

        val tvXinChao: TextView = findViewById(R.id.tvXinChao)
        val tenUser = intent.getStringExtra("gui_ten_user")

        if (tenUser != null && tenUser.isNotEmpty()) {
            tvXinChao.text = "Xin chào, $tenUser "
        }

        rcvSach = findViewById(R.id.rcvDanhSach)

        mangSach.add(Sach("Ông già và biển cả", "Ernest Hemingway", R.drawable.sach1,
            "Câu chuyện kể về cuộc chiến đấu không cân sức giữa ông già Santiago và con cá kiếm khổng lồ..."))

        mangSach.add(Sach("Dế mèn phiêu lưu ký", "Tô Hoài", R.drawable.sach2,
            "Tác phẩm kể về cuộc phiêu lưu của chú Dế Mèn qua nhiều vùng đất, học được nhiều bài học đường đời..."))

        mangSach.add(Sach("Yêu trên từng ngón tay", "Trần Trà My", R.drawable.sach3,
            "Những câu chuyện nhẹ nhàng về tình yêu, cuộc sống của một cô gái trẻ đầy nghị lực..."))

        mangSach.add(Sach("Sóc sợ sệt", "Milano Walt", R.drawable.sach4,
            "Chú sóc nhỏ luôn lo lắng về mọi thứ xung quanh, nhưng rồi cậu học được cách dũng cảm đối mặt..."))

        mangSach.add(Sach("Nơi nào có mẹ là nhà", "Hạ Mer", R.drawable.sach5,
            "Tuyển tập tản văn xúc động về tình mẫu tử, về những bữa cơm nhà và sự bình yên bên mẹ..."))

        mangSach.add(Sach("Tắt đèn", "Ngô Tất Tố", R.drawable.sach6,
            "Bức tranh hiện thực về cuộc sống khốn cùng của người nông dân Việt Nam dưới chế độ thực dân phong kiến..."))

        adapterSach = SachAdapter(mangSach)
        rcvSach.adapter = adapterSach
        rcvSach.layoutManager = GridLayoutManager(this, 2)
    }
}