package com.example.appdocsach

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DocSachActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doc_sach)

        val tvTieuDe: TextView = findViewById(R.id.tvTieuDe)
        val tvNoiDung: TextView = findViewById(R.id.tvNoiDung)
        val tvBack: TextView = findViewById(R.id.tvBack)

        val tenTruyen = intent.getStringExtra("gui_ten_truyen")

        tvTieuDe.text = tenTruyen

        var noiDungTruyen = ""

        if (tenTruyen == "Ông già và biển cả") {
            noiDungTruyen = """
                PHẦN 1: LÃO GIÀ VÀ CẬU BÉ
                
                Lão già câu cá một mình trên chiếc thuyền nhỏ ở dòng Nhiệt lưu và đã tám mươi tư ngày qua lão không bắt được lấy một mống cá nào. Bốn mươi ngày đầu có một thằng bé đi cùng. Nhưng sau bốn mươi ngày không câu được gì, cha mẹ thằng bé bảo nó rằng rốt cuộc lão già đã trở thành "salao", tức là kẻ rủi ro tột cùng.

                Thế là thằng bé chuyển sang đi một chiếc thuyền khác, và ngay tuần đầu tiên thuyền ấy đã câu được ba con cá lớn. Thằng bé buồn lắm khi mỗi ngày thấy lão già giong thuyền về không, nó luôn xuống giúp lão mang cuộn dây hoặc cái móc, cái lao và cánh buồm quấn quanh cột. Cánh buồm được vá bằng bao bột mì, cuộn lại trông như lá cờ của một thất bại triền miên.
                
                Lão già gầy gò, hốc hác với những nếp nhăn hằn sâu sau gáy. Những vệt sạm nâu của căn bệnh ung thư da lành tính do ánh mặt trời phản chiếu trên mặt biển nhiệt đới gây ra xuất hiện trên má lão. Những vệt ấy lan xuống hai bên mặt, tay lão hằn những vết sẹo sâu do những lần kéo những con cá lớn. Nhưng chẳng có vết sẹo nào mới cả. Chúng cũ kỹ như những vệt xói mòn trên sa mạc không cá.
                
                Mọi thứ trên người lão đều toát lên vẻ già nua, trừ đôi mắt. Đôi mắt lão có màu của biển cả, vui vẻ và không hề thất bại.
                
                - Bác Santiago, - thằng bé nói khi hai người leo lên bờ nơi chiếc thuyền được kéo lên. - Cháu có thể đi lại với bác. Nhà cháu đã kiếm được khá khá rồi.
                
                Lão già dạy thằng bé câu cá và thằng bé yêu lão.
                - Không, - lão già nói. - Cháu đang đi với một chiếc thuyền may mắn. Hãy cứ ở lại với họ.
                
               
                
                PHẦN 2: RA KHƠI
                
                Lão bắt đầu chèo ra khỏi bến khi trời còn tối. Lão nghe thấy tiếng mái chèo của những chiếc thuyền khác cũng đang ra khơi trong bóng tối, dù lão không nhìn thấy họ. Thỉnh thoảng có tiếng nói, nhưng hầu hết các thuyền đều im lặng, chỉ có tiếng mái chèo nhúng nước.
                
                Khi ra đến biển lớn, lão thả mồi xuống ở các độ sâu khác nhau. Lão giữ cho những sợi dây thẳng đứng tuyệt đối để ở bất kỳ độ sâu nào trong bóng tối của dòng nước, mồi cũng đang chờ đợi đúng chỗ lão muốn...
                
               
            """.trimIndent()
        }
        else if (tenTruyen == "Dế mèn phiêu lưu ký") {
            noiDungTruyen = """
                CHƯƠNG 1: TÔI SỐNG ĐỘC LẬP TỪ THUỞ BÉ
                
                Tôi sống độc lập từ thuở bé. Ấy là tục lệ lâu đời trong họ nhà dế chúng tôi. Vả lại, mẹ thường bảo chúng tôi rằng: "Phải như thế để các con biết kiếm ăn một mình cho quen đi".
                
                Bởi thế, lứa sinh ấy, chúng tôi có ba anh em. Mẹ đưa chúng tôi ra bờ ruộng, đào cho mỗi đứa một cái hang. Mẹ đem đến tận cửa hang một ít thức ăn, rồi mẹ bảo:
                - Nào, các con hãy vào nhà mới của mình và bắt đầu cuộc sống tự lập đi nhé!
                
                Thế là chúng tôi chia tay mẹ, chia tay nhau, đứa nào vào hang nấy...
            """.trimIndent()
        }
        else {
            noiDungTruyen = "Nội dung của cuốn sách \"$tenTruyen\" đang được cập nhật.\n\nVui lòng quay lại sau!"
        }

        tvNoiDung.text = noiDungTruyen

        tvBack.setOnClickListener {
            finish()
        }
    }
}