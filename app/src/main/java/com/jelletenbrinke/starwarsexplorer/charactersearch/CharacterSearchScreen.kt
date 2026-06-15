package com.jelletenbrinke.starwarsexplorer.charactersearch

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jelletenbrinke.starwarsexplorer.domain.model.Character
import com.jelletenbrinke.starwarsexplorer.domain.model.Film
import com.jelletenbrinke.starwarsexplorer.ui.shared.CharacterRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterSearchScreen(
    onBackClick: () -> Unit,
    onCharacterClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    // TODO: Implement view model.
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { TextField(value = "", onValueChange = {}, placeholder = { Text("Search for a star wars character...") }) },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding
        ) {
            item {
                // TODO: replace with viewModel etc.
                val dummyLukeSkywalker = Character(
                    url = "https://swapi.py4e.com/api/people/1/",
                    name = "Luke Skywalker (dummy for demo)",
                    height = "172",
                    birthYear = "2077",
                    films = listOf(
                        Film(url = "https://dummydata","A New Hope"),
                        Film(url = "https://dummydata","The Empire Strikes Back"),
                        Film(url = "https://dummydata","Return of the Jedi"),
                        Film(url = "https://dummydata", "Revenge of the Sith"),
                        Film(url = "https://dummydata", "The Force Awakens")
                    ),
                    homeworld = null,
                    gender = "n/a"
                )
                CharacterRow(
                    character = dummyLukeSkywalker,
                    onClick = { onCharacterClick(dummyLukeSkywalker.url) },
                )
            }
        }
    }
}