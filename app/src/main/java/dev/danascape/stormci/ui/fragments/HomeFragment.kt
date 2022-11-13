package dev.danascape.stormci.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import dev.danascape.stormci.R
import dev.danascape.stormci.databinding.FragmentHomeBinding
import dev.danascape.stormci.ui.fragments.home.BuildHistoryFragment
import dev.danascape.stormci.util.Constants
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding
    get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val kernelVersion = readKernelVersion()
        binding.tvKernel.text = kernelVersion

        if(kernelVersion.contains(Constants.KERNEL_NAME.lowercase(Locale.getDefault()))) {
            binding.tvKernelSupport.text = "Device is supported"
            Toast.makeText(context, "Device is supported", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Device is not supported", Toast.LENGTH_SHORT).show()
        }

        val deviceName = readDeviceName()
        binding.tvDeviceName.text = deviceName

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnHistory.setOnClickListener {
            val BuildHistoryFragment = BuildHistoryFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.flFragment, BuildHistoryFragment)
            transaction.commit()
        }
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