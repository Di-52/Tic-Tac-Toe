package com.diest.tictactoe.models

import com.diest.tictactoe.GAME_FIELD_CELLS_IN_ROW
import com.diest.tictactoe.GAME_FIELD_ROW_COUNT

class GameFieldModel {

    private lateinit var _field: Array<Array<GameCellModel>>

    init {
        var tmpRow: MutableList<GameCellModel>
        var tmpField: MutableList<Array<GameCellModel>> = arrayListOf()
        for (i in 0 until GAME_FIELD_ROW_COUNT) {
            tmpRow = arrayListOf()
            for (j in 0 until GAME_FIELD_CELLS_IN_ROW) {
                tmpRow.add(GameCellModel(i, j))
            }
            tmpField.add(tmpRow.toTypedArray())
        }
        _field = tmpField.toTypedArray()
    }

    fun getCellState(cordX: Int, cordY: Int) = _field[cordX][cordY].getState()

    fun trySetCell(cordX: Int, cordY: Int, state: CellState): Boolean {
        var curState = _field[cordX][cordY].getState()
        if (curState != CellState.STATE_EMPTY) return false
        _field[cordX][cordY].setState(gameCellState = state)
        return true
    }
}