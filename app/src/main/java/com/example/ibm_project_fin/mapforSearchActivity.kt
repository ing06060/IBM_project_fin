package com.example.ibm_project_fin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_mapfor_search.*

class mapforSearchActivity : AppCompatActivity() {
    lateinit var googleMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapfor_search)
        //타이틀 값 변경하기
        var inten=intent

        //전체 데이터 받기
        var data=intent.extras?.getSerializable("full") as ArrayList<StoreData>
        var listdata=intent.extras?.getSerializable("one") as ArrayList<StoreData>
        map_search_title.text=listdata[0].name


        //하단에 데이터 표시하기
        set_map_underlist(listdata[0])

        initmap(listdata[0])
        //지도 위에 마커 표시하기
        //set_mark_on_map(data)

        //뒤로 가기 버튼 클릭시
        map_search_back.setOnClickListener {
            finish()
        }
        //리스트 버튼 클릭시
        map_search_list.setOnClickListener {
            //recomandStoreListActivity로 이동
            finish()
        }
        //추천매장 보기
        map_search_button.setOnClickListener {
            //recomandstorelist로 이동
            val i = Intent(this,RecommendedStoreListActivity::class.java)
            var dat=ArrayList<StoreData>()
            dat.add(listdata[0])
            i.putExtra("SearchedDataArr",data)
            i.putExtra("SearchedData",dat)
            startActivity(i)
        }
    }

    fun set_mark_on_map( data:ArrayList<StoreData>){

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
    private fun initmap(storeData: StoreData) {

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapSearched) as SupportMapFragment
        mapFragment.getMapAsync{
            googleMap = it
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(storeData.latlng,18.0f))
            googleMap.setMinZoomPreference(15.0f)
            googleMap.setMaxZoomPreference(20.0f)
            val options = MarkerOptions()
            options.position(storeData.latlng)
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            options.title(storeData.name)
            // options.title(storeData.address)
            val mk1 = googleMap.addMarker(options)
            mk1.showInfoWindow()

        }
    }

}