package edu.skku.map.skyplatform

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

class OneWayFlightAdapter(val data:ArrayList<Flight>, val context: Context):BaseAdapter() {
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(p0: Int): Any {
        return data[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val generatedView = inflater.inflate(R.layout.item_one_way_flight, null)

        val airlineName = generatedView.findViewById<TextView>(R.id.airlineName)
        val airlineLogo = generatedView.findViewById<ImageView>(R.id.airlineLogo)
        val flightDepartureDate = generatedView.findViewById<TextView>(R.id.flightDepartureDate)
        val flightArrivalDate = generatedView.findViewById<TextView>(R.id.flightArrivalDate)
        val flightTime = generatedView.findViewById<TextView>(R.id.flightTime)
        val ticketPrice = generatedView.findViewById<TextView>(R.id.ticketPrice)
        val flightDiffDate = generatedView.findViewById<TextView>(R.id.flightDiffDate)


        val departureDate = data[p0].departureDate
        val arrivalDate = data[p0].arrivalDate
        val diffDate = calculateDayDifference(departureDate, arrivalDate)
        airlineName.text = data[p0].airlineName
        flightDepartureDate.text = formatTimeTo12Hour(departureDate)
        flightArrivalDate.text = formatTimeTo12Hour(arrivalDate)
        ticketPrice.text = formatCurrency(data[p0].ticketPrice)
        flightTime.text = calculateFlightTime(departureDate, arrivalDate)
        if(diffDate>0){
            flightDiffDate.text = "+" + diffDate.toString()
        }
        when (airlineName.text) {
            "대한항공" -> airlineLogo.setImageResource(R.drawable.korean_air_logo)
            "진에어항공" -> airlineLogo.setImageResource(R.drawable.jin_air_logo)
            "티웨이항공" -> airlineLogo.setImageResource(R.drawable.tway_air_logo)
            "제주항공" -> airlineLogo.setImageResource(R.drawable.jeju_air_logo)
            "아시아나항공" -> airlineLogo.setImageResource(R.drawable.asiana_air_logo)
        }

        return generatedView
    }

    private fun calculateFlightTime(departure: String, arrival: String): String {
        // 날짜 포맷 정의
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")

        // 문자열을 Date로 파싱
        val departureDate = dateFormat.parse(departure)
        val arrivalDate = dateFormat.parse(arrival)

        // 두 Date 간의 시간 차이를 밀리초 단위로 계산
        val durationMillis = arrivalDate.time - departureDate.time

        // 밀리초를 시간과 분으로 변환
        val hours = TimeUnit.MILLISECONDS.toHours(durationMillis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis) % 60

        // 결과를 "X시간 Y분" 형식의 문자열로 반환
        return "${hours}시간 ${minutes}분"
    }

    private fun formatCurrency(price: Int): String {
        val numberFormat = NumberFormat.getNumberInstance(Locale.KOREA)
        return numberFormat.format(price) + "원"
    }
    private fun calculateDayDifference(startDateString: String, endDateString: String): Int {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd") // 시간 정보 없이 날짜만 비교

        return try {
            val startDate = dateFormat.parse(startDateString)
            val endDate = dateFormat.parse(endDateString)
            val diffMillis = endDate.time - startDate.time // 밀리초 단위 차이 계산
            TimeUnit.MILLISECONDS.toDays(diffMillis).toInt() // 밀리초 → 일 단위 변환 후 반환
        } catch (e: Exception) {
            -1 // 예외 발생 시 기본값
        }
    }
    private fun formatTimeTo12Hour(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA) // 입력 형식
        val outputFormat = SimpleDateFormat("a h:mm", Locale.KOREA) // 출력 형식 ("오전/오후 h:mm")

        return try {
            val date = inputFormat.parse(dateString)
            outputFormat.format(date) // "오후 9:00" 등으로 반환
        } catch (e: Exception) {
            "시간 오류" // 예외 처리
        }
    }

}