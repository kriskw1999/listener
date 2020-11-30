package com.example.spoppinglist.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.spoppinglist.R
import com.example.spoppinglist.data.product.Product

class ProductAdapter(var data: List<Product>, private  val context: Context): MoltenAdapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.product_layout, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.product = data[position]

        holder.productName.text = holder.product!!.name
    }

    override fun getItemCount(): Int {
        return data.size
    }
}