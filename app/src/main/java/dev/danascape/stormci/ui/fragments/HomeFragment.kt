package dev.danascape.stormci.ui.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import dev.danascape.stormci.databinding.FragmentHomeBinding
import dev.danascape.stormci.ui.fragments.base.BaseFragment
import dev.danascape.stormci.utils.Constants
import dev.danascape.stormci.utils.DeviceUtils.getDeviceProperty
import java.util.*

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setDeviceInfo()

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun setDeviceInfo() {
        val kernelVersion = getDeviceProperty("uname -r")
        binding.tvKernel.text = "Kernel Version: $kernelVersion"

        if (kernelVersion.contains(Constants.KERNEL_NAME.lowercase(Locale.getDefault()))) {
            binding.tvKernelSupport.text = "Device is supported"
        } else {
            binding.tvKernelSupport.text = "Device is not supported"
        }

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            binding.tvAndroid.text = "Version: Android 10"
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
            binding.tvAndroid.text = "Version: Android 11"
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.S) {
            binding.tvAndroid.text = "Version: Android 12"
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.S_V2) {
            binding.tvAndroid.text = "Version: Android 12L"
        } else if (Build.VERSION.SDK_INT == 33) {
            binding.tvAndroid.text = "Version: Android 13"
        } else if (Build.VERSION.SDK_INT == 34) {
            binding.tvAndroid.text = "Version: Android 14"
        } else {
            binding.tvAndroid.text = "Version: Android Unknown"
        }

        // Build.DEVICE.toString() -> Codename
        binding.tvManufacturer.text = "Manufacturer: ${Build.BRAND}"
        val deviceName = getDeviceProperty("getprop ro.product.product.model")
        binding.tvDeviceName.text = "Device Name: $deviceName"
    }
}