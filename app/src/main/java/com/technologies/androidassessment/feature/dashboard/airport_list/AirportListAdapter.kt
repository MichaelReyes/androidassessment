package com.technologies.androidassessment.feature.dashboard.airport_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.technologies.androidassessment.R
import com.technologies.androidassessment.core.data.entity.Airport
import com.technologies.androidassessment.core.utils.AutoUpdatableAdapter
import com.technologies.androidassessment.databinding.ItemAirpotBinding
import javax.inject.Inject
import kotlin.properties.Delegates

class AirportListAdapter @Inject constructor() :
    RecyclerView.Adapter<AirportListAdapter.Holder>(),
    AutoUpdatableAdapter {

    internal var collection: List<Airport> by Delegates.observable(emptyList()) { prop, old, new ->
        autoNotify(old, new) { o, n -> o.airportCode == n.airportCode }
    }

    internal var clickListener: (Airport) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder.from(
            parent,
            R.layout.item_airpot
        )

    override fun getItemCount() = collection.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.apply {
            val airport = collection[position]
            item = airport
            holder.itemView.setOnClickListener {
                clickListener.invoke(airport)
            }
            executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class Holder(val binding: ItemAirpotBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup, layout: Int): Holder {
                val inflater = LayoutInflater.from(parent.context)
                val binding =
                    DataBindingUtil.inflate<ItemAirpotBinding>(
                        inflater,
                        layout,
                        parent,
                        false
                    )
                return Holder(
                    binding
                )
            }
        }
    }
}
