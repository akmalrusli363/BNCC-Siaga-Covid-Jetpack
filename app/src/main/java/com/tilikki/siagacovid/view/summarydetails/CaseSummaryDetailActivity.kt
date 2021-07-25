package com.tilikki.siagacovid.view.summarydetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tilikki.siagacovid.R
import com.tilikki.siagacovid.databinding.ActivityCaseSummaryDetailBinding

class CaseSummaryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCaseSummaryDetailBinding
    private lateinit var viewModel: CaseSummaryDetailViewModel

    private val appBarConfiguration = AppBarConfiguration(
        setOf(
            R.id.navigation_home,
            R.id.navigation_case_details,
            R.id.navigation_testing_and_tracing,
            R.id.navigation_vaccination
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaseSummaryDetailBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)
            .get(CaseSummaryDetailViewModel::class.java)
        setContentView(binding.root)
        setupNavigation()
        startObserve()
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupNavigation() {
        val navView: BottomNavigationView = binding.navView
        val navFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navFragment.navController

        binding.ivReturnIcon.setOnClickListener { finish() }
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun startObserve() {
        viewModel.fetchData()
    }
}
