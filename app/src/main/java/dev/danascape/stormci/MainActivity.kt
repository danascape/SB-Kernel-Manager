package dev.danascape.stormci

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvName = findViewById<TextView>(R.id.tvName)
        val tvDevice = findViewById<TextView>(R.id.tvDevice)
        val tvBranch = findViewById<TextView>(R.id.tvBranch)
        val tvStatus = findViewById<TextView>(R.id.tvStatus)

        val retrofit = BuildInterface.create().getBuildInfo()

        retrofit.enqueue(object: Callback<BuildModel>{
            override fun onResponse(call: Call<BuildModel>, response: Response<BuildModel>) {
                val resBody = response.body()
                if(resBody != null){
                    Log.d("retrofitResponse", "res: ${resBody}")
                    Log.d("retrofitResponse", "name: ${resBody.name}")
                    tvName.text = "${resBody.name}"
                    Log.d("retrofitResponse", "device: ${resBody.device}")
                    tvDevice.text = "Device: ${resBody.device}"
                    Log.d( "retrfitResponse", "branch: ${resBody.branch}" )
                    tvBranch.text = "Branch: ${resBody.branch}"
                    Log.d( "retrfitResponse", "status: ${resBody.status}" )
                    tvStatus.text = "Status: ${resBody.status}"
                }
            }

            override fun onFailure(call: Call<BuildModel>, t: Throwable) {
                Log.e("retrofitResponse","Error: ${t.message}")
            }
        })
    }
}