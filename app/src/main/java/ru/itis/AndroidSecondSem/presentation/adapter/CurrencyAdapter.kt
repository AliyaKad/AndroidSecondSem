package ru.itis.AndroidSecondSem.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CurrencyAdapter(private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    private var currencies = listOf<String>()

    fun submitList(newCurrencies: List<String>) {
        currencies = newCurrencies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencies[position]
        holder.bind(currency)
        holder.itemView.setOnClickListener {
            onItemClick(currency)
        }
    }

    override fun getItemCount(): Int = currencies.size

    class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(currency: String) {
            (itemView as TextView).text = currency
        }
    }
}