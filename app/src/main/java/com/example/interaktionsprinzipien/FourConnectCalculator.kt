package com.example.coin.com.example.interaktionsprinzipien

class FourConnectCalculator ( private val depth : Int = 0) {

    val scoreEnd = 1000000
    var winningList = mutableListOf<Pair<Int, Int>>()
    var rows = 6
    var columns = 7
    var iterations = 0


    fun calculateMove(virtualBoard: Array<Array<Int>>) : Int{

        if(depth == 0){
            return calculateRandomColumn(virtualBoard)
        }else{
            return generateComputerDecision(virtualBoard)
        }
    }

    private fun generateComputerDecision(virtualBoard: Array<Array<Int>>) :Int {
        val board = FourConnectBoard(this, virtualBoard, depth)

        if(!board.isFull() &&  scoreEnd != board.score() && scoreEnd != -board.score() ){ //
            iterations = 0 //Reset iterations
            var computerMove = maximizePlay(board, depth)
                return computerMove[0]

        }
        return -1
    }

    private fun maximizePlay(board: FourConnectBoard, currentDepth: Int, _alpha : Int = -100000, _beta : Int = 100000): Array<Int> {
        var score = board.score()
        var alpha = _alpha
        var beta = _beta
        if(board.isFinished(currentDepth, score)){
            return arrayOf(-1, score)
        }

        var max = arrayOf(-1, -99999)

        for(column in 0 until columns){
            var newBoard = board.copy()
            if(newBoard.place(column)){
                iterations++
                println(iterations)
                var nextMove = minimizePlay(newBoard, currentDepth-1, alpha, beta)

                if(max[0] == -1 || nextMove[1] > max[1]){
                    max[0] = column
                    max[1] = nextMove[1]
                    alpha = nextMove[1]
                }
                if(alpha >= beta)
                    return max
            }
        }
        return max
    }

    private fun minimizePlay(board: FourConnectBoard, currentDepth: Int, _alpha: Int = -100000, _beta : Int = 100000): Array<Int> {
        var score = board.score()
        var alpha = _alpha
        var beta = _beta
        if(board.isFinished(currentDepth, score)){
            return arrayOf(-1, score)
        }

        var min = arrayOf(-1, 99999)

        for(column in 0 until columns){
            var newBoard = board.copy()
            if(newBoard.place(column)){
                iterations++
                var nextMove = maximizePlay(newBoard, currentDepth-1, alpha, beta)

                if(min[0] == -1 || nextMove[1] < min[1]){
                    min[0] = column
                    min[1] = nextMove[1]
                    beta = nextMove[1]
                }
                if(alpha >= beta)
                    return min
            }
        }
        return min
    }

    private fun calculateRandomColumn(virtualBoard: Array<Array<Int>>) : Int {
        val randomList = (0..6).shuffled()
        for(i in randomList) {
            if (virtualBoard[0][i] == 0) {
                return i
            }
        }
        return -1
    }

    fun switchRound(playerId : Int) : Int{
        if(playerId == 2){
            return 1
        }else return 2
    }
}