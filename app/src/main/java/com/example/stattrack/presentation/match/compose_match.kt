package com.example.stattrack.presentation.match

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.stattrack.model.model.*
import com.example.stattrack.presentation.match.components.StopWatchComponent
import com.example.stattrack.presentation.match.components.TeamComponent
import com.example.stattrack.presentation.match.components.EventItems
import com.example.stattrack.presentation.ui.theme.PrimaryBlue
import com.example.stattrack.presentation.ui.theme.Typography


@Composable
fun MatchScreen(matchViewModel: MatchViewModel) {
    val teams: State<List<Team>> = matchViewModel.teams.collectAsState()
    val players: State<List<Player>> = matchViewModel.players.collectAsState()
    val events: State<List<EventData>> = matchViewModel.events.collectAsState()
    val currentMatchData: State<MatchData> = matchViewModel.matchData.collectAsState()
    val timeElapsed: State<String> = matchViewModel.time.collectAsState()
    val isRunning: State<Boolean> = matchViewModel.timerIsRunning.collectAsState()
    val loading = teams.value.isEmpty() || players.value.isEmpty()
    val properTeam1: State<Boolean> = matchViewModel.teamOneCheck.collectAsState()
    val properTeam2: State<Boolean> = matchViewModel.teamTwoCheck.collectAsState()
    val matchStarted: State<Boolean> = matchViewModel.startMatch.collectAsState()

    if(loading ) {
        /* Show loading */
        Box(modifier = Modifier
            .fillMaxSize()
        ) {
            Text(text = "Loading", color= PrimaryBlue, fontSize = 72.sp)
            Text(
                text= "Dette kan være fordi du ikke har tilføjet spillere til dit hold," +
                        "eller ikke oprettet et hold endnu.",
                color = PrimaryBlue,
                fontSize = Typography.body1.fontSize
            )
        }
    } else {
    MatchScreenContent(
        teams.value,
        currentMatchData.value,
        players.value,
        events.value,
        properTeam1.value,
        properTeam2.value,
        timeElapsed = timeElapsed.value,
        isRunning = isRunning.value,
        newEvent = { matchViewModel.insertEvent((it)) },
        setTeamOneName = { matchViewModel.setTeamOneName(it) },
        setTeamTwoName = { matchViewModel.setTeamTwoName(it) },
        onTeamTwoScore = { matchViewModel.setTeamTwoScore(it)},
        onPlayPressed = { matchViewModel.onPlayPressed() },
        onStopPressed = { matchViewModel.onStopPressed() },
        team1check = { matchViewModel.teamOneCheck(it)},
        team2check = { matchViewModel.teamTwoCheck(it)},
        matchStarted = matchStarted.value
    )
    }
}

@Composable
fun MatchScreenContent(
    teams: List<Team>,
    currentMatchData: MatchData,
    players: List<Player>,
    events: List<EventData>,
    properTeam1 : Boolean,
    properTeam2 : Boolean,
    timeElapsed: String,
    isRunning: Boolean,
    newEvent: (event: EventItems) -> Unit,
    setTeamOneName: (teamId: Int) -> Unit,
    setTeamTwoName: (teamTwoName: String) -> Unit,
    onTeamTwoScore: (score: Int) -> Unit,
    onPlayPressed: () -> Unit,
    onStopPressed: () -> Unit,
    team1check: (String) -> Unit,
    team2check: (String) -> Unit,
    matchStarted: Boolean)
{

    Column( // Main Column
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Row( modifier = Modifier.fillMaxWidth()) {

            TeamComponent(
                matchData = currentMatchData,
                teams = teams,
                onSelectedTeamOne = { setTeamOneName(it) },
                onTeamTwoName = {
                    setTeamTwoName(it)
                    team2check(it)},
                onTeamTwoScore = { onTeamTwoScore(it) },
                onTeamOneName = {team1check(it)},
                matchStarted = matchStarted
            )
        }
        Row( modifier = Modifier.fillMaxWidth()) {

            StopWatchComponent(
                matchStarted = matchStarted,
                timeElapsed = timeElapsed,
                isRunning = isRunning,
                onPlayPressed = { onPlayPressed() },
                onStopPressed = { onStopPressed() },
                properTeam1 = properTeam1,
                properTeam2 = properTeam2,
                )


        }
        Row( modifier = Modifier.fillMaxWidth()) {
            EventComponent(
                players,
                newEvent = { newEvent(it) }
            )
        }
        Row( modifier = Modifier.fillMaxWidth()) {
            LogComponent(events)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatchScreenPreview() {
    //MatchContent(matchViewState)
    
}