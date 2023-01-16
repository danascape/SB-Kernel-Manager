package dev.danascape.stormci.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dev.danascape.stormci.databinding.FragmentHomeBinding
import dev.danascape.stormci.ui.MainActivity
import dev.danascape.stormci.util.Constants
import dev.danascape.stormci.util.DeviceUtils.getDeviceProperty
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val activity: MainActivity? = activity as MainActivity?
        val loginState: Int = activity!!.checkLoggedInState()
        if (loginState == 0) {
            Toast.makeText(context, "Welcome Guest!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Successfully Logged In!", Toast.LENGTH_SHORT).show()
        }

        val kernelVersion = getDeviceProperty("uname -r")
        binding.tvKernel.text = kernelVersion

        if (kernelVersion.contains(Constants.KERNEL_NAME.lowercase(Locale.getDefault()))) {
            binding.tvKernelSupport.text = "Device is supported"
            Toast.makeText(context, "Device is supported", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Device is not supported", Toast.LENGTH_SHORT).show()
        }

        val deviceName = getDeviceProperty("getprop ro.product.product.model")
        binding.tvDeviceName.text = deviceName

        return binding.root
    }
}