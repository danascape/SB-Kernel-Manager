package dev.danascape.stormci.ui.team

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.danascape.stormci.R
import dev.danascape.stormci.adaptor.team.CoreTeamListAdaptor
import dev.danascape.stormci.api.client.GithubAPIClient
import dev.danascape.stormci.api.team.CoreTeamService
import dev.danascape.stormci.model.team.CoreTeam
import dev.danascape.stormci.model.team.CoreTeamList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoreTeamFragment : Fragment(R.layout.fragment_core_team) {

    private var mApiService: CoreTeamService? = null
    private var mAdapter: CoreTeamListAdaptor?= null;
    private var mCoreTeam: MutableList<CoreTeam> = ArrayList()

    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridManager = GridLayoutManager(context, 2)
        recyclerView = requireView().findViewById(R.id.rvCoreTeam)
        recyclerView.layoutManager = gridManager
        mAdapter = activity?.let { CoreTeamListAdaptor(it, mCoreTeam, R.layout.core_team_item) }
        recyclerView.adapter = mAdapter

        mApiService = GithubAPIClient.client.create(CoreTeamService::class.java)
        fetchCoreTeamList()
    }

    private fun fetchCoreTeamList() {
        val call = mApiService!!.fetchCoreTeam()

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