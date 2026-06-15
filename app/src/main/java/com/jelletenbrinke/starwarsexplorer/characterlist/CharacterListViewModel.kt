package com.jelletenbrinke.starwarsexplorer.characterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jelletenbrinke.starwarsexplorer.domain.usecases.FetchCharacterPageUseCase
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
class CharacterListViewModel @Inject constructor(
    private val fetchCharacterPageUseCase: FetchCharacterPageUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterListUiState())
    val uiState: StateFlow<CharacterListUiState> = _uiState.asStateFlow()

    init {
        loadInitialPage()
    }

    fun loadInitialPage() {
        loadCharacterPage(INITIAL_PAGE_URL)
    }

    fun loadNextPage() {
        _uiState.value.nextPageUrl?.let { loadCharacterPage(it) }
    }

    fun onErrorDismissed() {
        _uiState.update { it.copy(error = null) }
    }

    private fun loadCharacterPage(pageUrl: String) {
        // prevent launching multiple requests at the same time.
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            fetchCharacterPageUseCase.fetchPage(pageUrl)
                .onCompletion {
                    _uiState.update { it.copy(isLoading = false) }
                }
                .catch { error ->
                    _uiState.update { it.copy(error = error.message) }
                }
                .collect { result ->
                    result.onSuccess { page ->
                        _uiState.update { currentState ->
                            val existingPageIndex =
                                currentState.pages.indexOfFirst { it.url == page.url }

                            val newPages = if (existingPageIndex != -1) {
                                // Replace the existing page (update in-between)
                                currentState.pages.toMutableList().apply {
                                    set(existingPageIndex, page)
                                }
                            } else {
                                // Append the new page
                                currentState.pages + page
                            }

                            currentState.copy(
                                pages = newPages,
                                nextPageUrl = page.nextPageUrl
                            )
                        }
                    }.onFailure { error ->
                        _uiState.update { currentState ->
                            currentState.copy(error = error.message)
                        }
                    }
                }
        }
    }

    companion object {
        private const val INITIAL_PAGE_URL = "https://swapi.py4e.com/api/people/"
    }

}