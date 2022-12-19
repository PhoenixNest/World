package io.dev.android.game.ui.block_2048.core.v1

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import io.dev.android.game.util.LogUtil

class Block2048GridView(
    private val context: Context,
    private val containerLayout: ConstraintLayout,
    private val size: Int
) {
    val mapArray: Array<Array<Block2048GameCube?>> = Array(size) { Array(size) { null } }
    private val moveCubesList: MutableList<Block2048GameCube> = mutableListOf()
    private val removeCubesList: MutableList<Block2048GameCube> = mutableListOf()
    private val newCubesList: MutableList<Triple<Int, Int, Int>> = mutableListOf()

    companion object {
        private const val TAG = "Block2048GridView"

        // Available status of cube
        const val NO_MOVE = 0
        const val MOVE = 1
        const val MERGE = 2
        const val NOTHING_HAPPEN = -1

        // Available input action
        const val ACTION_UP = 0
        const val ACTION_RIGHT = 1
        const val ACTION_DOWN = 2
        const val ACTION_LEFT = 3
    }

    fun initGameMap() {
        for (y in 0 until size) {
            for (x in 0 until size) {
                val cube = mapArray[x][y]
                if (cube != null) {
                    cube.visibility = View.GONE
                    mapArray[x][y] = null
                }
            }
        }
        moveCubesList.clear()
        removeCubesList.clear()
        newCubesList.clear()
    }

    fun getCube(y: Int, x: Int): Block2048GameCube? {
        return mapArray[y][x]
    }

    fun setCube(y: Int, x: Int, cube: Block2048GameCube) {
        mapArray[y][x] = cube
    }

    fun getCubesNumber(): Int {
        var count = 0
        for (y in 0 until size) {
            for (x in 0 until size) {
                if (mapArray[y][x] != null) {
                    count++
                }
            }
        }

        return count
    }

    fun createNewCube(y: Int, x: Int, value: Int) {
        LogUtil.debug(TAG, "[createNewCube]: ($y, $x)")
        val autoTransition: AutoTransition = AutoTransition().apply { duration = 100 }
        val constraintSet = ConstraintSet()
        val newCube = Block2048GameCube(context)
        newCube.id = View.generateViewId()
        newCube.setCurrentPosition(y, x)
        newCube.setValue(value)
        mapArray[y][x] = newCube
        // Add new cube to map container
        containerLayout.addView(newCube)
        // Setup default constrains with new cube
        setDefaultConstraint(constraintSet, newCube.id)
        // Setup constrains for new cube with specify position
        setPositionConstraint(constraintSet, newCube.id, y, x)
        // load animation for new cube
        TransitionManager.beginDelayedTransition(containerLayout, autoTransition)
        // Apply change to map container layout
        constraintSet.applyTo(containerLayout)
    }

    private fun setDefaultConstraint(constraintSet: ConstraintSet, id: Int) {
        constraintSet.constrainHeight(id, 0)
        constraintSet.constrainWidth(id, 0)
        constraintSet.setDimensionRatio(id, "1:1")
    }

    @SuppressLint("DiscouragedApi")
    private fun setPositionConstraint(constraintSet: ConstraintSet, id: Int, y: Int, x: Int) {
        val identifierName = "cube_${y}_${x}"
        val constrainId: Int = context.resources.getIdentifier(identifierName, "id", context.packageName)
        constraintSet.connect(id, ConstraintSet.LEFT, constrainId, ConstraintSet.LEFT, 0)
        constraintSet.connect(id, ConstraintSet.RIGHT, constrainId, ConstraintSet.RIGHT, 0)
        constraintSet.connect(id, ConstraintSet.TOP, constrainId, ConstraintSet.TOP, 0)
        constraintSet.connect(id, ConstraintSet.BOTTOM, constrainId, ConstraintSet.BOTTOM, 0)
    }

    private fun getVector(direction: Int): Pair<Int, Int> {
        return when (direction) {
            ACTION_UP -> Pair(-1, 0)
            ACTION_DOWN -> Pair(1, 0)
            ACTION_LEFT -> Pair(0, -1)
            ACTION_RIGHT -> Pair(0, 1)
            else -> throw IllegalArgumentException("$TAG getVector error")
        }
    }

    private fun getTraversals(vector: Pair<Int, Int>): Pair<IntArray, IntArray> {
        val arrayY = IntArray(size) { i -> i }
        val arrayX = IntArray(size) { i -> i }
        if (vector.first == 1) {
            arrayY.reverse()
        } else if (vector.second == 1) {
            arrayX.reverse()
        }
        return Pair(arrayY, arrayX)
    }

    fun move(direction: Int): Int {
        val vector: Pair<Int, Int> = getVector(direction)
        val traversal: Pair<IntArray, IntArray> = getTraversals(vector)

        // Initialize value of other position
        for (y in traversal.first) {
            for (x in traversal.second) {
                val gameCube = mapArray[y][x]
                if (gameCube != null) {
                    gameCube.currentPosition = Pair(y, x)
                    gameCube.isMerged = false
                    gameCube.isMoved = false
                    gameCube.nextPosition = gameCube.currentPosition
                }
            }
        }

        // Initialize value of MOVE, MERGE
        for (y in traversal.first) {
            for (x in traversal.second) {
                val gameCube = mapArray[y][x] ?: continue
                when (shiftPosition(gameCube, Pair(y, x), vector)) {
                    MOVE -> {
                        val startPosition: Pair<Int, Int> = gameCube.currentPosition
                        val targetPosition: Pair<Int, Int> = gameCube.nextPosition
                        // Update end point
                        mapArray[targetPosition.first][targetPosition.second] = gameCube
                        // Update the starting point
                        mapArray[startPosition.first][startPosition.second] = null
                        LogUtil.debug(TAG, "[MOVE] (${startPosition.second}, ${startPosition.first}) -> (${targetPosition.second}, ${targetPosition.first})")
                    }
                    MERGE -> {
                        val startPosition: Pair<Int, Int> = gameCube.currentPosition
                        val targetPosition: Pair<Int, Int> = gameCube.nextPosition
                        // Update the starting point
                        mapArray[startPosition.first][startPosition.second] = null
                        LogUtil.debug(TAG, "[MERGE] (${startPosition.second}, ${startPosition.first}) -> (${targetPosition.second}, ${targetPosition.first})")
                    }
                }
            }
        }

        if (isNothingMerge()) {
            return NOTHING_HAPPEN
        }

        animMoveCubes()
        animRemoveTiles()
        return animNewCubes()
    }

    private fun shiftMove(cube: Block2048GameCube) {
        moveCubesList.add(cube)
        cube.isMoved = true
    }

    /**
     * Merge cube
     * */
    private fun shiftMerge(startCube: Block2048GameCube, targetCube: Block2048GameCube) {
        moveCubesList.add(startCube)
        removeCubesList.add(startCube)
        removeCubesList.add(targetCube)
        targetCube.isMerged = true
        val triple = Triple(targetCube.nextPosition.first, targetCube.nextPosition.second, startCube.getValue() * 2)
        newCubesList.add(triple)
        startCube.nextPosition = targetCube.nextPosition
    }

    private fun shiftPosition(currentCube: Block2048GameCube, position: Pair<Int, Int>, vector: Pair<Int, Int>): Int {
        var previousPosition: Pair<Int, Int>
        var targetPosition: Pair<Int, Int>
        targetPosition = position
        do {
            previousPosition = targetPosition
            val y = previousPosition.first + vector.first
            val x = previousPosition.second + vector.second
            targetPosition = Pair(y, x)
            currentCube.setNextPosition(previousPosition.first, previousPosition.second)
        } while (checkIsInBoundary(targetPosition) && checkIsAvailableMove(targetPosition))

        if (!checkIsInBoundary(targetPosition)) {
            if (currentCube.currentPosition != previousPosition) {
                shiftMove(currentCube)
                return MOVE
            }
            // Skip current process when stay in the last position
            return NO_MOVE
        }

        val targetCube: Block2048GameCube? = mapArray[targetPosition.first][targetPosition.second]
        if (currentCube.currentPosition == previousPosition) {
            return if (currentCube.getValue() != targetCube?.getValue()) {
                // Skip current process when stay in the last position
                NO_MOVE
            } else {
                // MERGE
                shiftMerge(currentCube, targetCube)
                MERGE
            }
        }

        // Check if targetCube value is different with currentCube
        return if (currentCube.getValue() != targetCube?.getValue()) {
            shiftMove(currentCube)
            MOVE
        } else {
            // Check if targetCube is already merge
            if (targetCube.isMerged) {
                shiftMove(currentCube)
                MOVE
            } else {
                shiftMerge(currentCube, targetCube)
                MERGE
            }
        }
    }

    private fun animMoveCubes() {
        val autoTransition: AutoTransition = AutoTransition().apply { duration = 100 }
        val constraintSet = ConstraintSet()
        for (cube in moveCubesList) {
            val startPosition = cube.currentPosition
            val targetPosition = cube.nextPosition
            LogUtil.debug(TAG, "[animMoveCubes]: (${startPosition.second}, ${startPosition.first}) -> (${targetPosition.second}, ${targetPosition.first})")
            val targetPositionY = cube.nextPosition.first
            val targetPositionX = cube.nextPosition.second
            setDefaultConstraint(constraintSet, cube.id)
            setPositionConstraint(constraintSet, cube.id, targetPositionY, targetPositionX)
            TransitionManager.beginDelayedTransition(containerLayout, autoTransition)
            cube.currentPosition = Pair(targetPositionY, targetPositionX)
        }

        constraintSet.applyTo(containerLayout)
        moveCubesList.clear()
    }

    private fun animRemoveTiles() {
        val autoTransition: AutoTransition = AutoTransition().apply { duration = 100 }
        val constraintSet = ConstraintSet()
        for (cube in removeCubesList) {
            val startPosition = cube.currentPosition
            val targetPosition = cube.nextPosition
            LogUtil.debug(TAG, "[animRemoveTiles]: (${startPosition.second}, ${startPosition.first}) -> (${targetPosition.second}, ${targetPosition.first})")
            val targetPositionY = cube.nextPosition.first
            val targetPositionX = cube.nextPosition.second
            mapArray[targetPositionY][targetPositionX] = null
            cube.animate().alpha(0.0F).duration = 100
            TransitionManager.beginDelayedTransition(containerLayout, autoTransition)
        }

        constraintSet.applyTo(containerLayout)
        for (cube in removeCubesList) {
            cube.visibility = View.GONE
            TransitionManager.beginDelayedTransition(containerLayout, autoTransition)
        }
        removeCubesList.clear()
    }

    private fun animNewCubes(): Int {
        var score = 0
        for (cube in newCubesList) {
            createNewCube(cube.first, cube.second, cube.third)
            score += cube.third
        }
        newCubesList.clear()
        return score
    }

    private fun checkIsInBoundary(targetPosition: Pair<Int, Int>): Boolean {
        if (targetPosition.first in 0 until size) {
            if (targetPosition.second in 0 until size) {
                return true
            }
        }
        return false
    }

    private fun checkIsAvailableMove(targetPosition: Pair<Int, Int>): Boolean {
        if (mapArray[targetPosition.first][targetPosition.second] == null) {
            return true
        }
        return false
    }

    private fun isNothingMerge(): Boolean {
        if (moveCubesList.count() + removeCubesList.count() + newCubesList.count() == 0) {
            return true
        }
        return false
    }
}