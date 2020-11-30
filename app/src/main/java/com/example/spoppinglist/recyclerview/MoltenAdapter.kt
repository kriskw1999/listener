package com.example.spoppinglist.recyclerview

import androidx.recyclerview.widget.RecyclerView

abstract class MoltenAdapter<VH: RecyclerView.ViewHolder>(): RecyclerView.Adapter<VH>() {

    //last changing state
    companion object {
        const val NO_ACTION = 0
        const val INSERTED = 1
        const val DELETED = 2
        const val CHANGED= 3
        const val DATA_SET_CHANGED = 4
    }

    var lastAction: Int = NO_ACTION

    //last deleted or changed item
    var changedElement = 0

    fun update(){
        when(lastAction) {
            NO_ACTION ->{}
            DELETED -> notifyItemRemoved(changedElement-1)
            INSERTED -> notifyItemInserted(itemCount)
            CHANGED -> notifyItemChanged(changedElement)
            DATA_SET_CHANGED -> notifyDataSetChanged()
        }
    }
}