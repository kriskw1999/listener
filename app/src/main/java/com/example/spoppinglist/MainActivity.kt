package com.example.spoppinglist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spoppinglist.bottomsheets.BottomSheetAddProduct
import com.example.spoppinglist.bottomsheets.BottomSheetNewProduct
import com.example.spoppinglist.data.product.ProductViewModel
import com.example.spoppinglist.recyclerview.MoltenAdapter
import com.example.spoppinglist.recyclerview.ProductAdapter
import com.example.spoppinglist.recyclerview.ProductViewHolder
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView

class MainActivity : AppCompatActivity() {

    private val floatingButton by lazy { findViewById<SpeedDialView>(R.id.floating_button) } //Floating button
    private val shoppingList by lazy { findViewById<RecyclerView>(R.id.shopping_list) } //Products RecyclerView
    private val toolBar by lazy { findViewById<androidx.appcompat.widget.Toolbar>(R.id.tool_bar) } //Toolbar
    private var addedAdapter: Boolean = false //Adapter added
    private lateinit var simpleCallback: ItemTouchHelper.SimpleCallback //SimpleCallback recyclerView
    lateinit var productAdapter: ProductAdapter //Product Adapter
    val productViewModel by viewModels<ProductViewModel>() //Product ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolBar)
        configFloatingButton()
        setUpRecyclerView()
    }

    private fun configFloatingButton() {
        var addNewItemIcon = SpeedDialActionItem.Builder(R.id.add_product, R.drawable.shop_icon)
        addNewItemIcon.setLabel("Add product")
        addNewItemIcon.setFabBackgroundColor(
            ResourcesCompat.getColor(
                resources,
                R.color.light_blue,
                theme
            )
        )
        addNewItemIcon.setFabImageTintColor(
            ResourcesCompat.getColor(
                resources,
                R.color.white,
                theme
            )
        )
        floatingButton.addActionItem(addNewItemIcon.create())

        addNewItemIcon = SpeedDialActionItem.Builder(R.id.new_product, R.drawable.new_product_icon)
        addNewItemIcon.setLabel("New product")
        addNewItemIcon.setFabBackgroundColor(
            ResourcesCompat.getColor(
                resources,
                R.color.light_blue,
                theme
            )
        )
        addNewItemIcon.setFabImageTintColor(
            ResourcesCompat.getColor(
                resources,
                R.color.white,
                theme
            )
        )
        floatingButton.addActionItem(addNewItemIcon.create())

        floatingButton.setOnActionSelectedListener { item ->
            when (item.id) {
                R.id.new_product -> {
                    val bottomSheet = BottomSheetNewProduct()
                    bottomSheet.show(supportFragmentManager, ("TAG"))
                    floatingButton.close()
                    true
                }
                R.id.add_product -> {
                    productViewModel.getNotAddedProducts()
                    val bottomSheet = BottomSheetAddProduct()
                    bottomSheet.show(supportFragmentManager, "TAG")
                    floatingButton.close()
                    true
                }
                else -> false
            }
        }
    }

    private fun setUpRecyclerView() {

        shoppingList.layoutManager = LinearLayoutManager(this)

        productViewModel.savingList.observe(this) { items ->

            //Adapter configuration
            if(!addedAdapter) {
                productAdapter = ProductAdapter(items, this)
                shoppingList.adapter = productAdapter
                addedAdapter = true
            }else{
                productAdapter.data = items
                productAdapter.update()
            }

        }

        //Swipe delete configuration
        swipeControllerSet()
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(shoppingList)


    }

    private fun swipeControllerSet() {

        simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val productViewHolder = viewHolder as ProductViewHolder
                val notToBuyProduct = productViewHolder.product
                notToBuyProduct!!.toBuy = false
                productViewModel.update(notToBuyProduct)
                productAdapter.lastAction = MoltenAdapter.DELETED
                productAdapter.changedElement = productViewHolder.adapterPosition +1
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.tool_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.remove_all->{
                val items = productAdapter.data
                for(i in items){
                    i.toBuy = false
                    productViewModel.update(i)
                }
                productAdapter.lastAction = MoltenAdapter.DATA_SET_CHANGED
                productAdapter.update()
                true
            }
            else -> false
        }
    }


}

