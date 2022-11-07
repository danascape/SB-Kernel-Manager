package dev.danascape.stormci.ui.team

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.danascape.stormci.R
import dev.danascape.stormci.adaptor.team.fragment.TeamListFragmentAdaptor
import dev.danascape.stormci.api.client.GithubAPIClient
import dev.danascape.stormci.api.team.CoreTeamService
import dev.danascape.stormci.model.team.CoreTeam
import dev.danascape.stormci.model.team.CoreTeamList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MaintainerFragment : Fragment(R.layout.fragment_maintainer) {

    private var mApiService: CoreTeamService? = null
    private var mAdapter: TeamListFragmentAdaptor?= null;
    private var mCoreTeam: MutableList<CoreTeam> = ArrayList()

    private lateinit var MaintainerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridManager = GridLayoutManager(context, 2)
        MaintainerView = requireView().findViewById(R.id.rvMaintainer)
        MaintainerView.layoutManager = gridManager
        mAdapter = activity?.let { TeamListFragmentAdaptor(it, mCoreTeam, R.layout.fragment_team_item) }
        MaintainerView.adapter = mAdapter

        mApiService = GithubAPIClient.client.create(CoreTeamService::class.java)
        fetchCoreTeamList()
    }

    private fun fetchCoreTeamList() {
        val call = mApiService!!.fetchMaintainer()

        call.enqueue(object : Callback<CoreTeamList> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<CoreTeamList>, response: Response<CoreTeamList>) {
                Log.d("StormCI", "Total Members Fetched: " + response.body()!!.members!!.size)
                val Response = response.body()
                if (Response != null) {
                    mCoreTeam.addAll(Response.members!!)
                    mAdapter!!.notifyDataSetChanged()
                    mCoreTeam=ArrayList<CoreTeam>()
                }
            }
            override fun onFailure(call: Call<CoreTeamList>, t: Throwable) {
                Log.d("StormCI", "Failed to download JSON")
            }
        })
    }
}