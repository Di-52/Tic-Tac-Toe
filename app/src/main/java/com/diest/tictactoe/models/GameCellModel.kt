package com.diest.tictactoe.models

class GameCellModel(val cordX: Int, val cordY: Int) {

    private var _state: CellState = CellState.STATE_EMPTY

    fun setState(gameCellState: CellState) {
        _state = gameCellState
    }

    fun getState(): CellState {
        return _state
    }

    override fun toString(): String {
        return "GameCell X:$cordX Y:$cordY State:${_state.toString()}"
    }
}