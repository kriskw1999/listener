package com.example.spoppinglist.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.spoppinglist.MainActivity
import com.example.spoppinglist.R
import com.example.spoppinglist.data.product.Product
import com.example.spoppinglist.recyclerview.MoltenAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class BottomSheetNewProduct: BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.custom_bottom_sheet_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cancelButton = view.findViewById<MaterialButton>(R.id.cancel_btn)
        val submitButton = view.findViewById<MaterialButton>(R.id.submit_button)
        val nameInputText = view.findViewById<TextInputEditText>(R.id.product_name_input_text)

        cancelButton.setOnClickListener{
            dismiss()
        }

        submitButton.setOnClickListener {
            if (nameInputText.text.toString()!=""){
                val product = Product(nameInputText.text.toString(), true)
                (activity as MainActivity).productViewModel.insert(product)
                (activity as MainActivity).productAdapter.lastAction = MoltenAdapter.INSERTED
                dismiss()
            }else{
                Toast.makeText(requireContext(),"Please fill empty spaces.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}