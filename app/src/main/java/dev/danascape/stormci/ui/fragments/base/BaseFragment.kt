package dev.danascape.stormci.ui.fragments.base

import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    fun showToast(message: String) {
        showToast(message, Toast.LENGTH_SHORT)
    }

    private fun showToast(message: String?, toastType: Int) {
        Toast.makeText(requireContext(), message, toastType).show()
    }

}