package com.example.ibm_project_fin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search_store_list.*

class searchStoreList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_store_list)

        //title정보 검색어로 변경하기
        val intent=getIntent()
        val title=intent.getStringExtra("title")
        var data=intent.extras?.getSerializable("data") as ArrayList<StoreData>
        search_store_title.setText(title)

        //어댑터에 검색가게 정보 넣기
        search_store_list.layoutManager=LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
        var adapter=search_store_list_adapter(data)
        adapter.onitemtouchlistener=object:search_store_list_adapter.OnItemtouch{
            override fun itemtouch(
                viewHolder: search_store_list_adapter.MyViewHolder,
                view: View,
                data: StoreData,
                position: Int
            ) {
                //mapforsearchactivity로 이동
                //현재 어댑터의 데이터의 값 전달해주기
                val map_search_Intent= Intent(applicationContext,mapforSearchActivity::class.java)
                map_search_Intent.putExtra("title",title.toString()) //타이틀 전송
                map_search_Intent.putExtra("fulldata",data) //검색 결과 전체 데이터 전송
                map_search_Intent.putExtra("onedata",data) //클릭한 데이터를 전송
                startActivity(map_search_Intent)
            }

        }


        //뒤로 가기 버튼 클릭하면 액티비티 종료시키기
        search_store_back.setOnClickListener {
            finish()
        }

        //지도 버튼 클릭하면
        search_store_map.setOnClickListener {
            //mapforsearchactivity로 이동
            //data[0]의 값 전달해주기
            val map_search_Intent= Intent(applicationContext,mapforSearchActivity::class.java)
            map_search_Intent.putExtra("title",title.toString()) //타이틀 전송
            map_search_Intent.putExtra("fulldata",data) //검색 결과 전체 데이터 전송
            map_search_Intent.putExtra("onedata",data[0]) //검색 결과 중 상위 데이터 전송
            startActivity(map_search_Intent)
        }
    }
}