package io.dev.relic.feature.function.agent.gemini

import io.agent.gemini.model.AbsGeminiCell

sealed class GeminiAgentDataState {

    /* Common */

    data object Init : GeminiAgentDataState()

    data object NoChatHistory : GeminiAgentDataState()

    /* Loading */

    data class SendingQuestion(
        private val questionCell: AbsGeminiCell
    ) : GeminiAgentDataState()

    /* Succeed */

    data class SuccessReceivedAnswer(
        private val answerCell: AbsGeminiCell
    ) : GeminiAgentDataState()

    /* Failed */

    data class FailedOrError(
        val errorCode: Int?,
        val errorMessage: String?
    ) : GeminiAgentDataState()

}
