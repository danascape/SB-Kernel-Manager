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
import dev.danascape.stormci.databinding.FragmentMaintainerBinding
import dev.danascape.stormci.repository.NetworkResponse

@AndroidEntryPoint
class MaintainerFragment : Fragment(R.layout.fragment_maintainer) {

    private var mAdapter: TeamListFragmentAdapter? = null
    lateinit var binding: FragmentMaintainerBinding
    private val viewModel: MaintainersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMaintainerBinding.bind(view)
        mAdapter = TeamListFragmentAdapter()
        val gridManager = GridLayoutManager(context, 2)
        binding.rvMaintainer.layoutManager = gridManager
        binding.rvMaintainer.adapter = mAdapter
        viewModel.teamMembers.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResponse.Error -> Log.d("MaintainerFragment", it.message.toString())
                is NetworkResponse.Loading -> {}
                is NetworkResponse.Success -> {
                    mAdapter!!.differ.submitList(it.data)
                }
            }
        }
    }
}