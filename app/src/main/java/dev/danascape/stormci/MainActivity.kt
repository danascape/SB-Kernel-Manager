package dev.danascape.stormci

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dev.danascape.stormci.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRefresh.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
            startActivity(getIntent())
            overridePendingTransition(0, 0)
        }

        val retrofit = BuildInterface.create().getBuildInfo()
        retrofit.enqueue(object: Callback<BuildModel>{
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<BuildModel>, response: Response<BuildModel>) {
                val resBody = response.body()
                if(resBody != null){
                    Log.d("retrofitResponse", "res: $resBody")
                    Log.d("retrofitResponse", "name: ${resBody.name}")
                    binding.tvName.text = resBody.name
                    Log.d("retrofitResponse", "device: ${resBody.device}")
                    binding.tvDevice.text = "Device: ${resBody.device}"
                    Log.d( "retrofitResponse", "branch: ${resBody.branch}" )
                    binding.tvBranch.text = "Branch: ${resBody.branch}"
                    Log.d( "retrofitResponse", "Build Status: ${resBody.status}" )
                    binding.tvStatus.text = "Status: ${resBody.status}"
                }
            }

            override fun onFailure(call: Call<BuildModel>, t: Throwable) {
                Log.e("retrofitResponse","Error: ${t.message}")
            }
        })
    }
}