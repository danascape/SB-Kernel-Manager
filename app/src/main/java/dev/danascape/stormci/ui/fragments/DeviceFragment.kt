package dev.danascape.stormci.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.danascape.stormci.R
import dev.danascape.stormci.ui.adapters.DeviceListAdapter
import dev.danascape.stormci.databinding.FragmentDevicesBinding
import dev.danascape.stormci.models.Device
import dev.danascape.stormci.repository.NetworkResponse
import dev.danascape.stormci.viewModels.DeviceViewModel

@AndroidEntryPoint
class DeviceFragment : Fragment(R.layout.fragment_devices) {

    private lateinit var binding: FragmentDevicesBinding

    private var mAdapter: DeviceListAdapter? = null
    private val viewModel: DeviceViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDevicesBinding.bind(view)
        mAdapter = activity?.let { DeviceListAdapter(it) }

        binding.rvDevices.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }
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

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                filterList(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

        })
    }

    private fun filterList(text: String) {
        if (text.isBlank()) {
            mAdapter?.differ?.submitList(viewModel.deviceList.value?.data ?: emptyList())
        } else {
            val filteredList: ArrayList<Device> = arrayListOf()
            for (item in mAdapter?.differ?.currentList!!) {
                if (item.name?.contains(text, ignoreCase = true) == true) {
                    filteredList.add(item)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show()
            } else {
                mAdapter!!.setFilteredList(filteredList)
            }
        }
    }
}
