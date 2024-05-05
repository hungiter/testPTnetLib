package com.example.testkt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testkt.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TestFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set initial text value
        binding.pageLoad.text = "${arguments?.getString("pageLoadResult") ?: ""}"
        binding.dnsApi.text = "${arguments?.getString("dnsResult_api") ?: ""}"
        binding.dnsJava.text = "${arguments?.getString("dnsResult_dnsJava") ?: ""}"
        binding.port.text = "${arguments?.getString("port") ?: ""}"
        binding.portScan.text = "${arguments?.getString("portScanResult") ?: ""}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}