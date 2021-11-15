package com.example.stattrack.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.stattrack.presentation.navbar.BottomNavigationBar
import com.example.stattrack.presentation.navbar.Navigation
import com.example.stattrack.services.ServiceLocator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ServiceLocator.init(application)
        setContent{
            MainScreen()
        }

//        setContentView(R.layout.activity_main)
//
//        val playerFragment = fragment_spiller()
//
//        // Initialize start fragment.
//        /*supportFragmentManager.beginTransaction().apply {
//            replace(R.id.flfragmentTest, playerFragment)
//            commit()
//        }*/
//
//        // Initialize Fragment from button-pressed
//        val btn = findViewById<Button>(R.id.btnFragmentSpiller)
//        btn.setOnClickListener{
//            supportFragmentManager.beginTransaction().apply {
//                replace(R.id.flfragmentTest, playerFragment)
//                addToBackStack(null)
//                commit()
//            }
//        }
    }
}


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        Navigation(navController = navController)
    }
}
