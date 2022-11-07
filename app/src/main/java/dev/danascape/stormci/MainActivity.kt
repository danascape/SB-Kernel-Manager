package dev.danascape.stormci

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dev.danascape.stormci.databinding.ActivityMainBinding
import dev.danascape.stormci.ui.DevicesFragment
import dev.danascape.stormci.ui.HomeFragment
import dev.danascape.stormci.ui.TeamFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if(!isOnline(this)) {
            Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show()
            finish()
        }
        val homeFragment = HomeFragment()
        val teamFragment = TeamFragment()
        val devicesFragment = DevicesFragment()
        setCurrentFragment(homeFragment)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.miHome -> setCurrentFragment(homeFragment)
                R.id.miTeam -> setCurrentFragment(teamFragment)
                R.id.miDevices -> setCurrentFragment(devicesFragment)
            }
            true
        }
    }

    private fun isOnline(context: Context?): Boolean {

        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false

        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

            // else return false
            else -> false
        }
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}