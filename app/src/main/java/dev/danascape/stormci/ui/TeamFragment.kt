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
import dev.danascape.stormci.adaptor.team.CoreTeamListAdaptor
import dev.danascape.stormci.adaptor.team.MaintainerListAdaptor
import dev.danascape.stormci.api.client.GithubAPIClient
import dev.danascape.stormci.api.team.CoreTeamService
import dev.danascape.stormci.api.team.MaintainerService
import dev.danascape.stormci.databinding.FragmentTeamBinding
import dev.danascape.stormci.model.team.CoreTeam
import dev.danascape.stormci.model.team.CoreTeamList
import dev.danascape.stormci.model.team.Maintainer
import dev.danascape.stormci.model.team.MaintainerList
import dev.danascape.stormci.ui.team.CoreTeamFragment
import dev.danascape.stormci.ui.team.MaintainerFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamFragment : Fragment(R.layout.fragment_team) {
    private var _binding: FragmentTeamBinding? = null
    private val binding
    get() = _binding!!

    private var mApiService: CoreTeamService? = null
    private var mApiMaintainerService: MaintainerService? = null

    private var mAdapter: CoreTeamListAdaptor?= null
    private var mMaintainerAdaptor: MaintainerListAdaptor? = null

    private var mCoreTeam: MutableList<CoreTeam> = ArrayList()
    private var mMaintainer: MutableList<Maintainer> = ArrayList()

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
        mAdapter = activity?.let { CoreTeamListAdaptor(it, mCoreTeam, R.layout.core_team_item) }
        recyclerView.adapter = mAdapter

        mApiService = GithubAPIClient.client.create(CoreTeamService::class.java)
        fetchCoreTeamList()

        MaintainerView = requireView().findViewById(R.id.rvMaintainer)
//        MaintainerView.layoutManager = layoutManager
        MaintainerView.setLayoutManager(LinearLayoutManager(getActivity()))
        mMaintainerAdaptor = activity?.let { MaintainerListAdaptor(it,mMaintainer, R.layout.core_team_item) }
        MaintainerView.adapter = mMaintainerAdaptor

        mApiMaintainerService = GithubAPIClient.client.create(MaintainerService::class.java)
        fetchMaintainerList()
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