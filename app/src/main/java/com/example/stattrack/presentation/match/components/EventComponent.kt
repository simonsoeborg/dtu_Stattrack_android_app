package com.example.stattrack.presentation.match

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stattrack.model.model.Player
import com.example.stattrack.model.model.defaultDummyPlayerData
import com.example.stattrack.presentation.match.components.EventItems
import com.example.stattrack.presentation.ui.theme.PrimaryBlue
import com.example.stattrack.presentation.ui.theme.PrimaryWhite


@Composable
fun EventComponent(
    players: List<Player>,
    newEvent: (event: EventItems) -> Unit
) {
    var expandedEvents by remember { mutableStateOf(false) }
    var selectedIndexEvents by remember { mutableStateOf(0) }
    var expandedPlayers by remember { mutableStateOf(false) }
    var selectedIndexPlayers by remember { mutableStateOf(0) }

    val eventItems = listOf(
        EventItems.Default,
        EventItems.EventGoal,
        EventItems.EventAttempt,
        EventItems.EventSave,
        EventItems.EventAssist,
        EventItems.EventEjection,
        EventItems.EventYellow,
        EventItems.EventRed
    )

    var buttonTitleEvents = "Vælg hændelse"
    var buttonTitlePlayers by remember { mutableStateOf("Vælg spiller") }

    Column( modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {
        Row {
            Column {
                Row( modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .align(Alignment.CenterHorizontally)) {

                    // Player dropdownmenu settings
                    DropdownMenu(
                        expanded = expandedPlayers,
                        selectedIndex = selectedIndexPlayers,
                        eventItems = null,
                        playerItems = players,
                        onSelect = { index ->
                            selectedIndexPlayers = index
                            expandedPlayers = false
                            buttonTitlePlayers = players[selectedIndexPlayers].name
                        },
                        onDismissRequest = {
                            expandedPlayers = false
                            buttonTitlePlayers = "Vælg spiller"
                        }
                    ) {
                        OutlinedButton(
                            onClick = {
                                expandedPlayers = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(PrimaryWhite)
                                .border(BorderStroke(1.dp, PrimaryBlue))
                        ) {
                            Text(
                                text = buttonTitlePlayers,
                                color = PrimaryBlue,
                                maxLines = 1
                            )
                        }
                    }
                }

                Row(modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 2.dp)
                    .align(Alignment.CenterHorizontally)) {

                    // Event drop-down menu settings
                    DropdownMenu(
                        colorSelected = PrimaryBlue,
                        expanded = expandedEvents,
                        selectedIndex = selectedIndexEvents,
                        eventItems = eventItems,
                        playerItems = null,
                        onSelect = { index ->
                            selectedIndexEvents = index
                            expandedEvents = false
                            buttonTitlePlayers = "Vælg spiller"
                            // Callback function to send event and playerId upwards in compose (return)
                            if(selectedIndexEvents!=0) {
                                eventItems[selectedIndexEvents].playerName = players[selectedIndexPlayers].name
                                eventItems[selectedIndexEvents].playerId = players[selectedIndexPlayers].id
                                newEvent(eventItems[selectedIndexEvents])
                            }
                            selectedIndexEvents = 0
                            selectedIndexPlayers = 0
                        },
                        onDismissRequest = {
                            expandedEvents = false
                            buttonTitlePlayers = "Vælg spiller"

                        })
                    {
                        OutlinedButton(
                            onClick = {
                                expandedEvents = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(PrimaryWhite)
                                .border(BorderStroke(1.dp, PrimaryBlue))
                        ) {
                            Text(
                                text = buttonTitleEvents,
                                color = PrimaryBlue,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventComponentPreview() {

    EventComponent(defaultDummyPlayerData, newEvent = { })
}