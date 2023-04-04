package ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.adapter.ProductAdapter
import ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.database.AppDatabase
import ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.databinding.ActivityMainBinding
import ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.databinding.DialogueAddProductBinding
import ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.entity.ProductEntity

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var db:AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "my_db").build()

        binding.fabAddProductButton.setOnClickListener{
            showAddProductDialog()
        }
        loadProducts()
    }

    fun loadProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            val productList = db.productDao().getAllProducts()
            withContext(Dispatchers.Main) {
                val adapter = ProductAdapter(productList)
                binding.productsListRecycler.adapter = adapter
                binding.productsListRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    private fun showAddProductDialog() {
        val activity = this@MainActivity
        val dialogueAddProductBinding = DialogueAddProductBinding.inflate(activity.layoutInflater)
        val builder = AlertDialog.Builder(activity).apply {
            setView(dialogueAddProductBinding.root)
            setPositiveButton("ADD") { _, _ ->
                val productName = dialogueAddProductBinding.productName.text.toString()
                val description = dialogueAddProductBinding.productDescription.text.toString()
                val priceStr = dialogueAddProductBinding.productPrice.text.toString()
                val price = priceStr.toDoubleOrNull()
                if (productName.isBlank() || description.isBlank() || price == null) {
                    Toast.makeText(activity, "Values cannot be empty!", Toast.LENGTH_SHORT).show()
                } else {
                    val product = ProductEntity(
                        productName = productName,
                        description = description,
                        price = price
                    )
                    insertProduct(product)
                }
            }
            setNegativeButton("CANCEL", null)
        }
        builder.show()
    }

    private fun insertProduct(product: ProductEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            db.productDao().insert(product)
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@MainActivity,
                    "Added the Product successfully",
                    Toast.LENGTH_SHORT
                ).show()
                loadProducts()
            }
        }
    }
}