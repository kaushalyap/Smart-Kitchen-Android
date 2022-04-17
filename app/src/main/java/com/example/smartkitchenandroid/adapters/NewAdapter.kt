package com.example.smartkitchenandroid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartkitchenandroid.R
import com.example.smartkitchenandroid.models.Order

class NewAdapter(var dataSet: ArrayList<Order>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<NewAdapter.ViewHolder>() {

    class ViewHolder(view: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(view) {
        val txtOrder: TextView
        val txtStatus: TextView
        val cbCheck: CheckBox

        init {
            txtOrder = view.findViewById(R.id.txt_order)
            txtStatus = view.findViewById(R.id.txt_status)
            cbCheck = view.findViewById(R.id.cb_complete)
            view.setOnClickListener {
                cbCheck.isChecked = true
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_order, viewGroup, false)
        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.txtOrder.text = dataSet[position].order
        viewHolder.txtStatus.text = dataSet[position].status.name
    }

    override fun getItemCount(): Int = dataSet.size

    companion object {
        const val TAG: String = "NewAdapter"
    }
}

