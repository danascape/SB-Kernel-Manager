package dev.danascape.stormci.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.CornerFamily
import com.squareup.picasso.Picasso
import dev.danascape.stormci.databinding.FragmentTeamItemBinding
import dev.danascape.stormci.models.Team


class TeamListAdapter(val context: Context) : RecyclerView.Adapter<TeamListAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding =
            FragmentTeamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(differ.currentList[position], context)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class TeamViewHolder(val binding: FragmentTeamItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(team: Team, context: Context) {
            val intentGithub = Intent(Intent.ACTION_VIEW, Uri.parse(team.github))
            val intentLinkedin = Intent(Intent.ACTION_VIEW, Uri.parse(team.linkedin))

            binding.tvName.text = team.name
            binding.tvTitle.text = team.title

            binding.tvGithub.setOnClickListener {
                context.startActivity(intentGithub)
            }

            binding.tvLinkedin.setOnClickListener {
                context.startActivity(intentLinkedin)
            }

//            binding.tvGithub.text = team.github ?: "Github"
//            binding.tvLinkedin.text = team.linkedin ?: "LinkedIn"
            val url = team.image
            if (url != null)
                Picasso.get()
                    .load(url)
                    .resize(90,90)
                    .into(binding.imgProfile)

            val radius = 100.0F
            binding.imgProfile.shapeAppearanceModel = binding.imgProfile.shapeAppearanceModel
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED, radius)
                .build()
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