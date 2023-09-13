package io.dev.relic.feature.fragments.machine_learning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import io.dev.relic.R
import io.dev.relic.databinding.FragmentIntroMachineLearningBinding
import io.dev.relic.feature.fragments.AbsBaseFragment
import io.dev.relic.global.utils.LogUtil

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

    private fun navigateToMLKitFragment() {
        LogUtil.debug(TAG, "[Machine-Learning Debug Intro] Navigate to MLKit.")
        findNavController().navigate(R.id.action_introMachineLearningFragment_to_MLKitFragment)
    }

    private fun navigateToMediaPipeFragment() {
        LogUtil.debug(TAG, "[Machine-Learning Debug Intro] Navigate to MediaPipe.")
        findNavController().navigate(R.id.action_introMachineLearningFragment_to_mediaPipeFragment)
    }

    /* ======================== Ui ======================== */

    private fun setupUi() {
        binding.apply {
            cardViewMLKit.setOnClickListener {
                navigateToMLKitFragment()
            }

            cardViewMediaPipe.setOnClickListener {
                navigateToMediaPipeFragment()
            }
        }
    }

}