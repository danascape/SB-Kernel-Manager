package dev.danascape.stormci.ui.fragments.team

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.danascape.stormci.R
import dev.danascape.stormci.adapters.team.fragment.TeamListFragmentAdapter
import dev.danascape.stormci.databinding.FragmentCoreTeamBinding
import dev.danascape.stormci.repository.NetworkResponse

@AndroidEntryPoint
class CoreTeamFragment : Fragment(R.layout.fragment_core_team) {

    private var mAdapter: TeamListFragmentAdapter? = null
    lateinit var binding: FragmentCoreTeamBinding
    private val viewModel: CoreTeamViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCoreTeamBinding.bind(view)
        mAdapter = TeamListFragmentAdapter()
        val gridManager = GridLayoutManager(context, 2)
        binding.rvCoreTeam.layoutManager = gridManager
        binding.rvCoreTeam.adapter = mAdapter
        viewModel.coreMember.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResponse.Error -> Log.d("CoreTeamFragment", it.message.toString())
                is NetworkResponse.Loading -> {}
                is NetworkResponse.Success -> {
                    mAdapter!!.differ.submitList(it.data)
                }
            }
        }
    }
}