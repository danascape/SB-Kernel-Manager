package dev.danascape.stormci

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import dev.danascape.stormci.databinding.ActivityMainBinding
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val btnRefresh = getView()?.findViewById<Button>(R.id.btnRefresh)
//        val tvName = getView()?.findViewById<TextView>(R.id.tvName)
//        val tvDevice = getView()?.findViewById<TextView>(R.id.tvDevice)
//        val tvStatus = getView()?.findViewById<TextView>(R.id.tvStatus)

        fetchUpdate()
        if (btnRefresh != null) {
            btnRefresh.setOnClickListener {
                fetchUpdate()
            }
        }

    }
//    val kernelVersion = readKernelVersion()

    private fun fetchUpdate() {
        val retrofit = BuildInterface.create().getBuildInfo()
        retrofit.enqueue(object: Callback<BuildModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<BuildModel>, response: Response<BuildModel>) {
                val tvName = getView()?.findViewById<TextView>(R.id.tvName)
                val tvDevice = getView()?.findViewById<TextView>(R.id.tvDevice)
                val tvStatus = getView()?.findViewById<TextView>(R.id.tvStatus)
                val resBody = response.body()
                if(resBody != null){
                    Log.d("retrofitResponse", "res: $resBody")
                    Log.d("retrofitResponse", "name: ${resBody.name} ${resBody.branch}")
                    tvName?.text = "${resBody.name} ${resBody.branch}"
                    Log.d("retrofitResponse", "device: ${resBody.device}")
                    tvDevice?.text = "Device: ${resBody.device}"
                    Log.d( "retrofitResponse", "Build Status: ${resBody.status}" )
                    tvStatus?.text = "Status: ${resBody.status}"
                }
            }

            override fun onFailure(call: Call<BuildModel>, t: Throwable) {
                Log.e("retrofitResponse","Error: ${t.message}")
            }
        })
    }

//    private fun readKernelVersion(): String {
//        try {
//            val p = Runtime.getRuntime().exec("uname -av")
//            val `is`: InputStream? = if (p.waitFor() == 0) {
//                p.inputStream
//            } else {
//                p.errorStream
//            }
//            val br = BufferedReader(
//                InputStreamReader(`is`),
//                32
//            )
//            val line = br.readLine()
//            br.close()
//            return line
//        } catch (ex: Exception) {
//            return "ERROR: " + ex.message
//        }
//    }
}