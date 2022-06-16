package com.example.coin.com.example.interaktionsprinzipien

import kotlin.random.Random

class FourConnectCalculator (val depth : Int = 0) {

    fun calculateMove(virtualBoard : Array<Array<Int>>) : Int{
        if(depth == 0){
            return calculateRandomColumn(virtualBoard)
        }
        return -1
    }

    private fun calculateRandomColumn(virtualBoard : Array<Array<Int>>) : Int {
        val randomList = (0..6).shuffled()
        for(i in randomList) {
            if (virtualBoard[0][i] == 0) {
                return i
            }
        }
        return -1
    }
}