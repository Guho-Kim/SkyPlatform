package edu.skku.map.skyplatform

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // 테이블 생성 쿼리
        val createTableQuery = """
            CREATE TABLE Flight (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                departure_location TEXT NOT NULL,
                arrival_location TEXT NOT NULL,
                departure_date datetime NOT NULL,
                arrival_date datetime NOT NULL,
                airline TEXT NOT NULL,
                price int NOT NULL
            )
        """
        db.execSQL(createTableQuery)

        // 데이터 삽입 쿼리
        val insertDataQueries = listOf(
            """
        INSERT INTO Flight (departure_location, arrival_location, departure_date, arrival_date, airline, price)
        VALUES 
        ('인천', '뉴욕', '2024-12-01 08:30', '2024-12-01 14:45', '대한항공', 1200000),
        ('인천', '부산', '2024-12-02 10:00', '2024-12-02 13:00', '대한항공', 200000)
        """,
            """
        INSERT INTO Flight (departure_location, arrival_location, departure_date, arrival_date, airline, price)
        VALUES ('인천', '런던', '2024-12-03 18:00', '2024-12-04 06:00', '아시아나항공', 1500000)
        """
        )

        // 데이터 삽입 실행
        for (query in insertDataQueries) {
            db.execSQL(query)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // 업그레이드 로직
        db.execSQL("DROP TABLE IF EXISTS Flight")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "flight.db" // 데이터베이스 이름
        const val DATABASE_VERSION = 1       // 데이터베이스 버전
    }
}
