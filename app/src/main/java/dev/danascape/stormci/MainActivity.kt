package dev.danascape.stormci

import android.os.Bundle
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

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}