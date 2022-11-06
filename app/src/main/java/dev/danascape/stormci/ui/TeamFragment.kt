package dev.danascape.stormci.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.danascape.stormci.R
import dev.danascape.stormci.adaptor.team.CoreTeamListAdaptor
import dev.danascape.stormci.api.GithubAPIClient
import dev.danascape.stormci.api.team.CoreTeamService
import dev.danascape.stormci.databinding.FragmentHomeBinding
import dev.danascape.stormci.databinding.FragmentTeamBinding
import dev.danascape.stormci.model.team.CoreTeam
import dev.danascape.stormci.model.team.CoreTeamList
import dev.danascape.stormci.ui.team.CoreTeamFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamFragment : Fragment(R.layout.fragment_team) {
    private var _binding: FragmentTeamBinding? = null
    private val binding
    get() = _binding!!

    private var mApiService: CoreTeamService? = null
    private var mAdapter: CoreTeamListAdaptor?= null;
    private var mCoreTeam: MutableList<CoreTeam> = ArrayList()

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnCoreTeam: Button

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
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.flFragment, CoreTeamFragment)
            transaction.commit()
        }

        val layoutManager = LinearLayoutManager(context)
        recyclerView = requireView().findViewById(R.id.rvCoreTeam)
        recyclerView.layoutManager = layoutManager
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