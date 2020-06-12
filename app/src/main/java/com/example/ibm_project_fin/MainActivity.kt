package com.example.ibm_project_fin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Thread.sleep
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Level.parse
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var nowLoc: String //현재 텍스트 위치
    var currentLoc = LatLng(0.0, 0.0) //현재 위치의 위도와 경도
    lateinit var storeVisitedList: ArrayList<StoreData> // 확진자 방문 가게 명단

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Intro 화면 2초가 보여주기
        show_intro()

        //현재 위치 설정하기
        //=> 위도, 경도와 텍스트 위치 받아오기
        set_location()

        //위치 기반으로 확진자가 방문한 가게 명단 가져오기
        storeVisitedList = make_visited_store_list(currentLoc)
        Log.i("data size", "storevisitedlist data size ask")
        if (storeVisitedList.size == 0) {
            Log.i("data size", "storevisitedlist data size not 0")
        }

        //recyclerView에 명단 붙이기
        main_visited_store.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        var adapter = main_visited_adapter(storeVisitedList)
        adapter.itemclicklistener = object : main_visited_adapter.OnItemClick {
            override fun itemclick(
                viewholder: main_visited_adapter.MyViewHolder,
                view: View,
                data: StoreData,
                position: Int
            ) {
                //mapActivity로 이동한다.
                val map_Intent = Intent(applicationContext, mapActivity::class.java)
                map_Intent.putExtra("fulldata", data) //하단에 보여줄 데이터 전송
                map_Intent.putExtra("onedata", storeVisitedList) //인텐트에 데이터 전송
                startActivity(map_Intent)
            }
        }
        main_visited_store.adapter = adapter


        //가게 이름을 입력하세요 부분 클릭하면 searchStoreList로 이동한다.
        search_store_part.setOnClickListener {
            val searchIntent=Intent(applicationContext,searchActivity::class.java)
            //기존에 검색했던 데이터 가져오기 // 서버로 할지 데이터 베이스로 할지 결정
            var search=get_recent_search()
            searchIntent.putExtra("data",search)
            startActivity(searchIntent)
        }

        //더보기 버튼 클릭시 visitedStoreListActivity로 이동한다.
        more.setOnClickListener {
            val visitedIntent=Intent(applicationContext,visitedStoreListActivity::class.java)
            visitedIntent.putExtra("data",storeVisitedList)
            startActivity(visitedIntent)
        }

        //위치 > 이 파트 누르면 위치 재설정하기
        nowLocation.setOnClickListener {
            //위치 재설정하는 부분 화면에 보이게 설정
            set_location.visibility=View.VISIBLE

            //찾기 버튼을 클릭한 경우
            //=>set_location_address_list recyclerView에 비슷한 주소들 뜨기 (아직까지는 구현x)
            //=> 현재까지는 입력한 주소 그대로 설정하기
            set_location_find_btn.setOnClickListener{
                //입력한 텍스트를 메인 화면의 위치 > 파트 수정하기
                nowLoc=inputAddress.text.toString()
                nowLocation.setText("위치 > "+nowLoc)

                //입력한 텍스트를 통해 현재 위치의 위도 경도값을 계산하기

                //계산한 위도 경도값을 기반으로 확진자 데이터 다시 설정하기
                storeVisitedList=make_visited_store_list(currentLoc)

                set_location.visibility=View.INVISIBLE
            }

        }



    }

    fun show_intro(){
        val intro_intent = Intent(applicationContext, Intro::class.java)
        startActivity(intro_intent)
    }

        fun set_location() {
            //=> 1. nowLoc과 currentLoc값 가져오기
            //=> 2. nowLocation에 현재 위치 텍스트로 입력하기
        }

        fun make_visited_store_list(latlng: LatLng): ArrayList<StoreData> {
            //확진자 명단 데이터 만들기
            var data = ArrayList<StoreData>()
            if (data.size > 0) {
                data.clear()
            }
            data.add(
                StoreData(
                    "온누리대산약국",
                    "서울특별시 관악구 시흥대로 566",
                    "02-853-4967",
                    LatLng(37.29003, 126.54085),
                    2.2,
                    0,
                    6,
                    3,
                    0,
                    0.0
                )
            )
            data.add(
                StoreData(
                    "CU 구로공단점",
                    "서울특별시 관악구 신림동 시흥대로 556",
                    "+02-859-6698",
                    LatLng(37.482733, 126.901657),
                    2.08,
                    1,
                    6,
                    3,
                    0,
                    1.5
                )
            )
            data.add(
                StoreData(
                    "예수비전교회",
                    "서울특별시 금천구 독산동 199-12",
                    "02-853-4967",
                    LatLng(37.470876, 126.901740),
                    1.2,
                    0,
                    6,
                    7,
                    0,
                    0.0
                )
            )
            data.add(
                StoreData(
                    "금천체육공원",
                    "서울특별시 금천구 독산동",
                    "02-853-4967", LatLng(37.468407, 126.908508),
                    2.2,
                    1,
                    6,
                    7,
                    0,
                    2.2
                )
            )

            for (i in 0 until data.size) {
                data[i].visited_days_ago =
                    get_visited_days_ago(data[i].visited_month, data[i].visited_day)
            }

            return data
        }

        fun get_visited_days_ago(visited_month: Int, visited_day: Int): Int {
            var today = Date()
            var dateFormat = SimpleDateFormat("MM-dd")
            var toDay = dateFormat.format(today)
            var to = dateFormat.parse(toDay.toString())
            var visited =
                dateFormat.parse(visited_month.toString() + "-" + visited_day.toString() + " 00:00")
            var diff = to.time - visited.time
            return (diff / (60 * 60 * 24 * 1000)).toInt()
        }

        fun get_recent_search(): ArrayList<String> {
            var data = ArrayList<String>()
            data.add("코인노래방")
            data.add("카페")
            data.add("마라탕")
            data.add("음식점")
            data.add("음식")
            return data
        }
    }
