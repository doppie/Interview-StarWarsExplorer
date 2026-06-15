package com.jelletenbrinke.starwarsexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jelletenbrinke.starwarsexplorer.characterlist.CharacterListScreen
import com.jelletenbrinke.starwarsexplorer.ui.theme.StarWarsExplorerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StarWarsExplorerTheme {
                // TODO: implement navigation.
                CharacterListScreen(
                    onCharacterClick = {
                        // TODO: navigate to the character detail screen.
                    }
                )
            }
        }
    }
}