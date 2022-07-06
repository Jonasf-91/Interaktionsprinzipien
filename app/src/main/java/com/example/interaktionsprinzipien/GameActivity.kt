package com.example.interaktionsprinzipien


import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.example.fourconnect.FourConnectCalculator
import com.example.quiz.Answer
import com.example.quiz.Question
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_game_four_connect_content.*
import java.io.File
import kotlin.math.abs


class GameActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var countDownTimer: CountDownTimer
    private var numberHelpText = 1
    private var maxCoinNumber = 6
    private var currentCoinNumber = 0

    private lateinit var playerOne : Player
    private lateinit var playerTwo : Player

    private var virtualBoard = arrayOf(
        arrayOf(0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0)
    )
    private var depth = 2
    private lateinit var computer : FourConnectCalculator

    private lateinit var currentPlayer : Player
    val animation: Animation = AlphaAnimation(1F, 0F)

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
    private var coinOnArrow = false
    private var column = -1

    private val questions = arrayListOf<Question>()

    private lateinit var board : Array<Array<ImageView>>


    //ANFANG YASIN___________________________________________________________________________________________________
    private var musicOn = false
    private var volumeOn = false
    var player: MediaPlayer? = null

    private fun saveData(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putBoolean("BOOLEAN_GAME_OPTIONS", true)
        }.apply()
    }

    private fun loadData(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val booleanMusic = sharedPreferences.getBoolean("BOOLEAN_MUSIC", false)
        val booleanVolume = sharedPreferences.getBoolean("BOOLEAN_VOLUME", true)
        val booleanFirstTurn = sharedPreferences.getBoolean("BOOLEAN_FIRST_TURN", false)
        val intDifficulty= sharedPreferences.getInt("INT_DIFFICULTY", 0)
        val intCoin = sharedPreferences.getInt("INT_COINS", 10)


        musicOn = booleanMusic
        volumeOn = booleanVolume
        depth = intDifficulty
        maxCoinNumber = intCoin

        currentPlayer = if(booleanFirstTurn){
            playerOne
        }else playerTwo

    }

    fun playMusic() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.marsel_minga)
            player!!.isLooping = true
            player!!.start()
        }
        if(player?.isPlaying == false){
            player!!.start()
        }
    }

    fun pauseMusic() {
        if (player?.isPlaying == true) player?.pause()
    }

    override fun onStop() {
        super.onStop()


        //HIER SOLLEN DIE PREFERENCES GELÖSCHT WERDEN, UM BEI NEUSTART DER APP KOMPLETT NEU ANFANGEN ZU KÖNNEN. FUNKTIONIERT NICHT!!!!!
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear()
        sharedPreferences.edit().apply()

        if (player != null) {
            player!!.release()
            player = null
        }
    }
    //ENDE YASIN_____________________________________________________________________________________________________



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val computerImage: Drawable? = ResourcesCompat.getDrawable(this.resources, R.drawable.four_connect_player2, null)
        var playerImage : Drawable? = ResourcesCompat.getDrawable(this.resources, R.drawable.four_connect_player1, null)

        val filePath: File = getFileStreamPath("mycoin")
        val d = Drawable.createFromPath(filePath.toString())
        if(d != null){
            playerImage = d
        }


        if(playerImage != null || computerImage != null){
            playerOne = Player(1, playerImage, "Jonas")
            playerTwo = Player(2,computerImage )
        }


        //ANFANG YASIN___________________________________________________________________________________________________
        saveData()
        loadData()
        computer = FourConnectCalculator(depth)
        if(musicOn && !volumeOn){
            playMusic()
        }
        //ENDE YASIN_____________________________________________________________________________________________________






        // ------------- Define Questions for Quiz -----------------
        questions.add(
            Question(
                "Gegen welche(s) Interaktionsprinzip(ien) wurde bei der runterlaufenden Uhr verstoßen?",
                getString(R.string.answerQuizGame01),
                listOf(
                    Answer("Aufgabenangemessen", true),
                    Answer("Selbstbeschreibend", false),
                    Answer("Steuerbarkeit", false),
                    Answer("Erwartungskonformität", true),
                ),
                R.drawable.quizgame1,
                false
            )
        )

        questions.add(
            Question(
                "Gegen welches Interaktionsprinzip wurde beim Setzen eines Coins verstoßen?",
                getString(R.string.answerQuizGame02),
                listOf(
                    Answer("Steuerbarkeit", false),
                    Answer("Robust gegen Benutzerfehler", false),
                    Answer("Erlernbarkeit", true),
                    Answer("Erwartungskonformität", true),
                ),
                R.drawable.quizgame2,
            false),

        )
        questions.add(
            Question(
                "Was ist das Problem an der Hilfe?",
                getString(R.string.answerQuizGame03),
                listOf(
                    Answer("Die Hilfe hilft nicht", false),
                    Answer("Toasts sind hier aufgabenunangemessen", true),
                    Answer("Das mentale Modell des:der Benutzer:in wird nicht beachtet", true),
                    Answer("Der Button ist nicht selbstbeschreibend", false),
                    Answer("Mist, ich habe die Hilfe nicht ausprobiert", false),
                ),
                R.drawable.quizgame3,
                false),
        )
        // --------------------------------------------------------


        four_connect_btn_options.setOnClickListener {
            val intent = Intent(this, OptionActivity::class.java)
            startActivity(intent)
        }

        four_connect_btn_help.setOnClickListener {
            var text = ""
            when(numberHelpText) {
                1 -> text = "Ernsthaft, du brauchst Hilfe?"
                2 -> text = "So schnell willst du schon aufgeben?"
                3 -> text = "Das ist ja langweilig.."
                4 -> text = "Klick ein Dreieck und schmeiß dein Smartphone aus dem Fenster.."
                5 -> text = "..also kurz schütteln reicht auch."
                6 -> text = "Sorry, das geht jetzt hier wieder von vorne los.."
            }
            numberHelpText++
            if(numberHelpText > 6) numberHelpText = 1
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }

        buildBoard()

        setUpSensor()

        startFourConnect()

    }

    private fun computerToMove() {

        val virtualBoard2 = copyBoard(virtualBoard)
        val column = computer.calculateMove(virtualBoard2)

        if(column == -1){
            Toast.makeText(this, "Alles voll oder Fehler?", Toast.LENGTH_LONG).show()
        }else{
            val row = getRowNumber(column)
            if(row != -1){

                placeCoinOnBoard(row, column)
                if(fourInRow(row, column)){
                    showResult(currentPlayer.id)
                }else{
                    switchPlayer()
                    setUpArrowButtons()
                    startCountdown()
                }
            }
        }
    }

    private fun showResult(result : Int) {

        if((result == 2 || result == 1) && depth > 0){
            blinkWinningList()
        }
        tv_four_connect_result.text = writeResultText(result)
        tv_four_connect_result.isVisible = true
        setUpNextButton()
    }

    private fun blinkWinningList() {
         //to change visibility from visible to invisible
        animation.duration = 400 //1 second duration for each animation cycle
        animation.interpolator = LinearInterpolator()
        animation.repeatCount = Animation.INFINITE //repeating indefinitely
        animation.repeatMode = Animation.REVERSE //animation will start from end point once ended.

        for(element in 0 until 4){
            val row = computer.winningList[element].first
            val column = computer.winningList[element].second
            board[row][column].startAnimation(animation)
        }
    }

    private fun setUpNextButton() {

        four_connect_btn_next.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java).apply{
                putParcelableArrayListExtra("questions", questions)
            }
            startActivity(intent)
        }

        four_connect_btn_again.setOnClickListener {
            restartGame()
        }
        four_connect_btn_next.isVisible = true
        four_connect_btn_again.isVisible = true
    }

    private fun restartGame() {
        loadData()

        currentPlayer = playerTwo
        for(row in virtualBoard.indices){
            for(column in 0 until virtualBoard[0].size){
                val emptyImage: Drawable? = ResourcesCompat.getDrawable(this.resources, R.drawable.four_connect_empty_coin, null)
                virtualBoard[row][column] = 0
                board[row][column].setImageDrawable(emptyImage)
            }
        }
        animation.cancel()
        four_connect_btn_next.isVisible = false
        four_connect_btn_again.isVisible = false
        tv_four_connect_result.isVisible = false
        currentCoinNumber = 0
        startFourConnect()

    }

    private fun writeResultText(result : Int) : String {
        var text = ""
        if(result == 1) {
            when (depth) {
                0 -> text =
                    "Auf der Schwierigkeitsstufe gewinnt ein kleines Kind gegen den Computer.\nJetzt spiel bitte mal richtig."
                2 -> text =
                    "Ja, gewonnen hast Du.\nAber jetzt hab mal bisschen Anspruch und erhöhe die Schwierigkeit.\nDann wollen wir sehen, wer zuletzt lacht "
                4 -> text =
                    "Nicht schlecht. Du scheinst ein kluges Köpfchen zu sein."
            }
        }else if(result == 2) {
               text =  "Netter Versuch.\nAber vielleicht versuchst du es noch mal."
            }else{
                text = "Deine Coins sind alle.\nEinfach nicht so geizig sein."
        }
            return text
    }

    private fun startCountdown(){
        val countDownStart = 6
        var countDownProgress = 0
        countDownTimer = object : CountDownTimer(10000, 1000){
            override fun onTick(p0: Long) {
                countDownProgress ++
                tv_countdown.text = (countDownStart - countDownProgress).toString()
            }

            override fun onFinish() {
                tv_countdown.text = "Entscheide Dich!"
            }
        }.start()
    }

    private fun setUpArrowButtons(){
            ib_four_connect_arrow0.setOnClickListener {
                resetArrows()
                ib_four_connect_arrow0.setImageDrawable(currentPlayer.img)
                column = 0
                coinOnArrow = true
            }
            ib_four_connect_arrow1.setOnClickListener {
                resetArrows()
                ib_four_connect_arrow1.setImageDrawable(currentPlayer.img)
                column = 1
                coinOnArrow = true
            }
            ib_four_connect_arrow2.setOnClickListener {
                resetArrows()
                ib_four_connect_arrow2.setImageDrawable(currentPlayer.img)
                column = 2
                coinOnArrow = true
            }
            ib_four_connect_arrow3.setOnClickListener {
                resetArrows()
                ib_four_connect_arrow3.setImageDrawable(currentPlayer.img)
                column = 3
                coinOnArrow = true
            }
            ib_four_connect_arrow4.setOnClickListener {
                resetArrows()
                ib_four_connect_arrow4.setImageDrawable(currentPlayer.img)
                column = 4
                coinOnArrow = true
            }
            ib_four_connect_arrow5.setOnClickListener {
                resetArrows()
                ib_four_connect_arrow5.setImageDrawable(currentPlayer.img)
                column = 5
                coinOnArrow = true
            }
            ib_four_connect_arrow6.setOnClickListener {
                resetArrows()
                ib_four_connect_arrow6.setImageDrawable(currentPlayer.img)
                column = 6
                coinOnArrow = true
            }
    }

    private fun resetArrows(){
        val arrowImage: Drawable? = ResourcesCompat.getDrawable(this.resources, R.drawable.four_connect_arrow, null)
        ib_four_connect_arrow0.setImageDrawable(arrowImage)
        ib_four_connect_arrow1.setImageDrawable(arrowImage)
        ib_four_connect_arrow2.setImageDrawable(arrowImage)
        ib_four_connect_arrow3.setImageDrawable(arrowImage)
        ib_four_connect_arrow4.setImageDrawable(arrowImage)
        ib_four_connect_arrow5.setImageDrawable(arrowImage)
        ib_four_connect_arrow6.setImageDrawable(arrowImage)
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
                    (coinOnArrow && xDifference > shakeThreshold  && yDifference > shakeThreshold) ||
                    (coinOnArrow && xDifference > shakeThreshold  && zDifference > shakeThreshold) ||
                    (coinOnArrow && zDifference > shakeThreshold  && yDifference > shakeThreshold)
                ){
                    finishPlayersMove()
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
        board[row][column].setImageDrawable(currentPlayer.img)
    }

    private fun getRowNumber(column : Int) : Int {
        for(rowIndex in virtualBoard.size-1 downTo 0){
            if(virtualBoard[rowIndex][column] == 0){
                virtualBoard[rowIndex][column] = currentPlayer.id
                return rowIndex
                }
            }
        Toast.makeText(this, "Die Spalte ist voll", Toast.LENGTH_SHORT).show()
        return -1
        }


    private fun switchPlayer(){
        if(currentPlayer == playerOne){
            currentPlayer = playerTwo
        }else{
            currentPlayer = playerOne
        }
    }

    private fun fourInRow(row : Int, column: Int) : Boolean{
        if(checkRowOfVirtualBoard(row))return true
        if(checkColumnOfVirtualBoard(column)) return true
        if(checkDiagnoalOfVirtualBoard()) return true
        return false
    }

    private fun checkDiagnoalOfVirtualBoard(): Boolean {
        val maxX = virtualBoard.size
        val maxY = virtualBoard[0].size
        val directions = arrayOf(arrayOf(1,0), arrayOf(1,-1), arrayOf(1,1), arrayOf(0,1))
        for(d in directions){
            val dx = d[0]
            val dy = d[1]
            for(x in 0 until maxX-1){
                for(y in 0 until maxY-1){
                    val lastX = x + 3*dx
                    val lastY = y + 3*dy
                    if(lastX in 0 until maxX && lastY in 0 until maxY){
                        val check = virtualBoard[x][y]
                        if(
                            check != 0 &&
                            check == virtualBoard[x+dx][y+dy] &&
                            check == virtualBoard[x+2*dx][y+2*dy] &&
                            check == virtualBoard[lastX][lastY]
                            )return true
                    }
                }
            }
        }
        return false
    }

    private fun checkRowOfVirtualBoard(row : Int) : Boolean{

        var counter = 0

        for(i in 0 until virtualBoard[0].size){
            if(virtualBoard[row][i] == currentPlayer.id)counter++
            else counter = 0
            if(counter >= 4)return true
        }
        return false
    }

    private fun checkColumnOfVirtualBoard(column : Int) : Boolean{
        var counter = 0

        for(element in virtualBoard){
            if(element[column] == currentPlayer.id)counter++
            else counter = 0
            if(counter >= 4)return true
        }
        return false
    }

    class Player(val id : Int, val img : Drawable?, var name : String = "Computer")

    private fun startFourConnect(){
        if(currentPlayer.id == 1){
            setUpArrowButtons()
            startCountdown()
        }else computerToMove()
    }

    private fun finishPlayersMove(){
        val row = getRowNumber(column)
        if(row != -1){
            placeCoinOnBoard(row,column)
            currentCoinNumber++
            coinOnArrow = false
            resetArrows()
            countDownTimer.cancel()
            if(fourInRow(row, column)){
                showResult(currentPlayer.id)
            }else if(currentCoinNumber >= maxCoinNumber){
                showResult(-1)
            }
            else{
                switchPlayer()
                computerToMove()
            }
        }
    }

    private fun copyBoard(board : Array<Array<Int>>):Array<Array<Int>>{
        val newBoard = arrayOf(
            arrayOf(0,0,0,0,0,0,0),
            arrayOf(0,0,0,0,0,0,0),
            arrayOf(0,0,0,0,0,0,0),
            arrayOf(0,0,0,0,0,0,0),
            arrayOf(0,0,0,0,0,0,0),
            arrayOf(0,0,0,0,0,0,0)
        )

        for(element in board.indices){
            newBoard[element] = board[element].copyOf()
        }
        return newBoard
    }

    override fun onBackPressed() {}

}