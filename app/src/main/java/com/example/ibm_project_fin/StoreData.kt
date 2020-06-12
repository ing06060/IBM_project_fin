package com.example.ibm_project_fin

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
class StoreData(var name:String, //가게 이름
                var address:String, //가게 주소
                var phone:String, //가게 번호
                var latlng : LatLng, //현재 가게의 위도와 경도 값
                var distance : Double, //현재 위치로부터 가게까지의 거리
                var state:Int, //현재 가게의 오픈 여부=> 1: 영업 중, 0 : 영업 종료, -1=> 휴업중
                var visited_month:Int, //확진자 방문한 월
                var visited_day:Int, //확진자 방문한 일
                var visited_days_ago:Int, //확진자가 방문하고 지난 날짜
                var conjuction:Double //현재 매장의 혼잡도
):Parcelable {
}