package dev.danascape.stormci.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val thisContext = container?.context!!
        val layoutManager = LinearLayoutManager(context)
        recyclerView = requireView().findViewById(R.id.listRecyclerView)
        recyclerView.layoutManager = layoutManager
        mAdapter = DevicesListAdaptor(thisContext, mQuestions, R.layout.devices_item)
        recyclerView.adapter = mAdapter

        mApiService = APIClient.client.create(DevicesService::class.java)
        fetchDevicesList()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun fetchDevicesList() {
        val call = mApiService!!.fetchDevices()
        call.enqueue(object : Callback<DevicesList> {

            override fun onResponse(call: Call<DevicesList>, response: Response<DevicesList>) {
                Log.d("StormCI", "Total Devices: " + response.body()!!.items!!.size)
                val questions = response.body()
                if (questions != null) {
                    mQuestions.addAll(questions.items!!)
                    mAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<DevicesList>, t: Throwable) {
                Log.d("StormCI", "Total Questions: ")
                TODO("Not yet implemented")
            }
        })
    }
}