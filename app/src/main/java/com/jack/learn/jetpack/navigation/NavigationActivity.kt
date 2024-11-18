package com.jack.learn.jetpack.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.jack.learn.R
import com.jack.learn.databinding.ActivityNavigationBinding

class NavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            val navController = Navigation.findNavController(this@NavigationActivity,R.id.fmt_main)
            // 绑定UI视图与Controller
            NavigationUI.setupWithNavController(btnNavigationView,navController)
            btnNavigationView.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.fa_one ->{
                        navController.navigate(R.id.action_fmta, Bundle())
                    }
                    R.id.fa_second->{
                        navController.navigate(R.id.action_fmtb,Bundle())
                    }
                    R.id.fa_third->{
                        navController.navigate(R.id.action_fmtc,Bundle())
                    }
                    R.id.fa_four ->{
                        navController.navigate(R.id.action_fmtd,Bundle())
                    }
                }
                return@setOnItemSelectedListener false
            }
        }
    }
}