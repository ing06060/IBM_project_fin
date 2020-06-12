package com.example.ibm_project_fin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_mapfor_search.*

class mapforSearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapfor_search)
        //타이틀 값 변경하기
        var inten=intent
        val title=intent.getStringExtra("title")
        map_search_title.setText(title)

        //전체 데이터 받기
        var data=intent.extras?.getSerializable("fulldata") as ArrayList<StoreData>
        var listdata=intent.extras?.getSerializable("onedata") as StoreData

        //하단에 데이터 표시하기
        set_map_underlist(listdata)

        //지도 위에 마커 표시하기
        set_mark_on_map(title,data)

        //뒤로 가기 버튼 클릭시
        map_search_back.setOnClickListener {
            finish()
        }
        //리스트 버튼 클릭시
        map_search_list.setOnClickListener {
            //recomandStoreListActivity로 이동
        }
        map_search_button.setOnClickListener {
            //recomandstorelist로 이동
        }
    }

    fun set_mark_on_map(title:String, data:ArrayList<StoreData>){

    }

    fun set_map_underlist(data:StoreData){
        map_search_name.text=data.name
        map_search_address.text=data.address
        map_search_congestionrate.text=data.conjuction.toString()
        if(data.conjuction>=1.5){
            map_search_congestionImage.setImageResource(R.drawable.congestionlowinmap)
        }else{
            map_search_congestionImage.setImageResource(R.drawable.congestionhighinmap)
        }
        map_search_distance.text=data.distance.toString()+"m"
        map_search_phone.text=data.phone
        when(data.state){
            1->{
                map_search_state.text="영업 중"
            }
            0->{
                map_search_state.text="영업 종료"
            }
            -1->{
                map_search_state.text="휴업"
            }
        }
        
    }

}