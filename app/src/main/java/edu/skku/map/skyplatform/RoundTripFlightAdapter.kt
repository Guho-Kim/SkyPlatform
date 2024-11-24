package edu.skku.map.skyplatform

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class RoundTripFlightAdapter(val data:ArrayList<Flight>, val context: Context):BaseAdapter() {
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
        val generatedView = inflater.inflate(R.layout.item_round_trip_flight, null)

        val airlineName = generatedView.findViewById<TextView>(R.id.airlineName)
        val flightDate = generatedView.findViewById<TextView>(R.id.flightDepartureDate)
//        val textViewGroupNumber = generatedView.findViewById<TextView>(R.id.textViewGroupSize)
//        val textViewTime = generatedView.findViewById<TextView>(R.id.textViewLastTime)
//        val imageViewThumbnail = generatedView.findViewById<ImageView>(R.id.imageViewProfile)
//        val unReadCount = generatedView.findViewById<TextView>(R.id.unreadNumber)

        airlineName.text = data[p0].departureLocation
        flightDate.text = data[p0].departureDate
//        + " - " + data[p0].arrival_date

//        textViewTime.text = data[p0].lastTime
//        unReadCount.text = data[p0].unreadCount.toString()

//        if(data[p0].groupNumber>1){
//            textViewGroupNumber.text = "" + data[p0].groupNumber
//        }
//        else{
//            textViewGroupNumber.text = ""
//        }
//        imageViewThumbnail.setImageResource(data[p0].thumbnail)

        return generatedView
    }

}