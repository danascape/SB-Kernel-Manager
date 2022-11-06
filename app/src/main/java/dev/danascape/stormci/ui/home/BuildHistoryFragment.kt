package dev.danascape.stormci.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.danascape.stormci.R
import dev.danascape.stormci.adaptor.ci.BuildHistoryAdaptor
import dev.danascape.stormci.api.AutomationService
import dev.danascape.stormci.api.client.DroneClient
import dev.danascape.stormci.api.client.GithubAPIClient
import dev.danascape.stormci.model.ci.BuildHistoryList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuildHistoryFragment : Fragment(R.layout.fragment_build_history) {

    private var mApiService: AutomationService? = null
    private var mAdapter: BuildHistoryAdaptor?= null;
    private var mBuildHistory: MutableList<BuildHistoryList> = ArrayList()

    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = requireView().findViewById(R.id.rvDevices)
        recyclerView.layoutManager = layoutManager
        mAdapter = activity?.let { BuildHistoryAdaptor(it, mBuildHistory, R.layout.build_history_item) }
        recyclerView.adapter = mAdapter

        mApiService = DroneClient.client.create(AutomationService::class.java)
        fetchDevicesList()
    }

    private fun fetchDevicesList() {
        val call = mApiService!!.fetchBuildHistory()

        call.enqueue(object : Callback<List<BuildHistoryList>> {

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<BuildHistoryList>>,
                response: Response<List<BuildHistoryList>>
            ) {
                   Log.d("StormCI", "Total Devices Fetched: " + response.body()!!.size)
                val Response = response.body()
                if (Response != null) {
                    mBuildHistory.addAll(Response!!)
                    mAdapter!!.notifyDataSetChanged()
                    mBuildHistory = ArrayList<BuildHistoryList>()
                }
            }
            override fun onFailure(call: Call<List<BuildHistoryList>>, t: Throwable) {
                Log.d("StormCI", "Failed to download JSON")
            }
        })
    }

}