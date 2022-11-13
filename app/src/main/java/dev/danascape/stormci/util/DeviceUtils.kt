package dev.danascape.stormci.util

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object DeviceUtils {
    fun readKernelVersion(): String {
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

    fun readDeviceName(): String {
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