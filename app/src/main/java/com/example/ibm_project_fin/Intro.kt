package com.example.ibm_project_fin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Intro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        //화면에 2초 보여지고 메인 액티비티로 넘어가기
        Handler().postDelayed({
            finish()
        },2000)
    }
}