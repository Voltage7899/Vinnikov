package com.example.vinnikov

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vinnikov.databinding.ElementBinding
import com.squareup.picasso.Picasso

class AdapterCars(val clickListener: ClickListener) : RecyclerView.Adapter<AdapterCars.ViewHolder>() {

    private var ListInAdapter = ArrayList<Car>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCars.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterCars.ViewHolder, position: Int) {
        holder.bind(ListInAdapter[position], clickListener)
    }

    override fun getItemCount(): Int {
        return ListInAdapter.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ElementBinding.bind(itemView)
        fun bind(car: Car, clickListener: ClickListener) {
            binding.elName.text = car.name

            Picasso.get().load(car.link).fit().into(binding.elImage)

            itemView.setOnClickListener {

                clickListener.onClick(car)
            }

        }
    }

    fun loadListToAdapter(productList: ArrayList<Car>) {
        ListInAdapter = productList
        notifyDataSetChanged()
    }

    interface ClickListener {
        fun onClick(car: Car) {

        }
    }

    fun deleteItem(i: Int): String? {
        var id = ListInAdapter.get(i).id

        ListInAdapter.removeAt(i)

        notifyDataSetChanged()

        return id
    }
}