package dev.danascape.stormci.ui.fragments.team

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
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
    private var mAdapter: TeamListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTeamBinding.bind(view)
//        mAdapter = TeamListFragmentAdapter()
        mAdapter = activity?.let { TeamListAdapter(it) }
//        val gridManager = GridLayoutManager(context, 2)
        val layoutManager = LinearLayoutManager(context)
        binding.rvTeam.layoutManager = layoutManager
        binding.rvTeam.adapter = mAdapter
        viewModel.MemberList.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResponse.Error -> Log.d("TeamFragment", it.message.toString())
                is NetworkResponse.Loading -> {}
                is NetworkResponse.Success -> {
                    mAdapter!!.differ.submitList(it.data)
                }
            }
        }
    }
}