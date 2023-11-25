package io.dev.relic.feature.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.dev.relic.databinding.FragmentIntroMachineLearningBinding
import io.dev.relic.feature.activities.machine_learning.MLKitActivity
import io.dev.relic.feature.activities.machine_learning.MediaPipeActivity
import io.common.util.LogUtil

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
        setupUi()
    }

    /* ======================== Logical ======================== */

    private fun navigateToMLKitActivity() {
        LogUtil.debug(TAG, "[Machine-Learning Debug Intro] Navigate to MLKit.")
        context?.run { MLKitActivity.start(this) }
    }

    private fun navigateToMediaPipeActivity() {
        LogUtil.debug(TAG, "[Machine-Learning Debug Intro] Navigate to MediaPipe.")
        context?.run { MediaPipeActivity.start(this) }
    }

    /* ======================== Ui ======================== */

    private fun setupUi() {
        binding.apply {
            cardViewMLKit.setOnClickListener {
                navigateToMLKitActivity()
            }

            cardViewMediaPipe.setOnClickListener {
                navigateToMediaPipeActivity()
            }
        }
    }

}