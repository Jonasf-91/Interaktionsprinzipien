package com.example.interaktionsprinzipien

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class GameActivity : AppCompatActivity(), SensorEventListener{


    private lateinit var mSensorManager : SensorManager
    private lateinit var mAccelerometer: Sensor

    private lateinit var square: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Game activity")
        setContentView(R.layout.activity_game)

       square = findViewById(R.id.coin)

        setUpSensor()


    }

    private fun setUpSensor(){
        //initialize sensor variables
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event?.sensor?.type == Sensor.TYPE_ACCELEROMETER){
            val sides = event.values[0]
            val upDown = event.values[1]

            square.apply{
                x -= sides * 3f
                y += upDown * 3f
            }

            square
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }
}