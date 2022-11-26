package dev.danascape.stormci.adapters.ci

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.danascape.stormci.databinding.BuildHistoryItemBinding
import dev.danascape.stormci.models.ci.BuildHistory
import java.util.concurrent.TimeUnit

class BuildHistoryAdapter : RecyclerView.Adapter<BuildHistoryAdapter.BuildHistoryHolder>() {

    inner class BuildHistoryHolder(val binding: BuildHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(buildHistory: BuildHistory, position: Int) {
            fun timeTaken(): String {
                val totalSecondsTaken = buildHistory.finished - buildHistory.started
                val minutesTaken = TimeUnit.SECONDS.toMinutes(totalSecondsTaken.toLong())
                val secondsTaken =
                    TimeUnit.SECONDS.toSeconds(totalSecondsTaken.toLong()) - TimeUnit.MINUTES.toSeconds(
                        minutesTaken
                    )
                return "Build Took: $minutesTaken and $secondsTaken"
            }
            try {
                binding.tvbuildNumber.text = "${position + 1}"
                binding.tvName.text = "Name: ${buildHistory.params?.device ?: "Not Found"}"
                binding.tvBranch.text = "Branch: ${buildHistory.params?.branch ?: "Not Found"}"
                binding.tvAuthor.text = "Triggered By: ${buildHistory.author_name}"
                binding.tvStatus.text = "Build Status: ${buildHistory.status}"
                binding.tvBuildTime.text = timeTaken()

            } catch (e: Exception) {
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildHistoryHolder {
        val binding =
            BuildHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuildHistoryHolder(binding)
    }

    override fun onBindViewHolder(holder: BuildHistoryHolder, position: Int) {
        holder.bind(differ.currentList[position], position)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffutilCallback = object : DiffUtil.ItemCallback<BuildHistory>() {
        override fun areItemsTheSame(oldItem: BuildHistory, newItem: BuildHistory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BuildHistory, newItem: BuildHistory): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffutilCallback)

}