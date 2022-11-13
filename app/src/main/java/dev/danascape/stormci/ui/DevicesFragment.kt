package dev.danascape.stormci.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.danascape.stormci.R
import dev.danascape.stormci.adaptor.device.DevicesListAdaptor
import dev.danascape.stormci.api.client.GithubAPIClient
import dev.danascape.stormci.api.device.DevicesService
import dev.danascape.stormci.model.device.Devices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DevicesFragment : Fragment(R.layout.fragment_devices) {

    private var mApiService: DevicesService? = null
    private var mAdapter: DevicesListAdaptor?= null;
    private var mDevices: MutableList<Devices> = ArrayList()
    private var progressBar: ProgressBar? = null

    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = requireView().findViewById(R.id.rvDevices)
        progressBar = requireView().findViewById(R.id.pbDevice)
        recyclerView.layoutManager = layoutManager
        mAdapter = activity?.let { DevicesListAdaptor(it, mDevices, R.layout.devices_item) }
        recyclerView.adapter = mAdapter

        mApiService = GithubAPIClient.client.create(DevicesService::class.java)
        progressBar?.isIndeterminate()
        progressBar?.visibility = View.VISIBLE
        fetchDevicesList()
    }

    private fun fetchDevicesList() {
        GlobalScope.launch(Dispatchers.Main) {
            val response = mApiService?.fetchDevices()
            if(response!!.isSuccessful) {
                    progressBar?.visibility = View.GONE
                    mDevices.addAll(response.body()!!.devices!!)
                    mAdapter!!.notifyDataSetChanged()
                    mDevices=ArrayList<Devices>()
            } else {
                Log.d("StormCI", "Failed to fetch devices")
            }
        }
    }
}
