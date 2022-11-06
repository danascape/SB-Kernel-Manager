package dev.danascape.stormci.adaptor.device

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import dev.danascape.stormci.R
import dev.danascape.stormci.model.device.Devices

class DevicesListAdaptor(private val context: Context, private val mDevices: MutableList<Devices>, private val mRowLayout: Int) : RecyclerView.Adapter<DevicesListAdaptor.DeviceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mRowLayout, parent, false)
        return DeviceViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.positionNumber.text = "Device: ${position + 1}"
        holder.title.text = mDevices[position].name
        holder.link.text = "Maintainer: ${mDevices[position].maintainer}"

        holder.containerView.setOnClickListener {
            Toast.makeText(context, "Link to download the kernel", Toast.LENGTH_SHORT).show();
        }
    }

    override fun getItemCount(): Int {
        return mDevices.size
    }

    class DeviceViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {
        val positionNumber: TextView = itemView.findViewById<View>(R.id.tvPositionNumber) as TextView
        val title: TextView = itemView.findViewById<View>(R.id.tvName) as TextView
        val link: TextView = itemView.findViewById<View>(R.id.tvMaintainer) as TextView
    }
}