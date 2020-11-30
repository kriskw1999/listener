package com.example.spoppinglist.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spoppinglist.R
import com.example.spoppinglist.data.product.Product

class ProductViewHolder(view: View): RecyclerView.ViewHolder(view){
    var product: Product? = null
    var productName: TextView = view.findViewById(R.id.product_name_list)
}