package com.edumalek.mammothinspector

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.edumalek.mammothinspector.databinding.RateItemBinding
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface BankApi {
    @GET("./p24api/exchange_rates?json&date=19.09.2022")
    fun getExchangeRate(): Single<Response>
}

class RateAdapter : ListAdapter<ExchangeRate, RateViewHolder>(RateDiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        return RateViewHolder(
            RateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class RateViewHolder(private val binding: RateItemBinding) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(rate: ExchangeRate) {
        binding.rateItemText.text =
            "${rate.currency}: ${
                String.format(
                    "%.2f",
                    rate.purchaseRateNB
                )
            } / ${String.format("%.2f", rate.saleRateNB)}"
    }
}


class RateDiffUtilCallBack : DiffUtil.ItemCallback<ExchangeRate>() {
    override fun areItemsTheSame(oldItem: ExchangeRate, newItem: ExchangeRate): Boolean {
        return oldItem.currency == newItem.currency
    }

    override fun areContentsTheSame(oldItem: ExchangeRate, newItem: ExchangeRate): Boolean {
        return oldItem == newItem
    }

}

data class Response(val exchangeRate: List<ExchangeRate>)
data class ExchangeRate(
    val baseCurrency: String,
    val currency: String,
    val saleRateNB: Float,
    val purchaseRateNB: Float
)
