package io.dev.android.game.ui.block_2048

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import io.dev.android.game.databinding.FragmentBlock2048Binding
import io.dev.android.game.ui.block_2048.viewmodel.Block2048ViewModel

class Block2048Fragment : Fragment() {

    private var _binding: FragmentBlock2048Binding? = null
    private val binding get() = _binding!!

    private val viewModel: Block2048ViewModel by viewModels()

    companion object {
        const val TAG = "Block2048Fragment"
    }

    /* ======================== Lifecycle ======================== */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBlock2048Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Avoid OOM
        _binding = null
    }

    /* ======================== Logical ======================== */

    private fun initialization() {
        initUi()
    }

    /* ======================== Ui ======================== */

    private fun initUi() {

    }
}