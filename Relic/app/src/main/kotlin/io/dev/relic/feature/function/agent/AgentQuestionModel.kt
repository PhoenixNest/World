package io.dev.relic.feature.function.agent

import androidx.annotation.StringRes
import io.dev.relic.R

data class AgentQuestionModel(
    @StringRes val strResId: Int
) {
    companion object {
        fun getRandomQuestions(): List<AgentQuestionModel> {
            return buildInQuestionsList().shuffled()
        }

        private fun buildInQuestionsList(): List<AgentQuestionModel> {
            return listOf(
                AgentQuestionModel(R.string.agent_question_1),
                AgentQuestionModel(R.string.agent_question_2),
                AgentQuestionModel(R.string.agent_question_3),
                AgentQuestionModel(R.string.agent_question_4),
                AgentQuestionModel(R.string.agent_question_5),
                AgentQuestionModel(R.string.agent_question_6),
            )
        }
    }
}