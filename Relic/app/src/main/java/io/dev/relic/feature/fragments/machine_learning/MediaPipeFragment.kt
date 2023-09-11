package io.dev.relic.feature.fragments.machine_learning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.dev.relic.databinding.FragmentMediapipeBinding
import io.dev.relic.feature.fragments.AbsBaseFragment

class MediaPipeFragment : AbsBaseFragment() {

    private var _binding: FragmentMediapipeBinding? = null
    private val binding: FragmentMediapipeBinding get() = _binding!!

    companion object {
        private const val TAG = "MediaPipeFragment"
    }

    override fun initialization(savedInstanceState: Bundle?) {
    }

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMediapipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initUi(savedInstanceState: Bundle?) {
    }

}