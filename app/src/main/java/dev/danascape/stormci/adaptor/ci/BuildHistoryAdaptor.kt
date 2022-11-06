package dev.danascape.stormci.adaptor.ci

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.danascape.stormci.R
import dev.danascape.stormci.model.ci.BuildHistoryList

class BuildHistoryAdaptor(private val context: Context, private val mBuildHistory: MutableList<BuildHistoryList>, private val mRowLayout: Int) : RecyclerView.Adapter<BuildHistoryAdaptor.BuildHistoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildHistoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(mRowLayout, parent, false)
        return BuildHistoryHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BuildHistoryHolder, position: Int) {
        holder.positionNumber.text = "Member: ${position + 1}"
        holder.name.text = mBuildHistory[position].status
    }

    override fun getItemCount(): Int {
        return mBuildHistory.size
    }

    class BuildHistoryHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {
        val positionNumber: TextView = itemView.findViewById<View>(R.id.tvPositionNumber) as TextView
        val name: TextView = itemView.findViewById<View>(R.id.tvName) as TextView
    }
}