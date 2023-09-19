package dev.danascape.stormci.util

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.IBinder

class BatteryManager(private val context: Context): Service() {

    fun getBatteryPercentage(): Int {
        return if (Build.VERSION.SDK_INT >= 21) {
            val bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } else {
            val iFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            val batteryStatus: Intent? = context.registerReceiver(null, iFilter)
            val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            val batteryPct = level?.div(scale?.toDouble()!!)
            (batteryPct?.times(100))!!.toInt()
        }
    }

    fun batteryTemperature(): String {
        val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val temp = intent!!.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0).toFloat() / 10
        return "$temp*C"
    }

    fun getChargingStatus(): Boolean {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            context.registerReceiver(null, ifilter)
        }

        val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL

        if (isCharging) {
            return true
        }
        return false
    }

    fun getBatteryCurrent(): Long {
        val mBatteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        return mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW)
            .div(1000)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

}