package dev.danascape.stormci.ui.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.danascape.stormci.R
import dev.danascape.stormci.databinding.DevicesItemBinding
import dev.danascape.stormci.models.Device

class DeviceListAdapter(val context: Context) :
    RecyclerView.Adapter<DeviceListAdapter.DeviceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val binding = DevicesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeviceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(differ.currentList[position], context)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class DeviceViewHolder(private val binding: DevicesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(device: Device, context: Context) {
            val intentDownload = Intent(Intent.ACTION_VIEW, Uri.parse(device.link))

            binding.tvName.text = device.name
            binding.tvMaintainer.text =
                context.getString(R.string.maintainer) + ": ${device.maintainer}"
            binding.tvDate.text = context.getString(R.string.release_date) + ":  ${device.date}"
            binding.tvVersion.text =
                context.getString(R.string.latest_version) + ": ${device.version}"
            if (!device.image.isNullOrBlank()) {
                Picasso.get()
                    .load(device.image)
                    .into(binding.imgProfile)
            }

            binding.cvDeviceView.setOnClickListener {
                context.startActivity(intentDownload)
            }
        }
    }

    fun setFilteredList(list: ArrayList<Device>) {
        val newListDiffer = AsyncListDiffer(this, diffUtilCallback)
        newListDiffer.submitList(list)
        differ = newListDiffer
        notifyDataSetChanged()
    }

    private val diffUtilCallback = object : DiffUtil.ItemCallback<Device>() {
        override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
            return oldItem == newItem
        }

    }
    var differ = AsyncListDiffer(this, diffUtilCallback)
}