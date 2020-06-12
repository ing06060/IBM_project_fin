package com.example.ibm_project_fin

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.recent_search_layout.*

class searchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        //최근 검색어 목록에 어댑터 달아주기
        search_recentTerms.layoutManager=LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
        //메인 액티비티에서 전송한 데이터 받기
        val main_intent=intent
        var data=main_intent.extras?.getSerializable("data") as ArrayList<String>
        var adapter=recent_search_adapter(data)

        adapter.itemclick=object:recent_search_adapter.OnItemClick{
            override fun itemclick(
                viewholder: recent_search_adapter.MyViewHolder,
                view: View,
                data: String,
                position: Int
            ) {
                if(view==viewholder.terms){
                    //해당 검색어를 검색해서 얻은 결과 화면에 출력하기
                    //해당 검색어 추가하기
                    adapter.addData(search_input.text.toString())
                    //데이터베이스나 서버에도 적용하는 코드

                    //검색어와 관련된 가게 정보 가져오기
                    var store=get_search_store(data)
                    //searchStoreList액티비티로 이동하기
                    val storelist_Intent= Intent(applicationContext,searchStoreList::class.java)
                    storelist_Intent.putExtra("title",data+" 검색 결과")
                    storelist_Intent.putExtra("data",store)
                    startActivity(storelist_Intent)
                }
                else if(view==viewholder.delete){
                    //해당 검색어 삭제하기
                    adapter.deleteData(position)
                    //데이터베이스나 서버에도 적용하는 코드
                }

            }
        }

        search_recentTerms.adapter=adapter

        //뒤로 가기 버튼을 클릭한경우 메인으로 가기
        search_back.setOnClickListener {
            finish()
        }

        //돋보기 버튼 클릭시 해당 검색어로 검색 진행하기
        recent_search_btn.setOnClickListener {
            //아무것도 입력하지 않은 경우
            if(search_input.text.toString()==""){
                Toast.makeText(applicationContext,"검색어를 입력해주세요",Toast.LENGTH_SHORT).show()
            }else{
                //해당 검색어를 검색해서 얻은 결과 화면에 출력하기
                //searchStoreList액티비티로 이동하기
                val storelist_Intent= Intent(applicationContext,searchStoreList::class.java)
                storelist_Intent.putExtra("title",search_input.text.toString()+" 검색 결과")
                var result=get_search_store(search_input.text.toString())
                storelist_Intent.putExtra("data",result)
                startActivity(storelist_Intent)
            }
        }


    }

    fun get_search_store(data:String):ArrayList<StoreData>{
        var result=ArrayList<StoreData>()
        result.add(StoreData("스타벅스 금천독산점", "서울특별시 금천구 독산동 291-1","1522-3232", LatLng(37.469371,126.895700),622.10,1,0,0,0,1.5,R.drawable.coffe))
        result.add(StoreData("스타벅스 독산사거리점","서울특별시 금천구 159-23 영진빌딩","1522-3232",LatLng(37.473584,126.898172),1050.0,1,0,0,0,2.0,R.drawable.coffe))
        result.add(StoreData("스타벅스 광명하안점","경기도 광명시 하안3동 범안로 1060","1522-3232",LatLng(37.462998,126.880698),874.29,1,0,0,0,1.2,R.drawable.coffe))
        result.add(StoreData("스타벅스  W-Mall점","서울특별시 금천구 가산동 디지털로 188","1522-3232",LatLng(37.478388, 126.887337),1280.0,0,0,0,0,0.0,R.drawable.coffe))
        result.add(StoreData("스타벅스 가산브이타워점","서울특별시 금천구 가산동 가산디지털1로 128","1522-3232",LatLng(37.477442,126.883845),1340.0,-1,0,0,0,0.0,R.drawable.coffe))
        result.add(StoreData("스타벅스 가산그레이트점","서울특별시 금천구 가산동 디지털로9길 32","1522-3232",LatLng(37.480159,126.887293),1510.0,0,0,0,0,0.0,R.drawable.coffe))
        result.add(StoreData("스타벅스 가산디지털점","서울특별시 금천구 가산동 371-28”,”1522-3232","1522-3232",LatLng(37.480703,126.882144),1680.0,1,0,0,0,1.9,R.drawable.coffe))

        //거리순으로 소팅하기
        result.sortBy { it.distance }

        return result
    }
}