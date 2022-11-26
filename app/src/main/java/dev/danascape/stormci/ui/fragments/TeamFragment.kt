package dev.danascape.stormci.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.danascape.stormci.R
import dev.danascape.stormci.adapters.team.TeamListAdapter
import dev.danascape.stormci.databinding.FragmentTeamBinding
import dev.danascape.stormci.repository.NetworkResponse

@AndroidEntryPoint
class TeamFragment : Fragment(R.layout.fragment_team) {

    lateinit var binding: FragmentTeamBinding

    private val viewModel: TeamViewModel by viewModels()

    private var mCoreAdapter: TeamListAdapter? = null
    private var mMaintainerAdaptor: TeamListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTeamBinding.bind(view)
        mCoreAdapter = TeamListAdapter()
        mMaintainerAdaptor = TeamListAdapter()
        val linearLayoutManager = LinearLayoutManager(activity)
        val teamLayoutManager = LinearLayoutManager(activity)
        binding.rvCoreTeam.layoutManager = linearLayoutManager
        binding.rvCoreTeam.adapter = mCoreAdapter

        binding.rvMaintainer.layoutManager = teamLayoutManager
        binding.rvMaintainer.adapter = mMaintainerAdaptor

        viewModel.coreMemberList.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResponse.Error -> Log.d("Team Fragment", it.message.toString())
                is NetworkResponse.Loading -> {}
                is NetworkResponse.Success -> {
                    mCoreAdapter?.differ?.submitList(it.data)
                }
            }
        }

        viewModel.maintainerList.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResponse.Error -> Log.d("Team Fragment", it.message.toString())
                is NetworkResponse.Loading -> {}
                is NetworkResponse.Success -> {
                    mMaintainerAdaptor?.differ?.submitList(it.data)
                }
            }
        }

        binding.btnCoreTeam.setOnClickListener {
            val action = TeamFragmentDirections.actionTeamFragmentToCoreTeamFragment()
            findNavController().navigate(action)
        }

        binding.btnMaintainer.setOnClickListener {
            val action = TeamFragmentDirections.actionTeamFragmentToMaintainerFragment()
            findNavController().navigate(action)
        }
    }
}