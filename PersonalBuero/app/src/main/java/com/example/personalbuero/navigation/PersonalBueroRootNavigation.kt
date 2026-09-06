package com.example.personalbuero.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.personalbuero.model.PersonalBueroViewModel
import com.example.personalbuero.screens.PersonalBueroDetailsScreen
import com.example.personalbuero.screens.PersonalBueroListScreen

@Composable
fun PersonalBueroRootNavigation(
    viewModel: PersonalBueroViewModel
) {

    val backStack = rememberNavBackStack(PersonalBueroNavKey.PersonalBueroList)

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<PersonalBueroNavKey.PersonalBueroList> {
                PersonalBueroListScreen(

                )
            }
            entry<PersonalBueroNavKey.PersonalDetail> { id ->
                PersonalBueroDetailsScreen(

                )
            }
        }
    )
}