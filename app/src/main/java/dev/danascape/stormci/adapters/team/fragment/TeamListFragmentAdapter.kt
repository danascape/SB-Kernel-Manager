package dev.danascape.stormci.adapters.team.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.danascape.stormci.databinding.FragmentTeamItemBinding
import dev.danascape.stormci.models.team.Team

class TeamListFragmentAdapter : RecyclerView.Adapter<TeamListFragmentAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding =
            FragmentTeamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class TeamViewHolder(val binding: FragmentTeamItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(team: Team) {
            binding.tvName.text = team.name
            binding.tvTitle.text = team.title
            binding.tvGithub.text = team.github ?: "Github"
            binding.tvLinkedin.text = team.linkedin ?: "LinkedIn"
            val url = team.image
            if (url != null)
                Picasso.get()
                    .load(url)
                    .into(binding.imgProfile)
        }
    }


    val diffUtilCallback = object : DiffUtil.ItemCallback<Team>() {
        override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem.name == newItem.name
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtilCallback)
}