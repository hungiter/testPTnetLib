package com.example.testkt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.testkt.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private var curr = 1
    private val end = 1023

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        reloadData()
        binding.fab.setOnClickListener {
            reloadData()
        }
    }

    private fun displayFragment(
        pageLoadResult: String,
        dnsApi: String,
        dnsJava: String,
        portScan: String,
        port: Int
    ) {
        val bundle = Bundle().apply {
            putString("pageLoadResult", "PageLoad\n$pageLoadResult")
            putString("dnsResult_api", "DnsApi\n$dnsApi")
            putString("dnsResult_dnsJava", "DnsJava\n$dnsJava")
            putString("portScanResult", "PortScan\n$portScan")
            putString("port", "Scanned: $port")
        }
        val fragment = TestFragment().apply { arguments = bundle }
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_main, fragment)
            .commit()
    }

    private fun reloadData() {
        coroutineScope.launch {

            val pageLoadResult = LibraryHandle().getPageLoad()
            val dnsResult_api = LibraryHandle().getDnsLookup()
            val dnsResult_dnsJava = LibraryHandle().getDnsLookup_dnsJava()
            var portScan_result = ""
            while (curr <= end) {
                LibraryHandle().getPortScan(curr) {
                    if (it.isNullOrEmpty()) {
                    } else {
                        if (portScan_result.isNullOrEmpty())
                            portScan_result = " Open:"
                        portScan_result += it
                    }

                    var temp = ""
                    if (portScan_result.isNullOrEmpty()) {
                        temp = "Not opened port til nows"
                    }
                    else{
                        temp = portScan_result
                    }

                    runOnUiThread {
                        displayFragment(
                            pageLoadResult,
                            dnsResult_api,
                            dnsResult_dnsJava,
                            portScan = temp,
                            curr++
                        )
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}