package dev.danascape.stormci.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.danascape.stormci.databinding.FragmentDeviceInfoBinding
import dev.danascape.stormci.ui.fragments.base.BaseFragment

class DeviceInfoFragment : BaseFragment() {
    private var _binding: FragmentDeviceInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeviceInfoBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }
}