package dev.danascape.stormci.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.elevation.SurfaceColors
import dev.danascape.stormci.R
import dev.danascape.stormci.databinding.ActivityMainBinding
import dev.danascape.stormci.ui.fragments.DeviceFragment
import dev.danascape.stormci.ui.fragments.HomeFragment
import dev.danascape.stormci.ui.fragments.TeamFragment
import dev.danascape.stormci.util.NetworkUtils.isOnline

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        window.statusBarColor=SurfaceColors.SURFACE_0.getColor(this)
        if(!isOnline(this)) {
            Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show()
            finish()
        }
        val homeFragment = HomeFragment()
        val teamFragment = TeamFragment()
        val deviceFragment = DeviceFragment()
        setCurrentFragment(homeFragment)

        binding.bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.miHome -> setCurrentFragment(homeFragment)
                R.id.miTeam -> setCurrentFragment(teamFragment)
                R.id.miDevices -> setCurrentFragment(deviceFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}