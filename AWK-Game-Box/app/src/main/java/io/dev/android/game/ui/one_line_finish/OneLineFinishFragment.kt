package io.dev.android.game.ui.one_line_finish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import io.dev.android.game.databinding.FragmentOneLineFinishBinding
import io.dev.android.game.ui.one_line_finish.viewmodel.OneLineFinishViewModel

class OneLineFinishFragment : Fragment() {

    private var _binding: FragmentOneLineFinishBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OneLineFinishViewModel by viewModels()

    companion object {
        const val TAG = "OneLineFinishFragment"
    }

    /* ======================== Lifecycle ======================== */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOneLineFinishBinding.inflate(inflater, container, false)
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