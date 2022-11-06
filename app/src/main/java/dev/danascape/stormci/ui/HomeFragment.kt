package dev.danascape.stormci.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import dev.danascape.stormci.R
import dev.danascape.stormci.databinding.FragmentHomeBinding
import dev.danascape.stormci.ui.home.BuildHistoryFragment
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

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