package com.example.ibm_project_fin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class mapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        //데이터 전송받기
        var intent=intent
        var visitedStoreList=intent.extras?.getSerializable("data") as ArrayList<StoreData>
        var output=intent.extras?.getSerializable("store") as StoreData

    }
}