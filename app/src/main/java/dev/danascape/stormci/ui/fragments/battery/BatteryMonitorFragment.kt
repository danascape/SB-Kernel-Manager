package dev.danascape.stormci.ui.fragments.battery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.danascape.stormci.databinding.FragmentBatteryMonitorBinding

class BatteryMonitorFragment : Fragment() {

    private var _binding: FragmentBatteryMonitorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBatteryMonitorBinding.inflate(inflater, container, false)
        return binding.root
    }
}