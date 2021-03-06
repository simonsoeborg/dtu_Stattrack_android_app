package com.example.stattrack.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.stattrack.presentation.match.MatchScreen
import com.example.stattrack.presentation.match.MatchViewModel
import com.example.stattrack.presentation.navbar.Screen
import com.example.stattrack.presentation.ui.theme.PrimaryBlue
import com.example.stattrack.presentation.ui.theme.PrimaryWhite
import com.example.stattrack.di.ServiceLocator.matchViewModel
import com.example.stattrack.di.ServiceLocator.playerViewModel
import com.example.stattrack.di.ServiceLocator.specificTeamViewModel
import com.example.stattrack.di.ServiceLocator.teamViewModel
import com.example.stattrack.di.ServiceLocator.fillSQLiteWithDemoData
import com.example.stattrack.di.ServiceLocator.specificMatchViewModel
import com.example.stattrack.model.model.Player
import com.example.stattrack.model.model.Team
import com.example.stattrack.presentation.player.PlayerClass
import com.example.stattrack.presentation.player.PlayerViewModel
import com.example.stattrack.presentation.team.*
import com.example.stattrack.presentation.ui.theme.StattrackTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val matchVM: MatchViewModel by lazy { matchViewModel }
        val teamVM: TeamViewModel by lazy { teamViewModel }
        val sTeamVM: SpecificTeamViewModel by lazy { specificTeamViewModel }
        val playerVM: PlayerViewModel by lazy { playerViewModel }
        val sMatchVM: SpecificMatchViewModel by lazy { specificMatchViewModel }

        supportActionBar?.hide() // Hide the title bar so the app shows in fullscreen

        fillSQLiteWithDemoData() // For development purposes

        setContent {
            val navController = rememberNavController()
            StattrackTheme {
                Scaffold(bottomBar = { BottomNavigationBar(navController) })
                {
                    NavHost(navController, startDestination = Screen.Landing.route)
                    {
                        composable(Screen.Landing.route) {
                            LandingScreen(navController)

                        }
                        composable(Screen.Team.route) {
                            MyTeamsScreen(teamViewModel = teamVM, navController)
                        }
                        composable(Screen.Match.route,) {
                            MatchScreen(matchViewModel = matchVM)
                        }
                        composable(Screen.SpecificTeam.route){

                            val teamObject = navController.previousBackStackEntry?.arguments?.getParcelable<Team>("specificTeam")
                            if (teamObject != null) {
                                SpecificTeamScreen(navController = navController,team = teamObject, specificTeamViewModel = sTeamVM)
                            }
                        }
                        composable(Screen.Player.route) {
                            val playerObject = navController.previousBackStackEntry?.arguments?.getParcelable<Player>("specificPlayer")
                            if (playerObject != null) {
                                PlayerClass(navController = navController, playerViewModel = playerVM, player = playerObject)
                            }
                        }
                        composable(Screen.CreateTeam.route){

                           AddTeam(teamVM,navController = navController)
                        }
                        composable(Screen.AddPlayer.route){
                            val teamObject = navController.previousBackStackEntry?.arguments?.getParcelable<Team>("Team")
                            if (teamObject != null) {
                                AddPlayer(sTeamVM, navController, team= teamObject)
                            }
                        }
                        composable(Screen.SpecificMatch.route){
                            val matchId = navController.previousBackStackEntry?.arguments?.getInt("matchId")
                            if (matchId != null){
                                SpecificMatchScreen(
                                    navController = navController,
                                    specificMatchViewModel = sMatchVM,
                                    matchId = matchId
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val screens = listOf(
        Screen.Match,
        Screen.Team,
        //Screen.Player
    )
    BottomNavigation(
        backgroundColor = PrimaryWhite,
        contentColor = PrimaryBlue
    ) {
        screens.forEach { screen ->
            AddItem(screen = screen, currentDestination = currentDestination, navController = navController)
        }
    }
}

@Composable fun RowScope.AddItem(
    screen: Screen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        icon = { Icon(screen.icon, contentDescription = screen.title) },
        label = { Text(text = screen.title) },
        alwaysShowLabel = true,
        onClick = {
            navController.navigate(screen.route) {
                navController.popBackStack()
            }
        },
        selected = currentDestination?.hierarchy?.any{
            it.route == screen.route
        } == true
    )
}






