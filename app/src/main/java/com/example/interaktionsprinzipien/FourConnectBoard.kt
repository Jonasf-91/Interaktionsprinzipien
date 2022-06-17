package com.example.coin.com.example.interaktionsprinzipien

class FourConnectBoard (private val calc : FourConnectCalculator, var field : Array<Array<Int>>, var currentPlayer : Int, private val computerId : Int = 2, private val humanId : Int = 1 ){

    fun isFinished(currentDepth : Int, score : Int) : Boolean{
        return currentDepth == 0 || isFull() || score == calc.scoreEnd || score == -calc.scoreEnd
    }

    fun isFull(): Boolean {
        for(i in 0 until calc.columns){
            if(field[0][i] == 0)
                return false
        }
        return true
    }

    fun place(column : Int) :  Boolean{
        //check if column  valid
        if(field[0][column] == 0 && column >= 0 && column < calc.columns){
            for(i in field.size-1 downTo 0){
                if(field[i][column] == 0){
                    field[i][column] = currentPlayer
                    break
                }
            }
            currentPlayer = calc.switchRound(currentPlayer)
            return true
        }
        return false
    }

    private fun scorePosition(_row : Int, _column : Int, delta_x : Int, delta_y : Int) : Int{
        var row = _row
        var column = _column
        var computerPoints = 0
        var humanPoints = 0
        var winningListComputer = mutableListOf<Pair<Int,Int>>()
        var winningListHuman = mutableListOf<Pair<Int,Int>>()

        for(i in 0 until 4){
            if(field[row][column] == humanId){
                winningListHuman.add(Pair(row, column))
                humanPoints++
            }else if(field[row][column] == computerId){
                winningListComputer.add(Pair(row, column))
                computerPoints++
            }
            row += delta_y
            column += delta_x
        }

        if(humanPoints == 4){
            calc.winningList = winningListHuman
            return -calc.scoreEnd
        }else if(computerPoints == 4){
            calc.winningList = winningListComputer
            return calc.scoreEnd
        }
        return computerPoints
    }

    fun score() : Int{
        var points: Int

        var verticalPoints = 0
        var horizontalPoints = 0
        var diagonalPoints1 = 0
        var diagonalPoints2 = 0

        //check for vertical score
        for(row in 0 until calc.rows-3){
            for(column in 0 until calc.columns){
                var score = scorePosition(row, column, 0, 1)
                if(score == calc.scoreEnd) return calc.scoreEnd
                if(score == -calc.scoreEnd) return -calc.scoreEnd
                verticalPoints += score
            }
        }
        //check for horizontal score
        for(row in 0 until calc.rows){
            for(column in 0 until  calc.columns-3){
                var score = scorePosition(row, column, 1, 0)
                if(score == calc.scoreEnd) return calc.scoreEnd
                if(score == -calc.scoreEnd) return -calc.scoreEnd
                horizontalPoints += score
            }
        }
        //check for daigonalPoints1 score
        for(row in 0 until calc.rows-3){
            for(column in 0 until  calc.columns-3){
                var score = scorePosition(row, column, 1, 1)
                if(score == calc.scoreEnd) return calc.scoreEnd
                if(score == -calc.scoreEnd) return -calc.scoreEnd
                diagonalPoints1 += score
            }
        }
        //check for daigonalPoints2 score
        for(row in 3 until calc.rows-3){
            for(column in 0 until  calc.columns-3){
                var score = scorePosition(row, column, 1, -1)
                if(score == calc.scoreEnd) return calc.scoreEnd
                if(score == -calc.scoreEnd) return -calc.scoreEnd
                diagonalPoints2 += score
            }
        }
        points = horizontalPoints + verticalPoints + diagonalPoints1 + diagonalPoints2
        return points
    }

    fun copy() : FourConnectBoard{
        var newField = copyBoard(field)
        return FourConnectBoard(calc, newField, currentPlayer)
    }

    private fun copyBoard(board : Array<Array<Int>>):Array<Array<Int>>{
        var newBoard = arrayOf(
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
}