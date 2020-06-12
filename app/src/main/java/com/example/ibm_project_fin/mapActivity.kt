package com.example.ibm_project_fin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.activity_mapfor_search.*

//확진자 이용매장 or 추천매장

class mapActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    lateinit var googleMap: GoogleMap
    var loc: LatLng = LatLng(37.554752,126.970631)
    lateinit var visitedStoreList:ArrayList<StoreData>

    lateinit var output:ArrayList<StoreData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        //데이터 전송받기
        var intent=intent
        var title=intent.getStringExtra("title")
        visitedStoreList=intent.extras?.getSerializable("full") as ArrayList<StoreData>
        output=intent.extras?.getSerializable("one") as ArrayList<StoreData>
        //init()

        init()
        //아래 항목에 StoreData추가하기
        map_name.text=output[0].name
        map_address.text=output[0].address
        map_tel.text=output[0].phone
        map_image.setImageResource(output[0].image)
        map_distance.text=output[0].distance.toString()+"m"

        if(intent.getStringExtra("title")=="검색 결과"){
            map_title.setText(output[0].name)
            map_daysago.visibility= View.INVISIBLE
            init()
        }else if(intent.getStringExtra("title")=="확진자이용매장모두표시"){
            map_title.setText("확진자이용매장모두표시")
            map_daysago.text=output[0].visited_days_ago.toString()+"일 전"
            initAllMap(visitedStoreList)
        }
        else{
            map_title.setText("확진자이용매장")
            map_daysago.text=output[0].visited_days_ago.toString()+"일 전"
            init()
        }

        if(output[0].conjuction>=1.5){
            map_congestionImage.setImageResource(R.drawable.congestionlowinmap)
        }else{
            map_congestionImage.setImageResource(R.drawable.congestionhighinmap)
        }

        when(output[0].state){
            1->{
                map_state.text="영업 중"
            }
            0->{
                map_state.text="영업 종료"
            }
            -1->{
                map_state.text="휴업중"
            }
        }

        map_back.setOnClickListener {
            finish()
        }
        map_list.setOnClickListener {
            val list_intent= Intent(applicationContext,visitedStoreListActivity::class.java)
            list_intent.putExtra("data",visitedStoreList)
            startActivity(list_intent)
        }

    }

    private fun initAllMap(data: ArrayList<StoreData>) {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapVisited) as SupportMapFragment
        mapFragment.getMapAsync{

            googleMap = it
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(data[3].latlng,13.0f))
            googleMap.setMinZoomPreference(10.0f)
            googleMap.setMaxZoomPreference(20.0f)
            val options = MarkerOptions()

            for(j in 0..(data.size-1)){
                options.position(data[j].latlng)
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                options.title(data[j].name)
                val mk1 = googleMap.addMarker(options)
                //mk1.showInfoWindow()

            }
            googleMap.setOnMarkerClickListener(this)
        }
    }

    override fun onMarkerClick(p0: Marker?): Boolean {

        p0?.showInfoWindow()

        for(i in 0..(visitedStoreList.size-1)){
            if(p0?.title==visitedStoreList[i].name){
                map_name.text = visitedStoreList[i].name
                map_address.text = visitedStoreList[i].address
                map_tel.text = visitedStoreList[i].phone
                map_distance.text =visitedStoreList[i].distance.toString()+"m"
                map_daysago.text=visitedStoreList[i].visited_days_ago.toString()

                if(visitedStoreList[i].conjuction>=1.5){
                    map_search_congestionImage.setImageResource(R.drawable.congestionlowinmap)
                }else{
                    map_search_congestionImage.setImageResource(R.drawable.congestionhighinmap)
                }

                when(visitedStoreList[i].state){
                    1->{
                        map_state.text="영업 중"
                    }
                    0->{
                        map_state.text="영업 종료"
                    }
                    -1->{
                        map_state.text="휴업"
                    }
                }
                map_search_congestionrate.text = visitedStoreList[i].conjuction.toString()
                //이미지도 바꾸기
                Toast.makeText(this, visitedStoreList[i].address,Toast.LENGTH_SHORT).show()
            }

        }
        return true
    }

    private fun init() {
        //확진자 이용매장
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapVisited) as SupportMapFragment
        mapFragment.getMapAsync{
            googleMap = it
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(output[0].latlng,16.0f))
            googleMap.setMinZoomPreference(10.0f)
            googleMap.setMaxZoomPreference(18.0f)
            val options = MarkerOptions()
            options.position(output[0].latlng)
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            options.title(output[0].name)
            options.title(output[0].address)
            val mk1 = googleMap.addMarker(options)
            mk1.showInfoWindow()

        }
    }
    //추천매장
}