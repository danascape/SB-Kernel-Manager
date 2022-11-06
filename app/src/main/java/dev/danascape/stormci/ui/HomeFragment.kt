package dev.danascape.stormci.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.danascape.stormci.api.GithubAPIClient
import dev.danascape.stormci.api.HomeService
import dev.danascape.stormci.databinding.FragmentHomeBinding
import dev.danascape.stormci.model.BuildModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding
    get() = _binding!!

    private var mApiService: HomeService? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val kernelVersion = readKernelVersion()
        binding.tvKernel.text = kernelVersion

        val deviceName = readDeviceName()
        binding.tvDeviceName.text = deviceName

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mApiService = GithubAPIClient.client.create(HomeService::class.java)
        fetchUpdate()
        binding.btnRefresh.setOnClickListener {
            fetchUpdate()
        }
    }

    private fun fetchUpdate() {
        val retrofit = mApiService!!.getBuildInfo()
        retrofit.enqueue(object: Callback<BuildModel> {

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<BuildModel>, response: Response<BuildModel>) {
                val resBody = response.body()
                if(resBody != null){
                    Log.d("retrofitResponse", "res: $resBody")
                    Log.d("retrofitResponse", "name: ${resBody.name} ${resBody.branch}")
                    binding.tvName.text = "${resBody.name} ${resBody.branch}"
                    Log.d("retrofitResponse", "device: ${resBody.device}")
                    binding.tvDevice.text = "Device: ${resBody.device}"
                    Log.d( "retrofitResponse", "Build Status: ${resBody.status}" )
                    binding.tvStatus.text = "Status: ${resBody.status}"
                }
            }

            override fun onFailure(call: Call<BuildModel>, t: Throwable) {
                Log.e("retrofitResponse","Error: ${t.message}")
            }
        })
    }

    private fun readKernelVersion(): String {
        try {
            val p = Runtime.getRuntime().exec("uname -r")
            val `is`: InputStream? = if (p.waitFor() == 0) {
                p.inputStream
            } else {
                p.errorStream
            }
            val br = BufferedReader(
                InputStreamReader(`is`),
                32
            )
            val line = br.readLine()
            br.close()
            return line
        } catch (ex: Exception) {
            return "ERROR: " + ex.message
        }
    }

    private fun readDeviceName(): String {
        try {
            val p = Runtime.getRuntime().exec("getprop ro.product.device")
            val `is`: InputStream? = if (p.waitFor() == 0) {
                p.inputStream
            } else {
                p.errorStream
            }
            val br = BufferedReader(
                InputStreamReader(`is`),
                32
            )
            val line = br.readLine()
            br.close()
            return line
        } catch (ex: Exception) {
            return "ERROR: " + ex.message
        }
    }
}