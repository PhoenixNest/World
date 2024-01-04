package io.module.debug.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.common.util.LogUtil
import io.domain.app.AbsBaseFragment
import io.module.debug.activities.machine_learning.MLKitActivity
import io.module.debug.activities.machine_learning.MediaPipeActivity
import io.module.debug.databinding.FragmentIntroMachineLearningBinding

class IntroMachineLearningFragment : AbsBaseFragment() {

    private var _binding: FragmentIntroMachineLearningBinding? = null
    private val binding get() = _binding!!

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
        LogUtil.d(TAG, "[Machine-Learning Debug Intro] Navigate to MLKit.")
        context?.run { MLKitActivity.start(this) }
    }

    private fun navigateToMediaPipeActivity() {
        LogUtil.d(TAG, "[Machine-Learning Debug Intro] Navigate to MediaPipe.")
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