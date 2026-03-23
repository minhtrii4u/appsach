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
                
                PHẦN 3: CUỘC CHIẾN VỚI CON CÁ KIẾM
                
                Lão già cảm thấy một sức nặng khó tin ở đầu dây bên kia. Nó không giật mạnh, nhưng sức kéo của nó lôi cả chiếc thuyền nhỏ trôi đi trên mặt biển. Lão biết mình đã câu được một con cá khổng lồ, một con cá kiếm vĩ đại nhất mà lão từng thấy trong đời.
                
                Mặt trời lên cao, mồ hôi ướt đẫm trán lão, đôi bàn tay bắt đầu ứa máu vì sợi dây cước siết chặt. Nhưng lão không bỏ cuộc. "Cá ơi," lão nói nhỏ, "Tao yêu mày và rất tôn trọng mày. Nhưng tao sẽ giết mày trước khi ngày hôm nay kết thúc."
                
                Cuộc giằng co kéo dài suốt hai ngày hai đêm. Con cá kéo chiếc thuyền đi xa mãi ra khơi, bỏ lại ánh đèn của Havana phía sau. Lão già đói, khát và kiệt sức, nhưng ý chí của lão còn mạnh hơn cả đại dương sâu thẳm. Lão lấy hết sức bình sinh, tự nhủ: "Con người có thể bị hủy diệt, nhưng không thể bị đánh bại."
            """.trimIndent()
        }
        else if (tenTruyen == "Dế mèn phiêu lưu ký") {
            noiDungTruyen = """
                CHƯƠNG 1: TÔI SỐNG ĐỘC LẬP TỪ THUỞ BÉ
                
                Tôi sống độc lập từ thuở bé. Ấy là tục lệ lâu đời trong họ nhà dế chúng tôi. Vả lại, mẹ thường bảo chúng tôi rằng: "Phải như thế để các con biết kiếm ăn một mình cho quen đi".
                
                Bởi thế, lứa sinh ấy, chúng tôi có ba anh em. Mẹ đưa chúng tôi ra bờ ruộng, đào cho mỗi đứa một cái hang. Mẹ đem đến tận cửa hang một ít thức ăn, rồi mẹ bảo:
                - Nào, các con hãy vào nhà mới của mình và bắt đầu cuộc sống tự lập đi nhé!
                
                Thế là chúng tôi chia tay mẹ, chia tay nhau, đứa nào vào hang nấy...
                
                CHƯƠNG 2: BÀI HỌC ĐƯỜNG ĐỜI ĐẦU TIÊN
                
                Bởi tôi ăn uống điều độ và làm việc có chừng mực nên tôi chóng lớn lắm. Chẳng bao lâu, tôi đã trở thành một chàng dế thanh niên cường tráng. Đôi càng tôi mẫm bóng. Những cái vuốt ở chân, ở khoeo cứ cứng dần và nhọn hoắt...
            """.trimIndent()
        }
        else if (tenTruyen == "Yêu trên từng ngón tay") {
            noiDungTruyen = """
                CHƯƠNG 1: NHỮNG NGÓN TAY ĐAN
                
                Hà Nội vào thu, trời se lạnh. Quán cà phê nhỏ nằm nép mình trong con ngõ tĩnh lặng thoang thoảng mùi hoa sữa. Lan mân mê ly trà nóng trên tay, ánh mắt lơ đãng nhìn qua khung cửa sổ. Cô luôn thích những buổi chiều như thế này, một mình, chậm rãi và không vướng bận.
                
                Cho đến khi anh bước vào.
                
                Tiếng chuông gió kêu leng keng phá vỡ bầu không gian yên tĩnh. Nam bước tới quầy gọi đồ, vô tình làm rơi chiếc chìa khóa xuống sàn. Theo phản xạ, Lan cúi xuống nhặt giúp anh. Hai bàn tay vô tình chạm nhẹ vào nhau. Một cảm giác ấm áp lạ thường chạy dọc qua từng ngón tay...
                
                CHƯƠNG 2: LỜI TỎ TÌNH TRONG MƯA
                
                "Tình yêu đôi khi không bắt đầu từ những lời đường mật, mà từ một cái nắm tay thật chặt khi giông bão kéo đến." Cơn mưa rào bất chợt đổ xuống khiến cả hai phải trú tạm dưới mái hiên của một tiệm sách cũ...
            """.trimIndent()
        }
        else if (tenTruyen == "Sóc sợ sệt") {
            noiDungTruyen = """
                PHẦN 1: NỖI SỢ CỦA CHÚ SÓC NHỎ
                
                Trong khu rừng Hạt Dẻ, có một chú sóc tên là Sóc Sợ Sệt. Khác với những người bạn luôn bay nhảy lanh lợi trên các cành cây cao, Sóc Sợ Sệt lúc nào cũng run rẩy.
                
                Cậu sợ tiếng gió rít qua kẽ lá. Cậu sợ những chiếc bóng đen ngoằn ngoèo khi trời tối. Và đặc biệt, cậu cực kỳ sợ độ cao. Mỗi lần nhìn xuống mặt đất từ trên cành cây, đôi chân cậu lại cứng đờ không bước nổi.
                
                "Cậu phải tập dũng cảm lên chứ!" - Bác Cú Mèo nói vọng xuống. Nhưng Sóc Sợ Sệt chỉ biết ôm cái đuôi to xù của mình lùi lại vào sâu trong hốc cây.
                
                PHẦN 2: CHUYẾN PHIÊU LƯU BẤT ĐẮC DĨ
                
                Một ngày nọ, một cơn bão lớn tràn qua khu rừng. Hốc cây của Sóc Sợ Sệt bị cành cây gãy đè lên. Không còn cách nào khác, cậu phải bò ra ngoài và tìm một nơi trú ẩn mới...
            """.trimIndent()
        }
        else if (tenTruyen == "Nơi nào có mẹ là nhà") {
            noiDungTruyen = """
                PHẦN 1: BẾP LỬA MÙA ĐÔNG
                
                Mùi khói bếp quê nhà luôn là thứ khiến tôi xao xuyến nhất mỗi khi đông về. Lớn lên ở thành phố sầm uất, quen với những bữa ăn nhanh và ánh đèn nê-ông sáng rực, nhưng trong ký ức tôi, không gì ấm áp bằng bóng lưng của mẹ mờ ảo trong làn khói rơm rạ.
                
                "Về ăn cơm đi con, mẹ nấu xong rồi."
                
                Câu nói giản dị ấy, qua bao nhiêu năm tháng, vẫn là âm thanh đẹp đẽ nhất mà tôi từng được nghe. Có những ngày đi làm về mệt mỏi, kẹt xe giữa dòng người hối hả, tôi chỉ muốn vứt bỏ tất cả để chạy về úp mặt vào đôi bàn tay đầy vết chai sần của mẹ.
                
                PHẦN 2: CHUYẾN TÀU VỀ QUÊ
                
                Vé tàu Tết năm nay hết sớm. Tôi may mắn mua được một vé ghế ngồi cứng. Hành trình mười mấy tiếng đồng hồ mệt mỏi dường như tan biến hết khi bước chân xuống ga, thấy mẹ đã đứng đó chờ từ lúc nào, khoác chiếc áo dạ cũ mềm...
            """.trimIndent()
        }
        else if (tenTruyen == "Tắt đèn") {
            noiDungTruyen = """
                CHƯƠNG 1
                
                Mới tờ mờ sáng, tiếng trống thúc sưu đã vang lên từng hồi dồn dập, rùng rợn như tiếng gọi hồn. Tiếng tù và thổi váng lên. Tiếng chó sủa ran. Trong làng, tiếng khóc lóc, tiếng kêu la thảm thiết vang lên khắp các ngõ ngách.
                
                Chị Dậu bế con cái Tí, mặt mày tái mét. Anh Dậu đang ốm liệt giường, vừa bị bọn cai lệ trói lôi ra đình đánh đập tàn nhẫn vì thiếu tiền sưu. Về đến nhà, anh mềm nhũn như một tàu lá chuối héo, nằm thoi thóp trên phản.
                
                Nhà chẳng còn hạt gạo nào. Chút khoai lang luộc từ hôm qua cũng đã nhường hết cho mấy đứa con. Chị Dậu gạt nước mắt, nhìn quanh căn nhà trống hoác. Chỉ còn ổ chó rơm và vài cái bát mẻ.
                
                CHƯƠNG 2
                
                Bọn cai lệ và người nhà lý trưởng lại xông vào. Chúng tay thước, tay roi, hầm hầm sấn sổ.
                - Thằng kia! Ông tưởng mày chết đêm qua, còn sống đấy à? Nộp tiền sưu! Không có thì ông trói cổ lại!
                
                Chị Dậu run run van xin: "Cháu van ông, nhà cháu vừa tỉnh được một lúc, ông tha cho..."
                Nhưng tên cai lệ tát đánh đốp vào mặt chị, rồi nhảy vào cạnh phản định lôi anh Dậu dậy...
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