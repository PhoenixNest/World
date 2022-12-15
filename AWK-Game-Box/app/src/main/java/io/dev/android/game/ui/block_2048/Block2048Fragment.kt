package io.dev.android.game.ui.block_2048

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.dev.android.game.databinding.FragmentBlock2048Binding
import io.dev.android.game.ui.block_2048.core.v1.Block2048GridView
import io.dev.android.game.ui.block_2048.core.v1.Block2048GridView.Companion.NOTHING_HAPPEN
import io.dev.android.game.ui.block_2048.core.v1.Block2048SwipeTouchListener
import io.dev.android.game.ui.block_2048.viewmodel.Block2048ViewModel
import kotlin.random.Random

class Block2048Fragment : Fragment() {

    private var _binding: FragmentBlock2048Binding? = null
    private val binding get() = _binding!!

    private val viewModel: Block2048ViewModel by lazy {
        ViewModelProvider(requireActivity())[Block2048ViewModel::class.java]
    }

    private var gameMapView: Block2048GridView? = null

    companion object {
        const val TAG = "Block2048Fragment"

        private const val MAP_SIZE = 4
        private const val INIT_BLOCK_COUNT = 2
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
        // initUi()
    }

    private fun createRandomNewCube(count: Int) {
        var newCubeCount = 0
        do {
            val y: Int = Random.nextInt(0, MAP_SIZE)
            val x: Int = Random.nextInt(0, MAP_SIZE)
            if (gameMapView?.getCube(y, x) == null) {
                gameMapView?.createNewCube(y, x, 2)
                newCubeCount++
            }
        } while (newCubeCount < count)
    }

    private fun updateScore(score: Int) {
        if (score == NOTHING_HAPPEN) {
            if (checkGameOver()) {
                startNewGame()
            }
            return
        }

        viewModel.apply {
            currentScore += score
            binding.apply {
                textViewCurrentScore.text = currentScore.toString()
                if (currentScore > finalScore) {
                    finalScore = currentScore
                    textViewHighestScore.text = finalScore.toString()
                }
                // Update Highest Score
                saveHighestScore(finalScore)
            }

            // Create new cube
            createRandomNewCube(1)
        }
    }

    private fun checkGameOver(): Boolean {
        gameMapView?.let { view ->
            for (x in 0 until MAP_SIZE) {
                for (y in 0 until MAP_SIZE - 1) {
                    if (y + 1 < MAP_SIZE) {
                        if (view.mapArray[y][x]?.getValue() == view.mapArray[y + 1][x]?.getValue()) {
                            return false
                        }
                    }

                    if (x + 1 < MAP_SIZE) {
                        if (view.mapArray[y][x]?.getValue() == view.mapArray[y][x + 1]?.innerValue) {
                            return false
                        }
                    }
                }
            }
        }

        return true
    }

    private fun startNewGame() {
        viewModel.currentScore = 0
        binding.textViewCurrentScore.text = viewModel.currentScore.toString()
        gameMapView?.let { view ->
            view.initGameMap()
            createRandomNewCube(INIT_BLOCK_COUNT)
            binding.touchArea.setOnTouchListener(object : Block2048SwipeTouchListener(
                context = requireContext(),
                mapView = view,
                moveCallback = { score -> updateScore(score) }
            ) {})
        }
    }

    /* ======================== Ui ======================== */

    private fun initUi() {
        initGameMap()
        setupHighestScoreTextView()
    }

    private fun initGameMap() {
        binding.apply {
            gameMapView = Block2048GridView(
                context = requireContext(),
                containerLayout = constraintLayoutGameContainer,
                size = MAP_SIZE
            )
            startNewGame()
        }
    }

    private fun setupHighestScoreTextView() {
        viewModel.apply {
            highestScore.observe(viewLifecycleOwner) { score ->
                finalScore = score
                binding.textViewHighestScore.text = score.toString()
            }
        }
    }
}