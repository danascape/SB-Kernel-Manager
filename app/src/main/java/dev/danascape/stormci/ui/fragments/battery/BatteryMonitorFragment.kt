package dev.danascape.stormci.ui.fragments.battery

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dev.danascape.stormci.databinding.FragmentBatteryMonitorBinding
import dev.danascape.stormci.ui.fragments.HomeFragment.Companion.currentList

class BatteryMonitorFragment : Fragment() {

    private var _binding: FragmentBatteryMonitorBinding? = null
    private val binding get() = _binding!!

    var barArrayList = mutableListOf<Entry>()
    var x = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBatteryMonitorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        for (i in currentList) {
            barArrayList.add(BarEntry(x * 1f, i.toInt() * 1f))
            x++
        }

        val lineDataSet = LineDataSet(barArrayList, "Current/Time")
        val lineData = LineData(lineDataSet)
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        binding.lineChart.axisRight.apply {
            setDrawGridLines(false)
            setDrawLabels(false)
            setDrawAxisLine(false)
        }
        binding.lineChart.axisLeft.apply {
            setDrawGridLines(false)
            setDrawLabels(false)
            setDrawAxisLine(false)
        }
        binding.lineChart.xAxis.apply {
            setDrawGridLines(false)
            setDrawLabels(false)
            setDrawAxisLine(false)
        }

        binding.lineChart.setDrawGridBackground(false)
        binding.lineChart.setDrawBorders(false)

        binding.lineChart.data=lineData

        lineDataSet.setDrawFilled(true)
        lineDataSet.valueTextColor= Color.WHITE
        lineDataSet.valueTextSize=16f
        val minXRange = 10f
        val maxXRange = 10f
        binding.lineChart.setVisibleXRange(minXRange, maxXRange)
        binding.lineChart.description.isEnabled=true
        binding.lineChart.notifyDataSetChanged()
    }
}