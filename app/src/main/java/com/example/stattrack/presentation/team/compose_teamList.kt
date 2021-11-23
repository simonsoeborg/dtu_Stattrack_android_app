package com.example.stattrack.presentation.team

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import com.example.stattrack.R
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.*
import com.airbnb.lottie.model.content.CircleShape
import com.example.stattrack.model.model.Team
import com.example.stattrack.presentation.navbar.NavItem
import com.example.stattrack.presentation.ui.theme.PrimaryBlue
import kotlinx.coroutines.delay

private const val SplashWaitTime: Long = 3500

@Composable
fun MyTeamsScreen(teamViewModel: TeamViewModel, navController: NavHostController) {
    val currentState: State<TeamViewState> = teamViewModel.viewState.collectAsState()
    var showLandingScreen by remember { mutableStateOf(true) }
    if (showLandingScreen) {
        LandingScreen(onTimeout = { showLandingScreen = false })
    } else {
        MyTeamsScreenContent(state = currentState, navController = navController )
    }
}

@Composable
fun MyTeamsScreenContent(state: State<TeamViewState>, navController: NavHostController){

    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            Box(modifier = Modifier.fillMaxWidth()) {
                Box(Modifier.align(Alignment.CenterStart)) {
                    Text(text = "Hold oversigt", fontSize = 32.sp, color = PrimaryBlue)
                }

                Box(Modifier.align(Alignment.CenterEnd)) {
                    Column(horizontalAlignment = CenterHorizontally) {
                        NewTeamButton()
                        Text(text = "New Team", color = PrimaryBlue)
                    }
                }
            }

            Column(modifier = Modifier.padding(start = 10.dp)) {
                TeamList(state, navController)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(text = "Kamp oversigt", fontSize = 32.sp, color = PrimaryBlue)
            Column(modifier = Modifier.padding(10.dp)) {
                dummydata2()
            }
        }
    }
}

// Inspired by: https://stackoverflow.com/questions/66671902/how-to-create-a-circular-outlined-button-with-jetpack-compose
@Composable
fun NewTeamButton(){
    OutlinedButton(onClick = { /*TODO*/ },
        modifier= Modifier
            .padding(top = 5.dp)
            .size(40.dp),
        shape = CircleShape,
        border= BorderStroke(1.dp, PrimaryBlue),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor =  PrimaryBlue)
    ) {
        Icon(Icons.Default.Add, contentDescription = "NewTeam Button")
    }
}


@Composable
fun TeamList(state: State<TeamViewState>, navController: NavHostController) {
    LazyColumn() {
        items(
            items = state.value.teams,
            key = { team ->
                // Return a stable + unique key for the item
                team.teamId
            },

        ) {
                    team ->
            // Clickable sender kun test-data pt.
            Surface(modifier = Modifier.clickable {

                // Pass data
                val specifikTeam = Team(team.teamId,team.name,team.clubName,team.creatorId,team.teamUYear,team.division) // User is a parcelable data class.

                navController.currentBackStackEntry?.arguments?.putParcelable("specifikTeam", specifikTeam)
                navController.navigate(NavItem.SpecifikTeam.route)

                println(team.name+team.teamId)
            }){
                Text(team.name,modifier = Modifier.padding(2.dp), color = PrimaryBlue)
            }
        }
    }
}

@Composable
fun LandingScreen (onTimeout: () -> Unit) {
    val currentOnTimeout by rememberUpdatedState(newValue = onTimeout)

    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Center),
        horizontalAlignment = CenterHorizontally
    ) {
        Row(modifier = Modifier.padding(top = 100.dp)) {
            Text(
                text = "StatTrack",
                color = PrimaryBlue,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                fontSize = 36.sp,
                //textAlign = TextAlign.Center
            )
        }

        Row{

            val compositionResult: LottieCompositionResult = rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(
                    R.raw.handball
                )
            )
            val progress by animateLottieCompositionAsState(
                composition = compositionResult.value,
                isPlaying = true,
                iterations = 1,
                speed = 1.0f
            )

            LottieAnimation(composition = compositionResult.value, progress = progress)

            LaunchedEffect(key1 = true) {
                delay(SplashWaitTime)
                currentOnTimeout()
            }
        }
    }
}


@Composable
fun dummydata2() {
    val items = listOf("HØJ U19 mod FIF U19", "HØJ Elite mod BSV", "HØJ 2 mod Randers", "HØJ 3 mod Rudersdal")

    items.forEach { item ->
        Text(text = "$item", modifier = Modifier.padding(2.dp), color = PrimaryBlue)
    }
}

@Preview(showBackground = true)
@Composable
fun HoldScreenPreview() {
    //val previewModel = TeamViewModel(ServiceLocator.repository)
   // HoldScreen(previewModel, navController)
}
