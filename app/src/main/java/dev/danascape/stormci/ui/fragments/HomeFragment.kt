package dev.danascape.stormci.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.danascape.stormci.R
import dev.danascape.stormci.databinding.FragmentHomeBinding
import dev.danascape.stormci.util.BatteryManager
import dev.danascape.stormci.util.Constants
import dev.danascape.stormci.util.DeviceUtils.getDeviceProperty
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var batteryManager: BatteryManager
    val handler = Handler()
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            setUpBatteryMonitor()
            handler.postDelayed(this, 60000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        batteryManager = BatteryManager(requireContext())
        binding.tvLogo.setOnClickListener {
            Toast.makeText(context, "You pressed to view info", Toast.LENGTH_SHORT).show()
        }

        handler.post(runnable)
        val kernelVersion = getDeviceProperty("uname -r")
        binding.tvKernel.text = kernelVersion

        if (kernelVersion.contains(Constants.KERNEL_NAME.lowercase(Locale.getDefault()))) {
            binding.tvKernelSupport.text = "Device is supported"
            Toast.makeText(context, "Device is supported", Toast.LENGTH_SHORT).show()
        } else {
            binding.tvKernelSupport.text = "Device is not supported"
            Toast.makeText(context, "Device is not supported", Toast.LENGTH_SHORT).show()
        }

        val deviceName = getDeviceProperty("getprop ro.product.product.model")
        binding.tvDeviceName.text = deviceName

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvBattery.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_batteryMonitorFragment)
        }
    }

    private fun setUpBatteryMonitor() {
        binding.batteryPercentage.text = batteryManager.getBatteryPercentage().toString() + "%"
        binding.temperature.text = "Temperature: " + batteryManager.batteryTemperature()
        val charging = batteryManager.getChargingStatus()
        if (charging) {
            binding.chargingStatus.text = context?.getString(R.string.charging)
        } else {
            binding.chargingStatus.text = context?.getString(R.string.not_charging)
        }
        binding.currentStatus.text = "Current: " + batteryManager.getBatteryCurrent() + " mA"

    }
}