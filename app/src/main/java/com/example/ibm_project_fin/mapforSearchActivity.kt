package com.example.ibm_project_fin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_mapfor_search.*

class mapforSearchActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {
    lateinit var googleMap: GoogleMap
    lateinit var storelist:ArrayList<StoreData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapfor_search)
        //타이틀 값 변경하기
        //var inten=intent


        //전체 데이터 받기
        var data=intent.extras?.getSerializable("full") as ArrayList<StoreData>
        storelist = data
        var listdata=intent.extras?.getSerializable("one") as ArrayList<StoreData>
       // map_search_title.text=listdata[0].name

        if(intent.getStringExtra("title")=="검색모든매장표시"){
            map_search_title.text = "검색모든매장표시"
            initAllMap(data)
        }else{
            map_search_title.text=listdata[0].name
            initmap(listdata[0])
        }


        //하단에 데이터 표시하기
        set_map_underlist(listdata[0])

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


    fun set_map_underlist(data:StoreData){
        map_search_name.text=data.name
        map_search_address.text=data.address
        map_search_congestionrate.text=data.conjuction.toString()
        map_search_image.setImageResource(data.image)
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

    private fun initAllMap(data: ArrayList<StoreData>) {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapSearched) as SupportMapFragment
        mapFragment.getMapAsync{

            googleMap = it
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(data[6].latlng,13.0f))
            googleMap.setMinZoomPreference(10.0f)
            googleMap.setMaxZoomPreference(20.0f)
            val options = MarkerOptions()

            for(j in 0..(data.size-1)){
                options.position(data[j].latlng)
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                options.title(data[j].name)
                val mk1 = googleMap.addMarker(options)
                //mk1.showInfoWindow()
                googleMap.setOnMarkerClickListener(this)
            }



        }

    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        p0?.showInfoWindow()

        for(i in 0..(storelist.size-1)){
            if(p0?.title==storelist[i].name){
                map_search_name.text = storelist[i].name
                map_search_address.text = storelist[i].address
                map_search_phone.text = storelist[i].phone
                map_search_distance.text =storelist[i].distance.toString()+"m"

                if(storelist[i].conjuction>=1.5){
                    map_search_congestionImage.setImageResource(R.drawable.congestionlowinmap)
                }else{
                    map_search_congestionImage.setImageResource(R.drawable.congestionhighinmap)
                }

                when(storelist[i].state){
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
                map_search_congestionrate.text = storelist[i].conjuction.toString()
                //이미지도 바꾸기

                Toast.makeText(this, storelist[i].address,Toast.LENGTH_SHORT).show()
            }

        }
        return true

    }

}