package io.dev.relic.feature.fragments.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import io.dev.relic.R
import io.dev.relic.databinding.FragmentIntroMapBinding
import io.dev.relic.feature.fragments.AbsBaseFragment
import io.dev.relic.global.utils.LogUtil

class IntroMapFragment : AbsBaseFragment() {

    private var _binding: FragmentIntroMapBinding? = null
    private val binding: FragmentIntroMapBinding get() = _binding!!

    companion object {
        private const val TAG = "IntroMapFragment"
    }

    /* ======================== override ======================== */

    override fun initialization(savedInstanceState: Bundle?) {
        //
    }

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initUi(savedInstanceState: Bundle?) {
        setupUi()
    }

    /* ======================== Logical ======================== */

    private fun navigateToAMapFragment() {
        LogUtil.debug(TAG, "[Map Debug Intro] Navigate to AMap.")
        findNavController().navigate(R.id.action_introMapFragment_to_AMapFragment)
    }

    private fun navigateToTomTomMapFragment() {
        LogUtil.debug(TAG, "[Map Debug Intro] Navigate to TomTomMap.")
        findNavController().navigate(R.id.action_introMapFragment_to_tomTomMapFragment)
    }

    /* ======================== Ui ======================== */

    private fun setupUi() {
        binding.apply {
            cardViewAMap.setOnClickListener {
                navigateToAMapFragment()
            }

            cardViewTomtom.setOnClickListener {
                navigateToTomTomMapFragment()
            }
        }
    }

}