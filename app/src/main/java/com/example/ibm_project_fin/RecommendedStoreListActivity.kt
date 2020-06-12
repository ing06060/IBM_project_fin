package com.example.ibm_project_fin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_mapfor_search.*
import kotlinx.android.synthetic.main.activity_recommended_store_list.*

class RecommendedStoreListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommended_store_list)
        init()
    }

    private fun init() {
        var listdata=intent.extras?.getSerializable("SearchedDataArr") as ArrayList<StoreData>
        var storedata = intent.extras?.getSerializable("SearchedData") as ArrayList<StoreData>

        //현재검색결과 띄우기
        storename.text = storedata[0].name
        location.text = storedata[0].address
        tel.text = storedata[0].phone
        distance.text = storedata[0].distance.toString()
        when(storedata[0].state){
            1->{
                state.text="영업 중"
            }
            0 ->{
                state.text="영업 종료"
            }
            -1-> {
                state.text="휴업"
            }

        }

        //리스트에 띄우기
        recommend_store_list.layoutManager= LinearLayoutManager(applicationContext,
            LinearLayoutManager.VERTICAL,false)
        listdata.sortByDescending {
            it.conjuction
        }

        var adapter=recommend_store_list_adapter(listdata)
        adapter.onitemtouchlistener=object:recommend_store_list_adapter.OnItemtouch{
            override fun itemtouch(
                viewHolder: recommend_store_list_adapter.MyViewHolder,
                view: View,
                data: StoreData,
                position: Int
            ) {
                //mapactivity로 이동
                //현재 어댑터의 데이터의 값 전달해주기
                val storelist = ArrayList<StoreData>()
                storelist.add(data)

                val map_Intent= Intent(applicationContext,mapActivity::class.java)
                map_Intent.putExtra("full",listdata) //검색 결과 전체 데이터 전송
                map_Intent.putExtra("one",storelist) //클릭한 데이터를 전송
                startActivity(map_Intent)
            }
        }

        recommend_store_list.adapter =  adapter

       /* map_search_button.setOnClickListener {
            val i = Intent(this,mapActivity::class.java)
            // i.putExtra로 추천매장 위도경도, 매장명, 상세주소, 전화번호, 혼잡도 담기 이때 위에 텍스트 바꾸기

        }*/
    }
}
