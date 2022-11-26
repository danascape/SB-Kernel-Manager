package dev.danascape.stormci.adapters.device

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.danascape.stormci.databinding.DevicesItemBinding
import dev.danascape.stormci.models.device.Device

class DeviceListAdapter() : RecyclerView.Adapter<DeviceListAdapter.DeviceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val binding = DevicesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeviceViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(differ.currentList[position])

//        holder.containerView.setOnClickListener {
//            Toast.makeText(context, "Link to download the kernel", Toast.LENGTH_SHORT).show();
//        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class DeviceViewHolder(val binding: DevicesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(device: Device) {
            binding.tvName.text = device.name
            binding.tvMaintainer.text = device.maintainer
            if (!device.image.isNullOrBlank()) {
                Picasso.get()
                    .load(device.image)
                    .into(binding.imgProfile)
            }
        }
    }

    private val diffUtilCallback = object : DiffUtil.ItemCallback<Device>() {
        override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtilCallback)
}