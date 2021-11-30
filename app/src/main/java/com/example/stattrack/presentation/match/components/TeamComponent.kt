package com.example.stattrack.presentation.match.components


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stattrack.di.ServiceLocator
import com.example.stattrack.model.model.MatchData
import com.example.stattrack.model.model.Team
import com.example.stattrack.presentation.ui.theme.PrimaryBlue
import com.example.stattrack.presentation.ui.theme.PrimaryWhite
import com.example.stattrack.presentation.ui.theme.Typography


@Composable
fun TeamComponent(
    matchData: MatchData,
    teams:List<Team>,
    onSelectedTeamOne: (teamId: Int) -> Unit,
    onTeamOneName : (String) -> Unit,
    onTeamTwoName: (String) -> Unit,
    onTeamTwoScore: (Int) -> Unit,
    isRunning: State<Boolean>,
    ){

    val teamTwoName = remember { mutableStateOf("")}
    val focusManager = LocalFocusManager.current

    Row( // Teams Row
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Column( // Main Column
            modifier = Modifier
                .fillMaxWidth()
                .padding(17.dp)
        ) {

            // Team 1 row
            Row(
                modifier = Modifier
                .padding(2.dp),
            )
            {
                    Column( modifier = Modifier
                        .weight(3f)
                        .padding(1.dp)
                    ) {
                        DropdownTeamsList(
                            teams = teams,
                            onSelectedTeam = {

                                if (isRunning.value){
                                    Toast.makeText(ServiceLocator.application, "Holdnavn kan ikke ændres under kamp", Toast.LENGTH_LONG).show()
                                }

                                else
                                onSelectedTeamOne(it) },
                            TeamName = { onTeamOneName(it)}
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 5.dp)

                    ) {
                        Text(
                            text = matchData.creatorTeamGoals.toString(),
                            color = PrimaryBlue,
                            fontSize = 24.sp,
                        )
                    }

            }
            // Team 2 row
            Row( modifier = Modifier
                .padding(2.dp)
                .background(color = PrimaryWhite)
            )
            {
                Column( modifier = Modifier
                    .weight(3f)
                    .padding(1.dp)
                    .background(color = PrimaryWhite)
                    //.border(2.dp, color = PrimaryBlue) <- doesnt look good find another style
                ) {
                    TextField(
                        value = teamTwoName.value,
                        onValueChange = {

                            if (isRunning.value){
                                Toast.makeText(ServiceLocator.application, "Modstander navn kan ikke ændres under kamp", Toast.LENGTH_LONG).show()
                            }
                            else
                            teamTwoName.value = it
                            onTeamTwoName(it)
                        },//cursorBrush = SolidColor(Transparent),
                        placeholder = {
                            Text(text = "Vælg modstander")}
                        ,
                        textStyle = TextStyle(color = PrimaryBlue, background = PrimaryWhite, fontSize = 24.sp),
                        singleLine = true,
                        readOnly = isRunning.value,
                        colors=  textFieldColors(
                            backgroundColor = PrimaryWhite,
                            /*focusedIndicatorColor = Transparent,
                            disabledIndicatorColor = Transparent,
                            unfocusedIndicatorColor = Transparent*/
                        ),

                        )
                        /*
                        colors =  textFieldColors(backgroundColor = PrimaryWhite) */

                }
                Column( modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp)
                ) {
                    Box(Modifier.fillMaxWidth()){
                        // TODO - Make a button so we can increment score on team 2 and use callback function onTeamTwoScore(Int) to call value upwards in compose-tree

                        Text(
                            text = matchData.opponentGoals.toString(),
                            color = PrimaryBlue,
                            fontSize = 24.sp,

                            modifier = Modifier.align(Alignment.CenterStart)
                            )

                        Column(modifier = Modifier.align(Alignment.CenterEnd)) {
                            IconButton(onClick = { onTeamTwoScore(matchData.opponentGoals+1) }) {
                                Icon(Icons.Default.Add, contentDescription = "Increment")
                            }
                            IconButton(onClick = { onTeamTwoScore(matchData.opponentGoals-1) }) {
                                Icon(Icons.Default.Remove, contentDescription = "Decrement")
                            }
                        }

                    }

                }
            }
        }
    }
}


@Composable
fun DropdownTeamsList(teams: List<Team>, onSelectedTeam: (teamId: Int) -> Unit, TeamName: (name : String)-> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedId by remember { mutableStateOf(0) }
    var selectedTeam by remember { mutableStateOf("")}

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Row(modifier = Modifier
        .clickable(onClick = { expanded = true })
        .background(
            PrimaryWhite
        )
    ) {
        TextField(
            value = selectedTeam,
            onValueChange = {
                selectedTeam = it
                TeamName(it)
                            },
            placeholder = {
                Text(text = "Vælg hold")}
            ,
            textStyle = Typography.body1.copy(
                fontSize = 24.sp,
                color = PrimaryBlue),
            trailingIcon = {
                Icon(icon, "" , Modifier.clickable{expanded = !expanded})
            },
            enabled = false,
            colors = textFieldColors(backgroundColor = PrimaryWhite)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = PrimaryWhite
                ),
        ) {
            teams.forEach { team ->
                DropdownMenuItem(onClick = {
                    selectedId = team.teamId-1
                    selectedTeam = team.name
                    onSelectedTeam(team.teamId)
                    expanded = false
                }) {
                    Text(team.clubName, color = PrimaryBlue, fontSize = 24.sp)
                }
            }
        }
    }
}

@Preview
@Composable
fun TeamComponentPreview(){
   /* TeamComponent(
        defaultDummyMatchData[0],
        teams = defaultTeamDummyData,
        onSelectedTeamOne = { },
        onTeamTwoName = { },
        onTeamTwoScore = { },
    )*/
}