package io.dev.android.game.ui.one_line_finish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import io.dev.android.game.data.db.one_line_finish.model.OneLineFinishRoadModel
import io.dev.android.game.databinding.FragmentOneLineFinishBinding
import io.dev.android.game.ui.one_line_finish.core.v1.OneLineGridView
import io.dev.android.game.ui.one_line_finish.data.OneLineFinishGameData
import io.dev.android.game.ui.one_line_finish.viewmodel.OneLineFinishViewModel
import io.dev.android.game.util.LogUtil

class OneLineFinishFragment : Fragment() {

    private var _binding: FragmentOneLineFinishBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OneLineFinishViewModel by viewModels()

    private var firstPassed: Boolean = false
    private var isHelping: Boolean = false

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
        // Game Map
        OneLineFinishGameData.apply {
            currentLevelLiveData.observe(viewLifecycleOwner) { level ->
                LogUtil.debug(TAG, "currentLevel: $level")
                val roadModel: OneLineFinishRoadModel = roadValuesList[level]
                LogUtil.debug(TAG, "roadModel info: $roadModel")
                initGameMap(roadModel)
            }
        }
    }

    private fun initGameMap(roadModel: OneLineFinishRoadModel) {
        binding.gridViewOneLineFinish.apply {
            refreshMapGrid()
            initMapGrid(roadModel, object : OneLineGridView.OneLineFinishListener {
                override fun initGridRoad(initRows: Int, initColumns: Int) {

                }

                override fun stopGettingRoad(): Boolean {
                    return true
                }

                override fun saveProgress(roadModel: OneLineFinishRoadModel?, passedPosition: List<Int>) {

                }

                override fun passed(roadModel: OneLineFinishRoadModel?) {
                    if (roadModel == null) {
                        return
                    }

                    if (!firstPassed) {
                        firstPassed = true
                        // viewModel.insertData(OneLineFinishEntity(roadModel))
                    }

                    TransitionManager.beginDelayedTransition(binding.gridViewOneLineFinish, AutoTransition())
                }

                override fun setIsHelping(isHelping: Boolean) {
                    this@OneLineFinishFragment.isHelping = isHelping
                }

                override fun isHelping(): Boolean {
                    return this@OneLineFinishFragment.isHelping
                }
            })
        }
    }
}