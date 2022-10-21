package dev.danascape.stormci.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.danascape.stormci.R
import dev.danascape.stormci.adaptor.DevicesListAdaptor
import dev.danascape.stormci.api.APIClient
import dev.danascape.stormci.api.DevicesService
import dev.danascape.stormci.model.Devices
import dev.danascape.stormci.model.DevicesList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DevicesFragment : Fragment(R.layout.fragment_devices) {

    private var mApiService: DevicesService? = null
    private var mAdapter: DevicesListAdaptor?= null;
    private var mQuestions: MutableList<Devices> = ArrayList()

    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = requireView().findViewById(R.id.listRecyclerView)
        recyclerView.layoutManager = layoutManager
        mAdapter = activity?.let { DevicesListAdaptor(it, mQuestions, R.layout.devices_item) }
        recyclerView.adapter = mAdapter

        mApiService = APIClient.client.create(DevicesService::class.java)
        fetchDevicesList()
    }

    private fun fetchDevicesList() {
        val call = mApiService!!.fetchDevices()
        call.enqueue(object : Callback<DevicesList> {

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<DevicesList>, response: Response<DevicesList>) {
                Log.d("StormCI", "Total Devices Fetched: " + response.body()!!.items!!.size)
                val questions = response.body()
                if (questions != null) {
                    mQuestions.addAll(questions.items!!)
                    mAdapter!!.notifyDataSetChanged()
                    mQuestions=ArrayList<Devices>()
                }
            }
            override fun onFailure(call: Call<DevicesList>, t: Throwable) {
                Log.d("StormCI", "Failed to download JSON")
            }
        })
    }
}