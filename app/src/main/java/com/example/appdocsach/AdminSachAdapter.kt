package com.example.appdocsach

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdminSachAdapter(
    private var dsSach: ArrayList<Sach>,
    private val onItemClick: (Sach) -> Unit
) : RecyclerView.Adapter<AdminSachAdapter.AdminViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_admin_sach, parent, false)
        return AdminViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        val sach = dsSach[position]
        holder.tvTen.text = sach.tenSach
        holder.tvTacGia.text = sach.tacGia
        holder.imgHinh.setImageResource(sach.hinhAnh)
        
        holder.itemView.setOnClickListener { onItemClick(sach) }
    }

    override fun getItemCount(): Int = dsSach.size

    fun updateData(newData: ArrayList<Sach>) {
        this.dsSach = newData
        notifyDataSetChanged()
    }

    class AdminViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgHinh: ImageView = itemView.findViewById(R.id.imgAdminSach)
        val tvTen: TextView = itemView.findViewById(R.id.tvAdminTenSach)
        val tvTacGia: TextView = itemView.findViewById(R.id.tvAdminTacGia)
    }
}