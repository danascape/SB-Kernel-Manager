package dev.danascape.stormci.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.elevation.SurfaceColors
import dagger.hilt.android.AndroidEntryPoint
import dev.danascape.stormci.R
import dev.danascape.stormci.databinding.ActivityMainBinding
import dev.danascape.stormci.ui.fragments.DeviceFragment
import dev.danascape.stormci.ui.fragments.HomeFragment
import dev.danascape.stormci.ui.fragments.TeamFragment
import dev.danascape.stormci.util.NetworkUtils.isOnline

@AndroidEntryPoint
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
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController=navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
    }

}