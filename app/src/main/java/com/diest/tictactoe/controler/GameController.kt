package com.diest.tictactoe.controler

import com.diest.tictactoe.GAME_FIELD_CELLS_IN_ROW
import com.diest.tictactoe.GAME_FIELD_ROW_COUNT
import com.diest.tictactoe.models.CellState
import com.diest.tictactoe.models.GameFieldModel

class GameController {

    private val _gameField: GameFieldModel = GameFieldModel()
    private var _observers = ArrayList<IGameControllerObserver>()

    fun attachObserver(observer: IGameControllerObserver) {
        _observers.add(observer)
    }

    private fun <T : Any?> setMsg(msg: T) {
        notify(msg = msg)
    }

    private fun <T : Any?> notify(msg: T) {
        for (o in _observers) o.update(msg = msg)
    }

    fun set(cordX: Int, cordY: Int, state: CellState): Boolean {
        var result = _gameField.trySetCell(cordX = cordX, cordY = cordY, state = state)
        checkGameEnd()
        return result
    }

    private fun checkGameEnd() {

        for (i in 0 until GAME_FIELD_ROW_COUNT) {
            var result = checkRow(i)
            if (result != null) {
                setMsg("Выиграл " + if (result == CellState.STATE_X) "крестики" else "нолики")
                return
            }
        }

        for (i in 0 until GAME_FIELD_CELLS_IN_ROW) {
            var result = checkColumn(i)
            if (result != null) {
                setMsg("Выиграл " + if (result == CellState.STATE_X) "крестики" else "нолики")
                return
            }
        }

        var result = checkDiagonalLeft()
        if (result != null) {
            setMsg("Выиграл " + if (result == CellState.STATE_X) "крестики" else "нолики")
            return
        }

        result = checkDiagonalRight()
        if (result != null) {
            setMsg("Выиграл " + if (result == CellState.STATE_X) "крестики" else "нолики")
            return
        }

        if (isAllCellFilled()){
            setMsg("Ничья")
            return
        }
    }

    private fun checkRow(rowNum: Int): CellState? {
        var result: CellState? = _gameField.getCellState(cordX = rowNum, 0)
        for (i in 0 until GAME_FIELD_CELLS_IN_ROW) {
            if (result != _gameField.getCellState(cordX = rowNum, cordY = i)) {
                result = null
                break
            }
        }
        if (result == CellState.STATE_EMPTY) result = null
        return result
    }

    private fun checkColumn(columNum: Int): CellState? {
        var result: CellState? = _gameField.getCellState(cordX = 0, cordY = columNum)
        for (i in 0 until GAME_FIELD_ROW_COUNT) {
            if (result != _gameField.getCellState(cordX = i, cordY = columNum)) {
                return null
                break
            }
        }
        if (result == CellState.STATE_EMPTY) result = null
        return result
    }

    private fun checkDiagonalLeft(): CellState? {
        var result: CellState? = _gameField.getCellState(cordX = 0, cordY = 0)
        for (i in 0 until GAME_FIELD_ROW_COUNT) {
            if (result != _gameField.getCellState(cordX = i, cordY = i)) {
                result = null
                break
            }
        }
        if (result == CellState.STATE_EMPTY) result = null
        return result
    }

    private fun checkDiagonalRight(): CellState? {
        var result: CellState? = _gameField.getCellState(cordX = 0, cordY = GAME_FIELD_CELLS_IN_ROW-1)
        for (i in 0 until GAME_FIELD_ROW_COUNT) {
            if (result != _gameField.getCellState(cordX = i, cordY = GAME_FIELD_CELLS_IN_ROW-i-1)) {
                result = null
                break
            }
        }
        if (result == CellState.STATE_EMPTY) result = null
        return result
    }

    private fun isAllCellFilled(): Boolean {
        for (i in 0 until GAME_FIELD_ROW_COUNT)
            for (j in 0 until GAME_FIELD_CELLS_IN_ROW) {
                if (_gameField.getCellState(cordX = i, cordY = j) == CellState.STATE_EMPTY)
                    return false
            }
        return true
    }
}