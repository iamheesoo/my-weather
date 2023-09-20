package com.example.myweather.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseMviViewModel<State : UiState, Event : UiEvent, Effect : UiEffect> : ViewModel() {

    private val createState: State by lazy { createState() }
    abstract fun createState(): State

    val state: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(createState)
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    private val _loadingState: MutableState<BaseContract.LoadState> =
        mutableStateOf(BaseContract.LoadState.LOADING)

    private val _loadingEvent: MutableSharedFlow<BaseContract.LoadEvent> = MutableSharedFlow()

    init {
        subscribeEvents()
    }

    /**
     * viewModel이 생성되면 screen의 event를 받을 준비를 한다
     */
    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }

    abstract fun handleEvent(event: Event)

    /**
     * VM -> Screen 단발성 이벤트 전송
     */
    protected fun sendEffect(effect: () -> Effect) {
        val effectValue = effect()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    /**
     * Screen -> VM 유저의 액션 전송
     */
    fun sendEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun setState(reduce: State.() -> State) {
        val newState = state.reduce()
        _uiState.value = newState
    }

}

interface UiState
interface UiEvent
interface UiEffect