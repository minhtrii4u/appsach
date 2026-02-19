package com.example.appdocsach

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SachAdapter(var dsSach: ArrayList<Sach>) : RecyclerView.Adapter<SachAdapter.SachViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SachViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val viewDoiTuong = inflater.inflate(R.layout.item_sach, parent, false)
        return SachViewHolder(viewDoiTuong)
    }

    override fun onBindViewHolder(holder: SachViewHolder, position: Int) {

        val sachHienTai = dsSach[position]


        holder.txtTen.text = sachHienTai.tenSach
        holder.txtTacGia.text = sachHienTai.tacGia
        holder.imgHinh.setImageResource(sachHienTai.hinhAnh)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context


            val intent = Intent(context, ChiTietActivity::class.java)

            intent.putExtra("gui_ten", sachHienTai.tenSach)
            intent.putExtra("gui_tacgia", sachHienTai.tacGia)
            intent.putExtra("gui_hinh", sachHienTai.hinhAnh)
            intent.putExtra("gui_tomtat", sachHienTai.tomTat)


            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dsSach.size
    }

    class SachViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgHinh: ImageView = itemView.findViewById(R.id.imgSach)
        var txtTen: TextView = itemView.findViewById(R.id.tvTenSach)
        var txtTacGia: TextView = itemView.findViewById(R.id.tvTacGia)
    }
}