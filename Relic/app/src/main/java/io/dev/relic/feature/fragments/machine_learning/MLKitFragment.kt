package io.dev.relic.feature.fragments.machine_learning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.dev.relic.databinding.FragmentMlkitBinding
import io.dev.relic.feature.fragments.AbsBaseFragment

class MLKitFragment : AbsBaseFragment() {

    private var _binding: FragmentMlkitBinding? = null
    private val binding: FragmentMlkitBinding get() = _binding!!

    companion object {
        private const val TAG = "MLKitFragment"
    }

    override fun initialization(savedInstanceState: Bundle?) {
    }

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMlkitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initUi(savedInstanceState: Bundle?) {
    }
}