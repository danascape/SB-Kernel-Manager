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
import dev.danascape.stormci.model.ci.Params

class BuildHistoryAdaptor(private val context: Context, private val mBuildHistory: MutableList<BuildHistoryList>, private val mRowLayout: Int) : RecyclerView.Adapter<BuildHistoryAdaptor.BuildHistoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildHistoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(mRowLayout, parent, false)
        return BuildHistoryHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BuildHistoryHolder, position: Int) {
        holder.buildNumber.text = "${position + 1}"
        holder.name.text = "Name: ${mBuildHistory[position].params!!.device!!}"
        holder.branch.text = "Branch: ${mBuildHistory[position].params!!.branch!!}"
        holder.author.text = "Triggered by ${mBuildHistory[position]!!.author_name}"
        holder.buildTime.text = "Build took: ${mBuildHistory[position].finished - mBuildHistory[position].started} seconds"
        holder.status.text = "Status: ${mBuildHistory[position].status}"
    }

    override fun getItemCount(): Int {
        return mBuildHistory.size
    }

    class BuildHistoryHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {
        val buildNumber: TextView = itemView.findViewById<View>(R.id.tvbuildNumber) as TextView
        val name: TextView = itemView.findViewById<View>(R.id.tvName) as TextView
        val branch: TextView = itemView.findViewById<View>(R.id.tvBranch) as TextView
        val author: TextView = itemView.findViewById<View>(R.id.tvAuthor) as TextView
        val buildTime: TextView = itemView.findViewById<View>(R.id.tvBuildTime) as TextView
        val status: TextView = itemView.findViewById<View>(R.id.tvStatus) as TextView
    }
}