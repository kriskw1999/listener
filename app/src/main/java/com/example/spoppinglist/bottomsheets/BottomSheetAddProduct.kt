package com.example.spoppinglist.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.spoppinglist.MainActivity
import com.example.spoppinglist.R
import com.example.spoppinglist.data.product.Product
import com.example.spoppinglist.recyclerview.MoltenAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton

class BottomSheetAddProduct : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.add_product_bottom_sheet_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cancelButton = view.findViewById<MaterialButton>(R.id.cancel_btn)
        val submitButton = view.findViewById<MaterialButton>(R.id.submit_button)
        val spinner = view.findViewById<Spinner>(R.id.select_product_spinner)

        val notAddedItems = (activity as MainActivity).productViewModel.notAddedProduct!!
        val notAddedLabels = mutableListOf<String>()
        for(item in notAddedItems){
            notAddedLabels.add(item.name)
        }

        val spinnerAdapter = ArrayAdapter(activity as MainActivity,android.R.layout.simple_spinner_item, notAddedLabels)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        cancelButton.setOnClickListener{
            if (spinner.selectedItem != null){
                val product = Product(spinner.selectedItem.toString(),true)
                (activity as MainActivity).productViewModel.delete(product)
                (activity as MainActivity).productAdapter.lastAction = MoltenAdapter.NO_ACTION
                dismiss()
            }else{
                Toast.makeText(requireActivity(), "There are no more products", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }

        submitButton.setOnClickListener {
            if (spinner.selectedItem != null){
                val product = Product(spinner.selectedItem.toString(),true)
                (activity as MainActivity).productViewModel.insert(product)
                (activity as MainActivity).productAdapter.lastAction = MoltenAdapter.INSERTED
                dismiss()
            }else{
                Toast.makeText(requireActivity(), "All products selected", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }
}