package dev.danascape.stormci.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import dev.danascape.stormci.R
import dev.danascape.stormci.adapters.team.TeamListAdapter
import dev.danascape.stormci.api.GithubAPI
import dev.danascape.stormci.api.team.TeamService
import dev.danascape.stormci.databinding.FragmentTeamBinding
import dev.danascape.stormci.models.team.Team
import dev.danascape.stormci.models.team.TeamList
import dev.danascape.stormci.ui.fragments.team.CoreTeamFragment
import dev.danascape.stormci.ui.fragments.team.MaintainerFragment
import dev.danascape.stormci.util.Constants.Companion.TAG
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class TeamFragment : Fragment(R.layout.fragment_team) {
    private var _binding: FragmentTeamBinding? = null
    private val binding
        get() = _binding!!

    private var mApiService: TeamService? = null

    private var mAdapter: TeamListAdapter? = null
    private var mMaintainerAdaptor: TeamListAdapter? = null

    private var mTeam: MutableList<Team> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamBinding.inflate(inflater, container, false)
        mAdapter = TeamListAdapter(requireActivity(), mTeam)
        binding.rvCoreTeam.layoutManager = LinearLayoutManager(activity)
        binding.rvCoreTeam.setHasFixedSize(true)
        binding.rvCoreTeam.adapter = mAdapter

        mMaintainerAdaptor = TeamListAdapter(requireActivity(), mTeam)
        binding.rvMaintainer.layoutManager = LinearLayoutManager(activity)
        binding.rvMaintainer.setHasFixedSize(true)
        binding.rvMaintainer.adapter = mMaintainerAdaptor

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCoreTeam.setOnClickListener {
            val CoreTeamFragment = CoreTeamFragment()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, CoreTeamFragment)
            transaction.commit()
        }

        binding.btnMaintainer.setOnClickListener {
            val MaintainerFragment = MaintainerFragment()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, MaintainerFragment)
            transaction.commit()
        }

        mApiService = GithubAPI.client.create(TeamService::class.java)

        fetchCoreTeamList()
        fetchMaintainerList()
    }

    private fun fetchCoreTeamList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = mApiService!!.fetchCoreTeam()
            withContext(Dispatchers.Main) {
                try {
                    if(response.isSuccessful) {
                        mTeam.addAll(response.body()!!.members!!)
                        binding.rvCoreTeam.layoutManager = LinearLayoutManager(activity)
                        binding.rvCoreTeam.setHasFixedSize(true)
                        val adapter = TeamListAdapter(requireActivity(), mTeam)
                        binding.rvCoreTeam.adapter = adapter
                        mTeam = ArrayList<Team>()
                    } else {
                        Log.d(TAG, "Failed to fetch Team List")
                    }
                } catch (e: Throwable) {
                    Log.d(TAG, "Something else went wrong")
                }
            }

        }
    }

    private fun fetchMaintainerList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = mApiService!!.fetchMaintainer()
            withContext(Dispatchers.Main) {
                try {
                    if(response.isSuccessful) {
                        mTeam.addAll(response.body()!!.members!!)
                        binding.rvMaintainer.layoutManager = LinearLayoutManager(activity)
                        binding.rvMaintainer.setHasFixedSize(true)
                        val adapter = TeamListAdapter(requireActivity(), mTeam)
                        binding.rvMaintainer.adapter = adapter
                        mTeam = ArrayList<Team>()
                    } else {
                        Log.d(TAG, "Failed to fetch Team List")
                    }

                } catch (e: Throwable) {
                    Log.d(TAG, "Something else went wrong")
                }
            }
        }
    }
}