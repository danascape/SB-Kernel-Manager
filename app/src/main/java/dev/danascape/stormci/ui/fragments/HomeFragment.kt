package dev.danascape.stormci.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.danascape.stormci.databinding.FragmentHomeBinding
import dev.danascape.stormci.util.Constants
import dev.danascape.stormci.util.DeviceUtils.getDeviceProperty
import java.util.*

@AndroidEntryPoint
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

        val kernelVersion = getDeviceProperty("uname -r")
        binding.tvKernel.text = kernelVersion

        if (kernelVersion.contains(Constants.KERNEL_NAME.lowercase(Locale.getDefault()))) {
            binding.tvKernelSupport.text = "Device is supported"
            Toast.makeText(context, "Device is supported", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Device is not supported", Toast.LENGTH_SHORT).show()
        }

        val deviceName = getDeviceProperty("getprop ro.product.device")
        binding.tvDeviceName.text = deviceName

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnHistory.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToBuildHistoryFragment2()
            findNavController().navigate(action)
        }
    }
}