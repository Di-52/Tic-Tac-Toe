package com.diest.tictactoe.controler

interface IGameControllerObserver {

    fun <T: Any?> update(msg: T)

}