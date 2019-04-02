package br.com.paulosalvatore.ocean_a7_27_03_19

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val databaseHelper = DatabaseHelper(this)
        val databaseManager = DatabaseManager.getInstancia(databaseHelper)

        val posicao = Posicao(
                latitude = 1.0,
                longitude = 2.0,
                dataHora = SystemClock.elapsedRealtime().toString()
        )
        databaseManager.criarPosicao(posicao)

        val posicoes = databaseManager.obterPosicoes()

        textView.text = ""
        posicoes.forEach {
            textView.append("${it.id}, ${it.latitude}, ${it.longitude}, ${it.dataHora}\n")
        }
    }
}
