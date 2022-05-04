package com.example.accountingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.accountingapp.ui.add.RecordFragment
import com.example.accountingapp.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}