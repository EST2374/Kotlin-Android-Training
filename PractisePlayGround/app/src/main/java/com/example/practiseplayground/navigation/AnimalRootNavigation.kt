package com.example.practiseplayground.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.practiseplayground.model.AnimalDetailsViewModelFactory
import com.example.practiseplayground.model.AnimalShelterViewModel
import com.example.practiseplayground.screens.AnimalDetailsScreen
import com.example.practiseplayground.screens.AnimalShelterScreen

@Composable
fun AnimalRootNavigation(
    animalShelterViewModel: AnimalShelterViewModel,
) {
    val backStack = rememberNavBackStack(AnimalDestination.AnimalList)


    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<AnimalDestination.AnimalList> {
                AnimalShelterScreen(
                    onAnimalClick = { id -> backStack.add(AnimalDestination.AnimalDetail(id)) },
                    viewModel = animalShelterViewModel
                )
            }
            entry<AnimalDestination.AnimalDetail> { key ->
                AnimalDetailsScreen(
                    onBack = { backStack.removeLastOrNull() },
                    viewModel = viewModel(factory = AnimalDetailsViewModelFactory(animalId = key.id))
                )
            }
        }
    )

}