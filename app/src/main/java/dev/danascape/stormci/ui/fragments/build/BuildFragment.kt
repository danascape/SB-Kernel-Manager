package dev.danascape.stormci.ui.fragments.build

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.danascape.stormci.R
import dev.danascape.stormci.adapters.build.BuildHistoryAdapter
import dev.danascape.stormci.databinding.FragmentBuildBinding
import dev.danascape.stormci.repository.NetworkResponse

@AndroidEntryPoint
class BuildFragment : Fragment(R.layout.fragment_build) {

    private var mAdapter: BuildHistoryAdapter? = null
    private val viewModel: BuildViewModel by viewModels()
    lateinit var binding: FragmentBuildBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBuildBinding.bind(view)
        val layoutManager = LinearLayoutManager(context)
        mAdapter = BuildHistoryAdapter()
        binding.rvDevices.layoutManager = layoutManager
        binding.rvDevices.adapter = mAdapter
        viewModel.buildHistory.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResponse.Error -> {
                    Log.d("BuildFragment", it.message.toString())
                }
                is NetworkResponse.Loading -> {}
                is NetworkResponse.Success -> {
                    mAdapter?.differ?.submitList(it.data)
                }
            }
        }
    }
}