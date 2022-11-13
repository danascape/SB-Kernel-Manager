package dev.danascape.stormci.ui.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.danascape.stormci.R
import dev.danascape.stormci.adapters.ci.BuildHistoryAdapter
import dev.danascape.stormci.api.DroneAPI
import dev.danascape.stormci.api.ci.DroneService
import dev.danascape.stormci.models.ci.BuildHistory
import dev.danascape.stormci.util.Constants.Companion.TAG
import kotlinx.coroutines.*
import retrofit2.awaitResponse

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
        fetchBuildHistory()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchBuildHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = mApiService?.fetchBuildHistory()!!
            withContext(Dispatchers.Main) {
                try {
                    if(response.isSuccessful) {
                        mBuildHistory.addAll(response.body()!!)
                        mAdapter!!.notifyDataSetChanged()
                        mBuildHistory = ArrayList<BuildHistory>()
                    } else {
                        Log.d(TAG, "Failed to fetch build history")
                    }
                } catch (e: Throwable) {
                    Log.d(TAG, "Something else went wrong")
                }
            }
        }
    }
}