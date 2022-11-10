package dev.danascape.stormci.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.danascape.stormci.R
import dev.danascape.stormci.adaptor.team.TeamListAdaptor
import dev.danascape.stormci.api.client.GithubAPIClient
import dev.danascape.stormci.api.team.TeamService
import dev.danascape.stormci.databinding.FragmentTeamBinding
import dev.danascape.stormci.model.team.Team
import dev.danascape.stormci.model.team.TeamList
import dev.danascape.stormci.ui.team.CoreTeamFragment
import dev.danascape.stormci.ui.team.MaintainerFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamFragment : Fragment(R.layout.fragment_team) {
    private var _binding: FragmentTeamBinding? = null
    private val binding
    get() = _binding!!

    private var mApiService: TeamService? = null
//    private var mApiMaintainerService: MaintainerService? = null

    private var mAdapter: TeamListAdaptor?= null
    private var mMaintainerAdaptor: TeamListAdaptor? = null

    private var mTeam: MutableList<Team> = ArrayList()
//    private var mMaintainer: MutableList<Team> = ArrayList()

    private lateinit var recyclerView: RecyclerView
    private lateinit var MaintainerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCoreTeam.setOnClickListener {
            val CoreTeamFragment = CoreTeamFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.flFragment, CoreTeamFragment)
            transaction.commit()
        }

        binding.btnMaintainer.setOnClickListener {
            val MaintainerFragment = MaintainerFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.flFragment, MaintainerFragment)
            transaction.commit()
        }

        val layoutManager = LinearLayoutManager(context)
        recyclerView = requireView().findViewById(R.id.rvCoreTeam)
        recyclerView.layoutManager = layoutManager
        mAdapter = activity?.let { TeamListAdaptor(it, mTeam, R.layout.core_team_item) }
        recyclerView.adapter = mAdapter

        MaintainerView = requireView().findViewById(R.id.rvMaintainer)
//        MaintainerView.layoutManager = layoutManager
        MaintainerView.setLayoutManager(LinearLayoutManager(getActivity()))
        mMaintainerAdaptor = activity?.let { TeamListAdaptor(it,mTeam, R.layout.core_team_item) }
        MaintainerView.adapter = mMaintainerAdaptor

        mApiService = GithubAPIClient.client.create(TeamService::class.java)

        fetchCoreTeamList()
        fetchMaintainerList()
    }

    private fun fetchCoreTeamList() {
        val call = mApiService!!.fetchCoreTeam()

        call.enqueue(object : Callback<TeamList> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<TeamList>, response: Response<TeamList>) {
                Log.d("StormCI", "Total Members Fetched: " + response.body()!!.members!!.size)
                val Response = response.body()
                if (Response != null) {
                    mTeam.addAll(Response.members!!)
                    mAdapter!!.notifyDataSetChanged()
                    mTeam = ArrayList<Team>()
                }
            }
            override fun onFailure(call: Call<TeamList>, t: Throwable) {
                Log.d("StormCI", "Failed to download JSON")
            }
        })
    }

    private fun fetchMaintainerList() {
        val call = mApiService!!.fetchMaintainer()

        call.enqueue(object : Callback<TeamList> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<TeamList>,
                response: Response<TeamList>
            ) {
                Log.d("StormCI", "Total Members Fetched: " + response.body()!!.members!!.size)
                val Response = response.body()
                if (Response != null) {
                    mTeam.addAll(Response.members!!)
                    mMaintainerAdaptor!!.notifyDataSetChanged()
                    mTeam = ArrayList<Team>()
                }
            }
            override fun onFailure(call: Call<TeamList>, t: Throwable) {
                Log.d("StormCI", "Failed to download JSON")
            }
        })
    }
}