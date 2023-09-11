package io.dev.relic.feature.fragments.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.dev.relic.databinding.FragmentIntroMapBinding
import io.dev.relic.feature.fragments.AbsBaseFragment

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
        //
    }

}