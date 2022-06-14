package com.example.interaktionsprinzipien


import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_game_four_connect_content.*
import kotlin.math.abs


class GameActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var countDownTimer: CountDownTimer
    private var countDownProgress : Int = 0
    private var countDownStart : Int = 6

    private lateinit var mSensorManager : SensorManager
    private lateinit var mAccelerometer: Sensor
    private var itIsNotFirstTime : Boolean = false
    private var currentX : Float = 0F
    private var currentY : Float = 0F
    private var currentZ : Float = 0F
    private var lastX : Float = 0F
    private var lastY : Float = 0F
    private var lastZ : Float = 0F
    private var xDifference : Float = 0F
    private var yDifference : Float = 0F
    private var zDifference : Float = 0F
    private var shakeThreshold : Float = 5F
    private var coinHasPosition : Boolean = false
    private var columnForCoin : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        four_connect_btn_options.setOnClickListener {
            val intent = Intent(this, OptionActivity::class.java)
            startActivity(intent)
        }

        //starts OnClickListener for all arrowButtons
        setUpArrowButtons()

        //starts Countdown for the time above the playboard
        startCountdown()

        setUpSensor()

    }

    private fun startCountdown(){
        countDownTimer = object : CountDownTimer(10000, 1000){
            override fun onTick(p0: Long) {
                countDownProgress ++
                tv_countdown.text = (countDownStart - countDownProgress).toString()
                //TODO add sound ticker
            }

            override fun onFinish() {
                //TODO get text from string.xml instead of hard-coding
                tv_countdown.text = "Entscheide Dich!"
                //TODO add sound highlight
            }
        }.start()
    }

    private fun setUpArrowButtons(){
        ib_four_connect_arrow0.setOnClickListener {
            resetArrows()
            ib_four_connect_arrow0.setImageResource(R.drawable.four_connect_coin_test)
            columnForCoin = 0
            coinHasPosition = true
        }
        ib_four_connect_arrow1.setOnClickListener {
            resetArrows()
            ib_four_connect_arrow1.setImageResource(R.drawable.four_connect_coin_test)
            columnForCoin = 1
            coinHasPosition = true
        }
        ib_four_connect_arrow2.setOnClickListener {
            resetArrows()
            ib_four_connect_arrow2.setImageResource(R.drawable.four_connect_coin_test)
            columnForCoin = 2
            coinHasPosition = true
        }
        ib_four_connect_arrow3.setOnClickListener {
            resetArrows()
            ib_four_connect_arrow3.setImageResource(R.drawable.four_connect_coin_test)
            columnForCoin = 3
            coinHasPosition = true
        }
        ib_four_connect_arrow4.setOnClickListener {
            resetArrows()
            ib_four_connect_arrow4.setImageResource(R.drawable.four_connect_coin_test)
            columnForCoin = 4
            coinHasPosition = true
        }
        ib_four_connect_arrow5.setOnClickListener {
            resetArrows()
            ib_four_connect_arrow5.setImageResource(R.drawable.four_connect_coin_test)
            columnForCoin = 5
            coinHasPosition = true
        }
        ib_four_connect_arrow6.setOnClickListener {
            resetArrows()
            ib_four_connect_arrow6.setImageResource(R.drawable.four_connect_coin_test)
            columnForCoin = 6
            coinHasPosition = true
        }
    }

    private fun resetArrows(){
        ib_four_connect_arrow0.setImageResource(R.drawable.four_connect_arrow)
        ib_four_connect_arrow1.setImageResource(R.drawable.four_connect_arrow)
        ib_four_connect_arrow2.setImageResource(R.drawable.four_connect_arrow)
        ib_four_connect_arrow3.setImageResource(R.drawable.four_connect_arrow)
        ib_four_connect_arrow4.setImageResource(R.drawable.four_connect_arrow)
        ib_four_connect_arrow5.setImageResource(R.drawable.four_connect_arrow)
        ib_four_connect_arrow6.setImageResource(R.drawable.four_connect_arrow)
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
            currentX = event.values[0]
            currentY = event.values[1]
            currentZ = event.values[2]

            if(itIsNotFirstTime){

                xDifference = abs(lastX-currentX)
                yDifference = abs(lastY-currentY)
                zDifference = abs(lastZ-currentZ)

                if(
                    (coinHasPosition && xDifference > shakeThreshold  && yDifference > shakeThreshold) ||
                    (coinHasPosition && xDifference > shakeThreshold  && zDifference > shakeThreshold) ||
                    (coinHasPosition && zDifference > shakeThreshold  && yDifference > shakeThreshold)
                ){
                    when(columnForCoin){
                        0 -> iv_four_connect_coin_row6_column0.setImageResource(R.drawable.four_connect_coin_test)
                        1 -> iv_four_connect_coin_row6_column1.setImageResource(R.drawable.four_connect_coin_test)
                        2 -> iv_four_connect_coin_row6_column2.setImageResource(R.drawable.four_connect_coin_test)
                        3 -> iv_four_connect_coin_row6_column3.setImageResource(R.drawable.four_connect_coin_test)
                        4 -> iv_four_connect_coin_row6_column4.setImageResource(R.drawable.four_connect_coin_test)
                        5 -> iv_four_connect_coin_row6_column5.setImageResource(R.drawable.four_connect_coin_test)
                        6 -> iv_four_connect_coin_row6_column6.setImageResource(R.drawable.four_connect_coin_test)
                    }
                    coinHasPosition = false
                    resetArrows()
                }

            }
            lastX = currentX
            lastY = currentY
            lastZ = currentZ
            itIsNotFirstTime = true
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }

}