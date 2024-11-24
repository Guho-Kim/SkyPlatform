package edu.skku.map.skyplatform

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import edu.skku.map.skyplatform.adapter.OneWayFlightAdapter
import java.text.SimpleDateFormat
import java.util.Locale

class FlightList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_list)

        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.readableDatabase



        val btnBack = findViewById<Button>(R.id.btn_back)
        val textAirport = findViewById<TextView>(R.id.textAirport)
        val textResultCnt = findViewById<TextView>(R.id.textResultCnt)
        val textDepartureDate = findViewById<TextView>(R.id.textDepartureDate)


        textDepartureDate.text = formatDateToKoreanStyle(intent.getStringExtra(MainActivity.EXT_DEPARTURE_DATE).toString())
        val roundTripOption = intent.getBooleanExtra(MainActivity.EXT_ROUND_TRIP_OPTION, true)
        val departureLocation = intent.getStringExtra(MainActivity.EXT_DEPARTURE_LOCATION)
        val arrivalLocation = intent.getStringExtra(MainActivity.EXT_ARRIVAL_LOCATION)
        val departureDate = intent.getStringExtra(MainActivity.EXT_DEPARTURE_DATE)
        val items = ArrayList<Flight>()
        textAirport.text = intent.getStringExtra(MainActivity.EXT_DEPARTURE_LOCATION) + " - " + intent.getStringExtra(MainActivity.EXT_ARRIVAL_LOCATION)


//      round trip
        if(roundTripOption){
        }
//      one way
        else{
//            val cursor = db.rawQuery("SELECT * FROM Flight", null)
            val cursor = db.rawQuery(
                "SELECT * FROM Flight WHERE departure_location = ? AND arrival_location = ? AND date(departure_date) = ?",
                arrayOf(departureLocation, arrivalLocation, departureDate)
            )
            var cnt=0;
            while (cursor.moveToNext()) {
                val departureDate = cursor.getString(cursor.getColumnIndexOrThrow("departure_date"))
                val arrivalDate = cursor.getString(cursor.getColumnIndexOrThrow("arrival_date"))
                val airline = cursor.getString(cursor.getColumnIndexOrThrow("airline"))
                val price = cursor.getInt(cursor.getColumnIndexOrThrow("price"))

                // Flight 객체 추가
                items.add(
                    Flight(
                        departureLocation.toString(),
                        arrivalLocation.toString(),
                        departureDate,
                        arrivalDate,
                        airline,
                        price
                    )
                )
                cnt+=1
            }
            cursor.close()
            textResultCnt.text="결과 $cnt 개"


            val myAdapter = OneWayFlightAdapter(items, applicationContext)
            val listView = findViewById<ListView>(R.id.listViewFlight)
            listView.adapter = myAdapter
        }



        btnBack.setOnClickListener{
            finish()
        }

    }

    private fun formatDateToKoreanStyle(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA) // 입력 형식
        val outputFormat = SimpleDateFormat("MM월 dd일", Locale.KOREA)  // 출력 형식

        return try {
            val date = inputFormat.parse(dateString) // 문자열을 Date로 변환
            outputFormat.format(date)               // 변환된 날짜 반환
        } catch (e: Exception) {
            "날짜 오류" // 파싱 실패 시 기본값 반환
        }
    }
}