package dev.danascape.stormci.ui.team

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.danascape.stormci.R
import dev.danascape.stormci.adaptor.team.MaintainerListAdaptor
import dev.danascape.stormci.api.client.GithubAPIClient
import dev.danascape.stormci.api.team.MaintainerService
import dev.danascape.stormci.model.team.Maintainer
import dev.danascape.stormci.model.team.MaintainerList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MaintainerFragment : Fragment(R.layout.fragment_maintainer) {

    private var mApiMaintainerService: MaintainerService? = null
    private var mMaintainerAdaptor: MaintainerListAdaptor? = null
    private var mMaintainer: MutableList<Maintainer> = ArrayList()

    private lateinit var MaintainerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridManager = GridLayoutManager(context, 2)
        MaintainerView = requireView().findViewById(R.id.rvMaintainer)
        MaintainerView.layoutManager = gridManager
        mMaintainerAdaptor = activity?.let { MaintainerListAdaptor(it,mMaintainer, R.layout.core_team_item) }
        MaintainerView.adapter = mMaintainerAdaptor

        mApiMaintainerService = GithubAPIClient.client.create(MaintainerService::class.java)
        fetchMaintainerList()
    }

    private fun fetchMaintainerList() {
        val call = mApiMaintainerService!!.fetchMaintainer()

        call.enqueue(object : Callback<MaintainerList> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<MaintainerList>,
                response: Response<MaintainerList>
            ) {
                Log.d("StormCI", "Total Members Fetched: " + response.body()!!.members!!.size)
                val Response = response.body()
                if (Response != null) {
                    mMaintainer.addAll(Response.members!!)
                    mMaintainerAdaptor!!.notifyDataSetChanged()
                    mMaintainer = ArrayList<Maintainer>()
                }
            }
            override fun onFailure(call: Call<MaintainerList>, t: Throwable) {
                Log.d("StormCI", "Failed to download JSON")
            }
        })
    }
}