package com.gafful.streche.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Kermit
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class MainActivityViewModel : ViewModel(), KoinComponent {
    private val log: Kermit by inject { parametersOf("BreedViewModel") }
    private val scope = viewModelScope
    private val breedModel: MainActivityViewModel = MainActivityViewModel()
//    private val _breedStateFlow: MutableStateFlow<DataState<ItemDataSummary>> = MutableStateFlow(
//        DataState(loading = true)
//    )
//
//    val breedStateFlow: StateFlow<DataState<ItemDataSummary>> = _breedStateFlow

    init {
        observeBreeds()
    }

    @OptIn(FlowPreview::class)
    private fun observeBreeds() {
        scope.launch {
            log.v { "getBreeds: Collecting Things" }
//            flowOf(
//                breedModel.refreshBreedsIfStale(true),
//                breedModel.getBreedsFromCache()
//            ).flattenMerge().collect { dataState ->
//                if (dataState.loading) {
//                    val temp = _breedStateFlow.value.copy(loading = true)
//                    _breedStateFlow.value = temp
//                } else {
//                    _breedStateFlow.value = dataState
//                }
//            }
        }
    }
}