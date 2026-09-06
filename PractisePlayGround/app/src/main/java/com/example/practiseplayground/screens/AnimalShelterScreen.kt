package com.example.practiseplayground.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.practiseplayground.data.animal.Animal
import com.example.practiseplayground.data.animal.AnimalStatus
import com.example.practiseplayground.data.animal.Species
import com.example.practiseplayground.model.AnimalShelterViewModel
import com.example.practiseplayground.model.AnimalUiState
import com.example.practiseplayground.screens.components.SelectableCard
import kotlinx.coroutines.launch
import kotlin.toString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalShelterScreen(
    viewModel: AnimalShelterViewModel,
    onAnimalClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val nameState = remember {
        TextFieldState()
    }

    val ageState = remember {
        TextFieldState()
    }

    var nameError by remember { mutableStateOf<String?>(null) }
    var ageError by remember { mutableStateOf<String?>(null) }
    var selectedItem by remember { mutableStateOf<String?>(null) }
    var selectedSpecie by remember { mutableStateOf(Species.ALL) }

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
    )

    val scope = rememberCoroutineScope()

    fun validate(): Boolean {
        var valid = true

        nameError = if (nameState.text.isBlank()) "Name darf nicht leer sein" else null
        if (nameError != null) valid = false

        ageError = when {
            ageState.text.isBlank() -> "Alter darf nicht leer sein"
            ageState.text.toString().toIntOrNull() == null -> "Ungültige Zahl"
            else -> null
        }
        if (ageError != null) valid = false

        return valid
    }

    BottomSheetScaffold(
        sheetPeekHeight = 0.dp,
        scaffoldState = scaffoldState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    state = nameState,
                    inputTransformation = InputTransformation {
                        if (!asCharSequence().all { it.isLetter() || it.isWhitespace() }) {
                            revertAllChanges()
                        }
                    },
                    placeholder = { Text("Enter the animals name") }
                )
                Spacer(
                    Modifier.padding(8.dp)
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    state = ageState,
                    inputTransformation = InputTransformation {
                        if (!asCharSequence().all { it.isDigit() }) {
                            revertAllChanges()
                        }
                    },
                    placeholder = { Text("Enter the animals age") }
                )
                Spacer(
                    Modifier.padding(8.dp)
                )
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Species.entries.filter { it != Species.ALL }.forEachIndexed { index, specie ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(index = index, count = Species.entries.size),
                            onClick = { selectedSpecie = specie },
                            selected = specie == selectedSpecie,
                            label = { Text(specie.name.lowercase().replaceFirstChar { it.uppercase() }) }
                        )
                    }
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (validate()) {
                            scope.launch {
                                viewModel.addAnimal(
                                    Animal(
                                        name = nameState.text.toString(),
                                        age = ageState.text.toString().toInt(),
                                        species = selectedSpecie
                                    )
                                )
                                scaffoldState.bottomSheetState.hide()
                                nameState.clearText()
                                ageState.clearText()
                            }
                        }
                    }
                ) {
                    Text("Submit")
                }
            }
        }
    ) { innerPadding ->

    when (val state = uiState) {
        is AnimalUiState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Something went wrong: ${state.error}")
            }
        }

        AnimalUiState.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text("Pls wait, it's loading")
            }
        }

        is AnimalUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LazyColumn{
                        items(state.animals, key = { it.id }) { animal ->
                            SelectableCard(
                                name = animal.name,
                                age = animal.age,
                                specie = animal.species,
                                isSelected = animal.name == selectedItem,
                                onCardClick = {
                                    onAnimalClick(animal.id)
                                },
                                onDelete = {
                                    viewModel.removeAnimal(animal.id)
                                    if (selectedItem == animal.name) selectedItem = null
                                }
                            )
                        }
                    }
                    Button(
                        onClick = {
                            scope.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                        }
                    ) {
                        Text(text = "Add Animal")
                    }
                    Spacer(
                        modifier = Modifier.padding(8.dp)
                    )
                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Species.entries.forEachIndexed { index, filter ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(index = index, count = Species.entries.size),
                                onClick = { viewModel.onFilteredSelected(filter) },
                                selected = filter == ((uiState as? AnimalUiState.Success)?.activeFilter
                                    ?: Species.ALL),
                                label = { Text(filter.name.lowercase().replaceFirstChar { it.uppercase() }) }
                            )
                        }
                    }
                }
            }
        }
    }

}