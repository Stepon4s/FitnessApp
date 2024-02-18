package com.example.fitnessapplication

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.fitnessapplication.ExerciseNameDatabase.ExerciseNameDatabase
import com.example.fitnessapplication.ExerciseNameDatabase.ExerciseNameViewModel
import com.example.fitnessapplication.databinding.ActivityMainBinding
import java.lang.Math.abs


class MainActivity : AppCompatActivity() {
    private lateinit var gestureDetector: GestureDetector
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, HomeFragment())
                .commit()
        }

        binding.bottomNavigationView.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.historyFragment -> {
                    replaceFragment(HistoryFragment())
                }

                R.id.homeFragment -> {
                    replaceFragment(HomeFragment())
                }

                R.id.listFragment -> {
                    replaceFragment(ExerciseListFragment())
                }
            }
        }
        binding.bottomNavigationView.selectedItemId = R.id.homeFragment





//        gestureDetector =
//            GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
//                override fun onFling(
//                    e1: MotionEvent?,
//                    e2: MotionEvent,
//                    velocityX: Float,
//                    velocityY: Float
//                ): Boolean {
//                    if (e1 != null && e2 != null) {
//                        if (e1.x - e2.x > 60 && kotlin.math.abs(velocityX) > 100) {
//                            val intent2 =
//                                Intent(this@MainActivity, WorkoutHistoryActivity::class.java)
//                            startActivity(intent2)
//                            return true
//                        }
//                    }
//                    return false
//                }
//
//            })
//    }
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        if (event != null) {
//            this.gestureDetector.onTouchEvent(event)
//        }
//        return super.onTouchEvent(event)
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}






