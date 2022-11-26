package dev.danascape.stormci.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.danascape.stormci.R
import dev.danascape.stormci.adapters.ci.BuildHistoryAdapter
import dev.danascape.stormci.databinding.FragmentBuildHistoryBinding
import dev.danascape.stormci.repository.NetworkResponse

@AndroidEntryPoint
class BuildHistoryFragment : Fragment(R.layout.fragment_build_history) {

    private var mAdapter: BuildHistoryAdapter? = null
    private val viewModel: BuildHistoryViewModel by viewModels()
    private lateinit var binding: FragmentBuildHistoryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBuildHistoryBinding.bind(view)
        val layoutManager = LinearLayoutManager(context)
        mAdapter = BuildHistoryAdapter()
        binding.rvDevices.layoutManager = layoutManager
        binding.rvDevices.adapter = mAdapter
        viewModel.buildHistory.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResponse.Error -> {
                    Log.d("BuildHistoryFragment", it.message.toString())
                }
                is NetworkResponse.Loading -> {}
                is NetworkResponse.Success -> {
                    mAdapter?.differ?.submitList(it.data)
                }
            }
        }
    }
}