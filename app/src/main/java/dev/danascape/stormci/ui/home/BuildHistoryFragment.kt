package dev.danascape.stormci.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.danascape.stormci.R
import dev.danascape.stormci.adaptor.ci.BuildHistoryAdaptor
import dev.danascape.stormci.adaptor.device.DevicesListAdaptor
import dev.danascape.stormci.api.AutomationService
import dev.danascape.stormci.api.client.GithubAPIClient
import dev.danascape.stormci.api.device.DevicesService
import dev.danascape.stormci.model.ci.BuildHistory
import dev.danascape.stormci.model.ci.BuildHistoryList
import dev.danascape.stormci.model.device.Devices
import dev.danascape.stormci.model.device.DevicesList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuildHistoryFragment : Fragment(R.layout.fragment_build_history) {

    private var mApiService: AutomationService? = null
    private var mAdapter: BuildHistoryAdaptor?= null;
    private var mBuildHistory: MutableList<BuildHistory> = ArrayList()

    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = requireView().findViewById(R.id.rvDevices)
        recyclerView.layoutManager = layoutManager
        mAdapter = activity?.let { BuildHistoryAdaptor(it, mBuildHistory, R.layout.devices_item) }
        recyclerView.adapter = mAdapter

        mApiService = GithubAPIClient.client.create(AutomationService::class.java)
        fetchDevicesList()
    }

    private fun fetchDevicesList() {
        val call = mApiService!!.fetchBuildHistory()

        call.enqueue(object : Callback<BuildHistoryList> {

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<BuildHistoryList>, response: Response<BuildHistoryList>) {
                   Log.d("StormCI", "Total Devices Fetched: " + response.body()!!.list!!.size)
                val Response = response.body()
                if (Response != null) {
                    mBuildHistory.addAll(Response.list!!)
                    mAdapter!!.notifyDataSetChanged()
                    mBuildHistory = ArrayList<BuildHistory>()
                }
            }
            override fun onFailure(call: Call<BuildHistoryList>, t: Throwable) {
                Log.d("StormCI", "Failed to download JSON")
            }
        })
    }

}