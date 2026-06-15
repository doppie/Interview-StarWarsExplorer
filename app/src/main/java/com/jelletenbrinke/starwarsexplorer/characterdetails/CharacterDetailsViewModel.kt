package com.jelletenbrinke.starwarsexplorer.characterdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jelletenbrinke.starwarsexplorer.domain.usecases.FetchCharacterDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val fetchCharacterDetailsUseCase: FetchCharacterDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterDetailsUiState())
    val uiState: StateFlow<CharacterDetailsUiState> = _uiState.asStateFlow()

    fun loadCharacter(characterUrl: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            fetchCharacterDetailsUseCase.fetchDetails(characterUrl)
                .onCompletion {
                    _uiState.update { it.copy(isLoading = false) }
                }
                .catch { error ->
                    _uiState.update { it.copy(error = error.message) }
                }
                .collect { result ->
                    result.onSuccess { character ->
                        _uiState.update { it.copy(character = character) }
                    }.onFailure { error ->
                        _uiState.update { it.copy(error = error.message) }
                    }
                }
        }
    }

    fun onErrorDismissed() {
        _uiState.update { it.copy(error = null) }
    }
}