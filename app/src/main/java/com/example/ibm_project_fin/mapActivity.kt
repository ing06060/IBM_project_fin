package com.example.ibm_project_fin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
//확진자 이용매장 or 추천매장

class mapActivity : AppCompatActivity() {

    lateinit var googleMap: GoogleMap
    var loc: LatLng = LatLng(37.554752,126.970631)
    lateinit var visitedStoreList:ArrayList<StoreData>
    lateinit var output:ArrayList<StoreData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        //데이터 전송받기
        var intent=intent
        visitedStoreList=intent.extras?.getSerializable("full") as ArrayList<StoreData>
        output=intent.extras?.getSerializable("one") as ArrayList<StoreData>
        Toast.makeText(this,output[0].address,Toast.LENGTH_SHORT).show()
        //init()
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

        }
    }
    //추천매장
}