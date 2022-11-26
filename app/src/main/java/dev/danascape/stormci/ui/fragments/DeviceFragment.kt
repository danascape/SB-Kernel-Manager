package dev.danascape.stormci.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.danascape.stormci.R
import dev.danascape.stormci.adapters.device.DeviceListAdapter
import dev.danascape.stormci.databinding.FragmentDevicesBinding
import dev.danascape.stormci.repository.NetworkResponse

@AndroidEntryPoint
class DeviceFragment : Fragment(R.layout.fragment_devices) {

    private var mAdapter: DeviceListAdapter? = null
    private lateinit var binding: FragmentDevicesBinding
    private val viewModel: DeviceViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDevicesBinding.bind(view)
        mAdapter = DeviceListAdapter()
        val layoutManager = LinearLayoutManager(context)
        binding.rvDevices.layoutManager = layoutManager
        binding.rvDevices.adapter = mAdapter
        binding.pbDevice.isIndeterminate
        viewModel.deviceList.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResponse.Error -> {
                    binding.pbDevice.visibility = View.GONE
                    Log.d("DeviceFragment", it.message.toString())
                }
                is NetworkResponse.Loading -> binding.pbDevice.visibility = View.VISIBLE
                is NetworkResponse.Success -> {
                    binding.pbDevice.visibility = View.GONE
                    mAdapter?.differ?.submitList(it.data)
                }
            }
        }
    }
}
