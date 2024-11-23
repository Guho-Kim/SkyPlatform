package edu.skku.map.skyplatform

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper // 전역 변수로 선언
    private lateinit var db: SQLiteDatabase      // 전역 변수로 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 데이터베이스 초기화
        dbHelper = DatabaseHelper(this)
        db = dbHelper.writableDatabase

        val btn_round_trip = findViewById<Button>(R.id.btn_round_trip)
        val btn_one_way = findViewById<Button>(R.id.btn_one_way)
        val btn_search = findViewById<Button>(R.id.btn_search)

        // 버튼 클릭 시 데이터베이스 작업
        btn_round_trip.setOnClickListener {
            val cursor = db.rawQuery("SELECT * FROM Flight", null)
            println("Flight 테이블의 모든 데이터:")
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val departureLocation = cursor.getString(cursor.getColumnIndexOrThrow("departure_location"))
                val arrivalLocation = cursor.getString(cursor.getColumnIndexOrThrow("arrival_location"))
                val departureDate = cursor.getString(cursor.getColumnIndexOrThrow("departure_date"))
                val arrivalDate = cursor.getString(cursor.getColumnIndexOrThrow("arrival_date"))
                val airline = cursor.getString(cursor.getColumnIndexOrThrow("airline"))
                val price = cursor.getInt(cursor.getColumnIndexOrThrow("price"))
                println("ID: $id, From: $departureLocation, To: $arrivalLocation, Airline: $airline, Price: $price, departureDate: $departureDate, arrivalDate: $arrivalDate")
            }
            cursor.close() // 반드시 닫기
        }

        btn_one_way.setOnClickListener {
            // 테이블 삭제 및 재생성
//            db.execSQL("DROP TABLE IF EXISTS Flight")
//            dbHelper.onCreate(db) // 테이블 재생성
//            println("Flight 테이블이 삭제되고 새로 생성되었습니다.")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close() // 데이터베이스 연결 닫기
        dbHelper.close()
        println("데이터베이스 연결 닫힘")
    }
}
