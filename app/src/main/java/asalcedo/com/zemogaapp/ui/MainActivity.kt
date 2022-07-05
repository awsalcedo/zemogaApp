package asalcedo.com.zemogaapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import asalcedo.com.zemogaapp.R
import asalcedo.com.zemogaapp.databinding.ActivityMainBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bnPost
        navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.postListFragment, R.id.favoritePostFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNavigationView.setupWithNavController(navController)

        NavigationUI.setupWithNavController(binding.bnPost, navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || onSupportNavigateUp()
    }
}