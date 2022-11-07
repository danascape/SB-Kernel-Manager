package dev.danascape.stormci.adaptor.team.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.danascape.stormci.R
import dev.danascape.stormci.model.team.Maintainer

class MaintainerListFragmentAdaptor(private val context: Context, private val mMaintainer: MutableList<Maintainer>, private val mRowLayout: Int) : RecyclerView.Adapter<MaintainerListFragmentAdaptor.MaintainerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaintainerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mRowLayout, parent, false)
        return MaintainerViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MaintainerViewHolder, position: Int) {
        val url = mMaintainer[position].image
        if(url == null) {
        } else {
            Picasso.get()
                .load(url)
                .into(holder.image)
        }
        holder.positionNumber.text = "Member: ${position + 1}"
        holder.name.text = mMaintainer[position].name
        holder.title.text = mMaintainer[position].title
    }

    override fun getItemCount(): Int {
        return mMaintainer.size
    }

    class MaintainerViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {
        val positionNumber: TextView = itemView.findViewById<View>(R.id.tvPositionNumber) as TextView
        val name: TextView = itemView.findViewById<View>(R.id.tvName) as TextView
        val title: TextView = itemView.findViewById<View>(R.id.tvTitle) as TextView
        val image: ImageView = itemView.findViewById<ImageView>(R.id.imgProfile) as ImageView
    }
}