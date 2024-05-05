package com.example.testkt

import androidx.compose.runtime.mutableStateOf
import com.ftel.ptnetlibrary.services.*

class LibraryHandle {
    private val domainName = "znews.vn"
    private val dnsServer = "8.8.8.8"
    private var curr = 1;
    private val end = 1023;

    fun getPageLoad(): String {
        val pageLoad = PageLoadService()
        return "Domain: ${domainName}. Time: ${pageLoad.pageLoadTimer(domainName)}ms"
    }

    fun getDnsLookup(): String {
        val lookup = NsLookupService()
        return "Domain: $domainName\n${lookup.lookupResponse_api(domainName)}"
    }

    fun getDnsLookup_dnsJava(): String {
        val lookup = NsLookupService()
        return "Domain: $domainName\nDnsServer: $dnsServer\n${
            lookup.lookupResponse_dnsjava(
                domainName,
                dnsServer
            )
        }"
    }

    fun getPortScan(result: (String) -> Unit) {
        var temp = "Open: "
        while (curr <= end) {
            val portScanResult = PortScanService().portScan(domainName, curr++)
            if (portScanResult.isNotEmpty())
                temp += "\n  $portScanResult"
        }
        result(temp)
    }

    fun getPortScan(port: Int, result: (String) -> Unit) {
        val res = PortScanService().portScan(domainName, port)
        if (res.isNotEmpty()) {
            result("\n         ${PortScanService().portScan(domainName, port)}")
        } else {
            result("")
        }
    }
}