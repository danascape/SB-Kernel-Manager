package dev.danascape.stormci.ui.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.danascape.stormci.R
import dev.danascape.stormci.adapters.ci.BuildHistoryAdapter
import dev.danascape.stormci.api.ci.DroneService
import dev.danascape.stormci.api.DroneAPI
import dev.danascape.stormci.models.ci.BuildHistory
import dev.danascape.stormci.util.Constants.Companion.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuildHistoryFragment : Fragment(R.layout.fragment_build_history) {

    private var mApiService: DroneService? = null
    private var mAdapter: BuildHistoryAdapter?= null;
    private var mBuildHistory: MutableList<BuildHistory> = ArrayList()

    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = requireView().findViewById(R.id.rvDevices)
        recyclerView.layoutManager = layoutManager
        mAdapter = activity?.let { BuildHistoryAdapter(it, mBuildHistory, R.layout.build_history_item) }
        recyclerView.adapter = mAdapter

        mApiService = DroneAPI.client.create(DroneService::class.java)
        fetchDevicesList()
    }

    private fun fetchDevicesList() {
        val call = mApiService!!.fetchBuildHistory()

        call.enqueue(object : Callback<List<BuildHistory>> {

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<BuildHistory>>,
                response: Response<List<BuildHistory>>
            ) {
                Log.d(TAG, "Total Devices history Fetched: " + response.body()!!.size)
                val Response = response.body()
                if (Response != null) {
                    mBuildHistory.addAll(Response)
                    mAdapter!!.notifyDataSetChanged()
                    mBuildHistory = ArrayList<BuildHistory>()
                }
            }
            override fun onFailure(call: Call<List<BuildHistory>>, t: Throwable) {
                Log.d(TAG, "Failed to download JSON")
            }
        })
    }

}