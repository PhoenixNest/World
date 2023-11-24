package io.dev.relic.feature.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.dev.relic.databinding.FragmentIntroMapBinding
import io.dev.relic.feature.activities.map.AMapActivity
import io.dev.relic.feature.activities.map.TomTomMapActivity
import io.module.common.util.LogUtil

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

    private fun navigateToAMapActivity() {
        LogUtil.debug(TAG, "[Map Debug Intro] Navigate to AMap.")
        context?.run { AMapActivity.start(this) }
    }

    private fun navigateToTomTomMapActivity() {
        LogUtil.debug(TAG, "[Map Debug Intro] Navigate to TomTomMap.")
        context?.run { TomTomMapActivity.start(this) }
    }

    /* ======================== Ui ======================== */

    private fun setupUi() {
        binding.apply {
            cardViewAMap.setOnClickListener {
                navigateToAMapActivity()
            }

            cardViewTomtom.setOnClickListener {
                navigateToTomTomMapActivity()
            }
        }
    }

}