package com.example.kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var ed_name: EditText? = null
    private var tv_text: TextView? = null
    private var tv_name: TextView? = null
    private var tv_winner: TextView? = null
    private var tv_mmora: TextView? = null
    private var tv_cmora: TextView? = null
    private var btn_scissor: RadioButton? = null
    private var btn_stone: RadioButton? = null
    private var btn_paper: RadioButton? = null
    private var btn_mora: Button? = null

    private val moraResultsMap: Map<Int, String>
        get() { return initMoraResultsMap() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_main)

        initEelements()
    }

    private fun initEelements() {
        val ed_name = findViewById<EditText>(R.id.ed_name)
        val tv_text = findViewById<TextView>(R.id.tv_text)
        val tv_name = findViewById<TextView>(R.id.tv_name)
        val tv_winner = findViewById<TextView>(R.id.tv_winner)
        val tv_mmora = findViewById<TextView>(R.id.tv_mmora)
        val tv_cmora = findViewById<TextView>(R.id.tv_cmora)
        val btn_scissor = findViewById<RadioButton>(R.id.btn_scissor)
        val btn_stone = findViewById<RadioButton>(R.id.btn_stone)
        val btn_paper = findViewById<RadioButton>(R.id.btn_paper)
        val btn_mora = findViewById<Button>(R.id.btn_mora)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initMoraResultsMap(): Map<Int, String> {
        return mapOf(0 to getString(R.string.btn_scissor), 1 to getString(R.string.btn_stone), 2 to getString(R.string.btn_paper))
    }

    private fun moraResult() {

        val ed_name = ed_name?: return
        val tv_text = tv_text?: return
        val tv_name = tv_name?: return
        val tv_winner = tv_winner?: return
        val tv_cmora = tv_cmora?: return

        if (ed_name.length() < 1) {
            Toast.makeText(this, "請輸入玩家姓名", Toast.LENGTH_LONG).show(); return
        }

        val resultForMe = moraResultForMe()
        val resultForPC = moraResultForPC()
        val moraResult = moraWinner(resultForMe, resultForPC)

        tv_text.text = String.format("%s\n%s", getString(R.string.tv_text), ed_name.text)
        tv_name.text = "${getString(R.string.tv_name)}\n${moraResultsMap[resultForMe]}"
        tv_cmora.text = "${getString(R.string.tv_cmora)}\n${moraResultsMap[resultForPC]}"
        tv_winner.text = "${getString(R.string.tv_winner)}\n${moraWinner(moraResult)}"
    }
    private fun moraWinner(result: Boolean?): String {
        val isWin = result ?: return "平局，請再試一次！"
        if (isWin) { return "恭喜你獲勝了!!!" }
        return "可惜，電腦獲勝了!"
    }
    private fun moraWinner(meResult: Int, pcResult: Int): Boolean? {

        var result: Boolean? = null

        if (meResult == 0) {
            when (pcResult) {
                1 -> result = false
                2 -> result = true
            }

            return result
        }

        if (meResult == 1) {
            when (pcResult) {
                0 -> result = true
                2 -> result = false
            }

            return result
        }

        if (meResult == 2) {
            when (pcResult) {
                0 -> result = false
                1 -> result = true
            }

            return result
        }

        return result
    }
    private fun moraResultForMe(): Int {

        val btn_cissor = findViewById<RadioButton>(R.id.btn_scissor)
        val btn_stone = findViewById<RadioButton>(R.id.btn_stone)
        val btn_paper = findViewById<RadioButton>(R.id.btn_paper)

        var answer = 0

        when {
            btn_cissor.isChecked -> answer = 0
            btn_stone.isChecked -> answer = 1
            btn_paper.isChecked -> answer = 2
        }

        return answer
    }
    private fun moraResultForPC(): Int {
        return (Math.random() * 2).toInt()
    }

}
