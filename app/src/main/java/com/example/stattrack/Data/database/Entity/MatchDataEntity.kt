package com.example.stattrack.Data.database.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.stattrack.Data.model.MatchData
import java.util.*

@Entity(tableName = "matchData")
data class MatchDataEntity (
    @PrimaryKey val id : Int,
    val creatorId : String,
    val creatorTeamId : Int,
    val opponent : String,
    val matchDate : Date,
    val creatorTeamGoals : Int,
    val opponentGoals : Int
)

fun MatchDataEntity.toModel(): MatchData =
    MatchData(id, creatorId, creatorTeamId, opponent, matchDate, creatorTeamGoals, opponentGoals)

fun MatchData.toEntity(): MatchDataEntity =
    MatchDataEntity(id, creatorId, creatorTeamId, opponent, matchDate, creatorTeamGoals, opponentGoals)