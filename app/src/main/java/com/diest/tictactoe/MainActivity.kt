package com.diest.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.diest.tictactoe.controler.GameController
import com.diest.tictactoe.controler.IGameControllerObserver
import com.diest.tictactoe.databinding.ActivityMainBinding
import com.diest.tictactoe.models.CellState

class MainActivity : AppCompatActivity(), View.OnClickListener, IGameControllerObserver {

    private var playersFigure = CellState.STATE_X
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var buttons: ArrayList<ImageButton> = arrayListOf()
    private var gameController = GameController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        gameController.attachObserver(this)

        buttons.add(binding.bt11)
        buttons.add(binding.bt12)
        buttons.add(binding.bt13)
        buttons.add(binding.bt21)
        buttons.add(binding.bt22)
        buttons.add(binding.bt23)
        buttons.add(binding.bt31)
        buttons.add(binding.bt32)
        buttons.add(binding.bt33)
        gameRestart()
        setContentView(binding.root)
    }

    private fun gameRestart() {
        playersFigure = CellState.STATE_X
        for(b in buttons) {
            b.isEnabled = true
            b.setOnClickListener(this)
            b.setImageResource(0)
        }
    }

    private fun gameEnd() {
        for (b in buttons) {
            b.isEnabled = false
        }
    }

    private fun getButtonXY(view: View): Array<Int> {
        when (view.id) {
            R.id.bt_11 -> return arrayOf(0,0)
            R.id.bt_12 -> return arrayOf(0,1)
            R.id.bt_13 -> return arrayOf(0,2)
            R.id.bt_21 -> return arrayOf(1,0)
            R.id.bt_22 -> return arrayOf(1,1)
            R.id.bt_23 -> return arrayOf(1,2)
            R.id.bt_31 -> return arrayOf(2,0)
            R.id.bt_32 -> return arrayOf(2,1)
            R.id.bt_33 -> return arrayOf(2,2)
        }
        return arrayOf()
    }

    override fun onClick(view: View?) {
        if (view == null) return
        view?.isEnabled = false
        val buttonXY = getButtonXY(view = view)
        gameController.set(cordX = buttonXY[0], cordY = buttonXY[1], playersFigure)
        var button = findViewById<ImageButton>(view.id)
        button.setImageResource(if (playersFigure == CellState.STATE_X)
            R.drawable.ic_baseline_clear_24 else R.drawable.ic_baseline_panorama_fish_eye_24)
        when(playersFigure) {
            CellState.STATE_X -> playersFigure = CellState.STATE_O
            else -> playersFigure = CellState.STATE_X
        }
    }

    override fun <T> update(msg: T) {
        gameEnd()
        Toast.makeText(this, msg as String, Toast.LENGTH_LONG).show()
    }
}