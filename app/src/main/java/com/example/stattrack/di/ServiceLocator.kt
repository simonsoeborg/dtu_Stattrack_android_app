package com.example.stattrack.di


import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.get
import com.example.stattrack.model.database.Repository
import com.example.stattrack.model.database.AppDatabase
import com.example.stattrack.model.model.*
import com.example.stattrack.presentation.team.TeamViewModel
import com.example.stattrack.presentation.match.MatchViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object ServiceLocator {

    private lateinit var application: Application

    fun init(application: Application) {
        ServiceLocator.application = application
    }

    private val database: AppDatabase by lazy { AppDatabase.build(application) }

    private val repository: Repository by lazy { Repository(database) }

    // Effectively singleton
    private val viewModelFactory by lazy {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return when (modelClass) {
                    MatchViewModel::class.java -> MatchViewModel(repository)
                    TeamViewModel::class.java -> TeamViewModel(repository)
                    else -> throw IllegalArgumentException("Unsupported ViewModel $modelClass")
                } as T
            }
        }
    }


    val ViewModelStoreOwner.matchViewModel: MatchViewModel
        get() = ViewModelProvider(this, viewModelFactory).get()

    val ViewModelStoreOwner.teamViewModel: TeamViewModel
            get() = ViewModelProvider(this, viewModelFactory).get()

    fun prepopulateSQLiteDB(){
        GlobalScope.launch() {
            val repo = repository
            val eventData = defaultDummyEventData
            val matchData = defaultDummyMatchData
            val playerData = defaultDummyPlayerData
            val playerStatsData = defaultDummyPlayerStatsData
            val teamData = defaultTeamDummyData
            Log.d("prepopulateSQLiteDB","Prepopulation begun")
            for (eventdata in eventData){
                GlobalScope.launch() {
                    repo.insertEventData(eventdata)
                }
            }
            for (matchdata in matchData){
                GlobalScope.launch() {
                    repo.insertMatchData(matchdata)
                }
            }
            for (playerdata in playerData){
                GlobalScope.launch() {
                    repo.insertPlayer(playerdata)
                }
            }
            for (playerstatsdata in playerStatsData){
                GlobalScope.launch() {
                    repo.insertPlayerStats(playerstatsdata)
                }
            }
            for (team in teamData){
                GlobalScope.launch() {
                    repo.insertTeam(team)
                }
            }
            Log.d("prepopulateSQLiteDB","Prepopulation finished")
        }
    }
}