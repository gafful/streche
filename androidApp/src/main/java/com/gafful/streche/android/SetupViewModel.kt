package com.gafful.streche.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Kermit
import com.gafful.streche.TriviaModel
import com.gafful.streche.TriviaViewState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class SetupViewModel : ViewModel(), KoinComponent {
    private val log: Kermit by inject { parametersOf("BreedViewModel") }
    private val scope = viewModelScope
    private val triviaModel: TriviaModel = TriviaModel()//TODO Inject...?
    private val _triviaStateFlow: MutableStateFlow<TriviaViewState> = MutableStateFlow(
        TriviaViewState.Initial
    )
    private val _triviaLiveData: MutableLiveData<TriviaViewState> = MutableLiveData(
        TriviaViewState.Initial
    )

    val triviaStateFlow: StateFlow<TriviaViewState> = _triviaStateFlow
    val triviaLiveData: LiveData<TriviaViewState> = _triviaLiveData

    init {
        initSession()
    }

//    @ExperimentalSettingsApi
//    @ExperimentalSerializationApi
    @OptIn(FlowPreview::class)
    private fun initSession() {
        scope.launch {
            log.v { "Initializing app ..." }
            triviaModel.initSession().collect {
                log.v { "Update from model ..." }
                log.v { "Update from model ... $it" }
                //_triviaStateFlow.value = it
                _triviaLiveData.value = it
            }

//            flowOf(
//                triviaModel.initSession()
////                breedModel.refreshBreedsIfStale(true),
////                breedModel.getBreedsFromCache()
//            ).flattenMerge().collect { dataState ->
////                if (dataState.loading) {
////                    val temp = _breedStateFlow.value.copy(loading = true)
////                    _triviaStateFlow.value = temp
////                } else {
////                    _triviaStateFlow.value = dataState
////                }
//            }
        }
    }
}