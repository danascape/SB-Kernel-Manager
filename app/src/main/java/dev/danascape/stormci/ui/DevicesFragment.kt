package dev.danascape.stormci.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.WanderingCubes
import dev.danascape.stormci.R
import dev.danascape.stormci.adaptor.device.DevicesListAdaptor
import dev.danascape.stormci.api.client.GithubAPIClient
import dev.danascape.stormci.api.device.DevicesService
import dev.danascape.stormci.model.device.Devices
import dev.danascape.stormci.model.device.DevicesList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DevicesFragment : Fragment(R.layout.fragment_devices) {

    private var mApiService: DevicesService? = null
    private var mAdapter: DevicesListAdaptor?= null;
    private var mDevices: MutableList<Devices> = ArrayList()

    private lateinit var recyclerView: RecyclerView

    private  var progressBar: ProgressBar? =null
    private  var doubleBounce: Sprite=WanderingCubes()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = requireView().findViewById(R.id.rvDevices)
	    progressBar = requireView().findViewById(R.id.spin_kit)
        recyclerView.layoutManager = layoutManager
        mAdapter = activity?.let { DevicesListAdaptor(it, mDevices, R.layout.devices_item) }
        recyclerView.adapter = mAdapter

        mApiService = GithubAPIClient.client.create(DevicesService::class.java)
        progressBar?.setIndeterminateDrawable(doubleBounce)
        progressBar?.visibility=View.VISIBLE
        fetchDevicesList()
    }

    private fun fetchDevicesList() {
        val call = mApiService!!.fetchDevices()

        call.enqueue(object : Callback<DevicesList> {

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<DevicesList>, response: Response<DevicesList>) {
                progressBar?.visibility=View.GONE
                Log.d("StormCI", "Total Devices Fetched: " + response.body()!!.devices!!.size)
                val Response = response.body()
                if (Response != null) {
                    mDevices.addAll(Response.devices!!)
                    mAdapter!!.notifyDataSetChanged()
                    mDevices=ArrayList<Devices>()
                }
            }
            override fun onFailure(call: Call<DevicesList>, t: Throwable) {
                Log.d("StormCI", "Failed to download JSON")
            }
        })
    }
}
