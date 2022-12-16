package io.dev.android.game.ui.block_2048.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import io.dev.android.game.databinding.DialogBlock2048GameOverBinding
import io.dev.android.game.ui.base.dialog.FullScreenDialog

class Block2048GameOverDialog : FullScreenDialog() {

    private var _binding: DialogBlock2048GameOverBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<Block2048GameOverDialogArgs>()
    private var score: Int = 0

    companion object {
        private const val ARG_SCORE = "score"

        @JvmStatic
        fun newInstance(score: Int): Block2048GameOverDialog {
            return Block2048GameOverDialog().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SCORE, score)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            score = it.getInt(ARG_SCORE)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DialogBlock2048GameOverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.score = args.score
    }
}