package com.example.interaktionsprinzipien


import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_game_four_connect_content.*
import kotlin.math.abs


class GameActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var countDownTimer: CountDownTimer

    private var player1 =R.drawable.four_connect_player1
    private var player2 =R.drawable.four_connect_player2
    private var currentPlayer = player1

    private lateinit var mSensorManager : SensorManager
    private lateinit var mAccelerometer: Sensor
    private var itIsNotFirstTime : Boolean = false
    private var currentX = 0F
    private var currentY = 0F
    private var currentZ = 0F
    private var lastX = 0F
    private var lastY  = 0F
    private var lastZ = 0F
    private var xDifference = 0F
    private var yDifference = 0F
    private var zDifference = 0F
    private var shakeThreshold = 5F
    private var coinHasPosition = false
    private var columnForCoin = -1

    private var virtualBoard = arrayOf(5,5,5,5,5,5,5)
    private lateinit var board : Array<Array<ImageView>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        four_connect_btn_options.setOnClickListener {
            val intent = Intent(this, OptionActivity::class.java)
            startActivity(intent)
        }

        buildBoard()
        //starts OnClickListener for all arrowButtons
        setUpArrowButtons()

        //starts Countdown for the time above the playboard
        startCountdown()

        setUpSensor()
    }

    private fun startCountdown(){
        val countDownStart = 6
        var countDownProgress = 0
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
            ib_four_connect_arrow0.setImageResource(currentPlayer)
            columnForCoin = 0
            coinHasPosition = true
        }
        ib_four_connect_arrow1.setOnClickListener {
            resetArrows()
            ib_four_connect_arrow1.setImageResource(currentPlayer)
            columnForCoin = 1
            coinHasPosition = true
        }
        ib_four_connect_arrow2.setOnClickListener {
            resetArrows()
            ib_four_connect_arrow2.setImageResource(currentPlayer)
            columnForCoin = 2
            coinHasPosition = true
        }
        ib_four_connect_arrow3.setOnClickListener {
            resetArrows()
            ib_four_connect_arrow3.setImageResource(currentPlayer)
            columnForCoin = 3
            coinHasPosition = true
        }
        ib_four_connect_arrow4.setOnClickListener {
            resetArrows()
            ib_four_connect_arrow4.setImageResource(currentPlayer)
            columnForCoin = 4
            coinHasPosition = true
        }
        ib_four_connect_arrow5.setOnClickListener {
            resetArrows()
            ib_four_connect_arrow5.setImageResource(currentPlayer)
            columnForCoin = 5
            coinHasPosition = true
        }
        ib_four_connect_arrow6.setOnClickListener {
            resetArrows()
            ib_four_connect_arrow6.setImageResource(currentPlayer)
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

                    val row = getRowNumber(columnForCoin)
                    if(row != -1){
                        placeCoinOnBoard(row,columnForCoin)
                        switchPlayer()
                        coinHasPosition = false
                        resetArrows()
                        countDownTimer.cancel()
                        startCountdown()
                    }
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

    private fun buildBoard(){
        board = arrayOf(
            arrayOf(
                iv_four_connect_coin_row1_column0, iv_four_connect_coin_row1_column1, iv_four_connect_coin_row1_column2,iv_four_connect_coin_row1_column3,iv_four_connect_coin_row1_column4,iv_four_connect_coin_row1_column5,iv_four_connect_coin_row1_column6
            ),
            arrayOf(
                iv_four_connect_coin_row2_column0, iv_four_connect_coin_row2_column1, iv_four_connect_coin_row2_column2,iv_four_connect_coin_row2_column3,iv_four_connect_coin_row2_column4,iv_four_connect_coin_row2_column5,iv_four_connect_coin_row2_column6
            ),
            arrayOf(
                iv_four_connect_coin_row3_column0, iv_four_connect_coin_row3_column1, iv_four_connect_coin_row3_column2,iv_four_connect_coin_row3_column3,iv_four_connect_coin_row3_column4,iv_four_connect_coin_row3_column5,iv_four_connect_coin_row3_column6
            ),
            arrayOf(
                iv_four_connect_coin_row4_column0, iv_four_connect_coin_row4_column1, iv_four_connect_coin_row4_column2,iv_four_connect_coin_row4_column3,iv_four_connect_coin_row4_column4,iv_four_connect_coin_row4_column5,iv_four_connect_coin_row4_column6
            ),
            arrayOf(
                iv_four_connect_coin_row5_column0, iv_four_connect_coin_row5_column1, iv_four_connect_coin_row5_column2,iv_four_connect_coin_row5_column3,iv_four_connect_coin_row5_column4,iv_four_connect_coin_row5_column5,iv_four_connect_coin_row5_column6
            ),
            arrayOf(
                iv_four_connect_coin_row6_column0, iv_four_connect_coin_row6_column1, iv_four_connect_coin_row6_column2,iv_four_connect_coin_row6_column3,iv_four_connect_coin_row6_column4,iv_four_connect_coin_row6_column5,iv_four_connect_coin_row6_column6
            )

        )
    }

    private fun placeCoinOnBoard(row : Int, column : Int){
        board[row][column].setImageResource(currentPlayer)
    }

    private fun getRowNumber(column : Int) : Int {
        val num = virtualBoard[column]
        if(num < 0){
            Toast.makeText(this, "Die Spalte ist voll", Toast.LENGTH_SHORT).show()
            return -1
        }else{
                virtualBoard[column] = num - 1
                return num
            }
    }

    private fun switchPlayer(){
        if(currentPlayer == player1){
            currentPlayer = player2
        }else currentPlayer = player1
    }
}