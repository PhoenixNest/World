package io.dev.relic.feature.fragments.machine_learning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.dev.relic.databinding.FragmentIntroMachineLearningBinding
import io.dev.relic.feature.fragments.AbsBaseFragment

class IntroMachineLearningFragment : AbsBaseFragment() {

    private var _binding: FragmentIntroMachineLearningBinding? = null
    private val binding: FragmentIntroMachineLearningBinding get() = _binding!!

    companion object {
        private const val TAG = "IntroMachineLearningFragment"
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
        _binding = FragmentIntroMachineLearningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initUi(savedInstanceState: Bundle?) {
        //
    }

}