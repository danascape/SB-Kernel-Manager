package dev.danascape.stormci.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.danascape.stormci.R
import dev.danascape.stormci.adapters.device.DeviceListAdapter
import dev.danascape.stormci.api.GithubAPI
import dev.danascape.stormci.api.device.DeviceService
import dev.danascape.stormci.models.device.Device
import dev.danascape.stormci.util.Constants.Companion.TAG
import kotlinx.coroutines.*

class DeviceFragment : Fragment(R.layout.fragment_devices) {

    private var mApiService: DeviceService? = null
    private var mAdapter: DeviceListAdapter?= null;
    private var mDevices: MutableList<Device> = ArrayList()
    private var progressBar: ProgressBar? = null

    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = requireView().findViewById(R.id.rvDevices)
        progressBar = requireView().findViewById(R.id.pbDevice)
        recyclerView.layoutManager = layoutManager
        mAdapter = activity?.let { DeviceListAdapter(it, mDevices, R.layout.devices_item) }
        recyclerView.adapter = mAdapter

        mApiService = GithubAPI.client.create(DeviceService::class.java)
        progressBar?.isIndeterminate()
        progressBar?.visibility = View.VISIBLE
        fetchDevicesList()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchDevicesList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = mApiService?.fetchDevices()
            withContext(Dispatchers.Main) {
                try {
                    if(response!!.isSuccessful) {
                        progressBar?.visibility = View.GONE
                        mDevices.addAll(response.body()!!.devices!!)
                        mAdapter!!.notifyDataSetChanged()
                        mDevices=ArrayList<Device>()
                    } else {
                        Log.d(TAG, "Failed to fetch devices")
                    }
                }  catch (e: Throwable) {
                    Log.d(TAG, "Something else went wrong")
                }
            }
        }
    }
}
