package com.jelletenbrinke.starwarsexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jelletenbrinke.starwarsexplorer.characterdetails.CharacterDetailsScreen
import com.jelletenbrinke.starwarsexplorer.characterlist.CharacterListScreen
import com.jelletenbrinke.starwarsexplorer.ui.theme.StarWarsExplorerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StarWarsExplorerTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Destinations.CharacterListPage
                ) {
                    composable<Destinations.CharacterListPage> {
                        CharacterListScreen(
                            onCharacterClick = { characterUrl ->
                                navController.navigate(Destinations.CharacterDetailsPage(characterUrl))
                            },
                            onSearchClick = {
                                navController.navigate(Destinations.CharacterSearchPage)
                            }
                        )
                    }
                    composable<Destinations.CharacterDetailsPage> { backStackEntry ->
                        val details = backStackEntry.toRoute<Destinations.CharacterDetailsPage>()
                        CharacterDetailsScreen(
                            characterUrl = details.characterUrl,
                            onBackClick = { navController.popBackStack() },
                        )
                    }
                    composable<Destinations.CharacterSearchPage> {
                        Text(text = "Character Search")
                    }
                }
            }
        }
    }

    private sealed interface Destinations {
        @Serializable
        object CharacterListPage

        @Serializable
        data class CharacterDetailsPage(val characterUrl: String)

        @Serializable
        object CharacterSearchPage

    }
}


