package io.dev.relic.feature.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class AbsBaseFragment : Fragment() {

    companion object {
        private const val TAG: String = "AbsBaseFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initialization(savedInstanceState)
        return bindView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        initUi(savedInstanceState)
    }

    /* ======================== Logical ======================== */

    abstract fun initialization(savedInstanceState: Bundle?)

    /* ======================== Ui ======================== */

    abstract fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View

    abstract fun initUi(savedInstanceState: Bundle?)

}