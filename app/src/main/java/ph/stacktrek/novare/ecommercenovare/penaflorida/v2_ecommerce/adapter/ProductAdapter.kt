package ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.databinding.ProductItemBinding
import ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.entity.ProductEntity

class ProductAdapter(private val context: Context, private val productList: ArrayList<ProductEntity>):RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun deleteProduct(position:Int){
        productList.removeAt(position)
        notifyItemRemoved(position)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.binding.productName.text = product.productName
        holder.binding.description.text = product.description
        holder.binding.price.text = product.price.toString()
    }

    override fun getItemCount(): Int{
        return productList.size
    }

    fun getProductAt(position: Int): Any {
        return productList[position]
    }


}





