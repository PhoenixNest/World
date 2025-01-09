package io.dev.relic.feature.pages.studio.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.common.ext.ViewModelExt.setState
import io.common.util.LogUtil
import io.data.dto.maxim.MaximDTO
import io.data.mappers.MaximDataMapper.toModel
import io.data.model.NetworkResult
import io.dev.relic.feature.function.maxim.MaximDataState
import io.domain.use_case.maxim.MaximUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudioMaximViewModel @Inject constructor(
    application: Application,
    private val maximUseCase: MaximUseCase
) : AndroidViewModel(application) {

    /**
     * The maxim data flow of recommend.
     * */
    private val maximDataStateFlow = MutableStateFlow<MaximDataState>(MaximDataState.Init)

    companion object {
        private const val TAG = "Studio_Maxim"
    }

    init {
        fetchRandomMaxim()
    }

    fun getMaximDataStateFlow(): StateFlow<MaximDataState> {
        return maximDataStateFlow
    }

    fun fetchRandomMaxim() {
        viewModelScope.launch {
            maximUseCase.getRandomMaxim.invoke().stateIn(
                scope = this,
                started = SharingStarted.WhileSubscribed(5 * 1000L),
                initialValue = NetworkResult.Loading()
            ).collect { result ->
                handleRandomData(result = result)
            }
        }
    }

    private fun handleRandomData(result: NetworkResult<MaximDTO>) {
        when (result) {
            is NetworkResult.Loading -> {
                LogUtil.d(TAG, "[Handle Random Data] Loading...")
                setState(maximDataStateFlow, MaximDataState.Fetching)
            }

            is NetworkResult.Success -> {
                result.data?.also { dto ->
                    LogUtil.d(TAG, "[Handle Random Data] Succeed, data: $dto")
                    val model = dto.toModel()
                    setState(maximDataStateFlow, MaximDataState.FetchSucceed(model))
                } ?: {
                    LogUtil.d(TAG, "[Handle Random Data] Succeed without data")
                    setState(maximDataStateFlow, MaximDataState.NoMaximData)
                }
            }

            is NetworkResult.Failed -> {
                val errorCode = result.code
                val errorMessage = result.message
                LogUtil.e(TAG, "[Handle Random Data] Failed, ($errorCode, $errorMessage)")
                val newState = MaximDataState.FetchFailed(errorCode, errorMessage)
                setState(maximDataStateFlow, newState)
            }
        }
    }
}