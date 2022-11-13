package dev.danascape.stormci.ui.fragments.team

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.danascape.stormci.R
import dev.danascape.stormci.adapters.team.fragment.TeamListFragmentAdapter
import dev.danascape.stormci.api.GithubAPI
import dev.danascape.stormci.api.team.TeamService
import dev.danascape.stormci.models.team.Team
import dev.danascape.stormci.models.team.TeamList
import dev.danascape.stormci.util.Constants.Companion.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MaintainerFragment : Fragment(R.layout.fragment_maintainer) {

    private var mApiService: TeamService? = null
    private var mAdapter: TeamListFragmentAdapter?= null;
    private var mCoreTeam: MutableList<Team> = ArrayList()

    private lateinit var MaintainerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridManager = GridLayoutManager(context, 2)
        MaintainerView = requireView().findViewById(R.id.rvMaintainer)
        MaintainerView.layoutManager = gridManager
        mAdapter = activity?.let { TeamListFragmentAdapter(it, mCoreTeam, R.layout.fragment_team_item) }
        MaintainerView.adapter = mAdapter

        mApiService = GithubAPI.client.create(TeamService::class.java)
        fetchCoreTeamList()
    }

    private fun fetchCoreTeamList() {
        val call = mApiService!!.fetchMaintainer()

        call.enqueue(object : Callback<TeamList> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<TeamList>, response: Response<TeamList>) {
                Log.d(TAG, "Total Members Fetched: " + response.body()!!.members!!.size)
                val Response = response.body()
                if (Response != null) {
                    mCoreTeam.addAll(Response.members!!)
                    mAdapter!!.notifyDataSetChanged()
                    mCoreTeam=ArrayList<Team>()
                }
            }
            override fun onFailure(call: Call<TeamList>, t: Throwable) {
                Log.d(TAG, "Failed to download JSON")
            }
        })
    }
}