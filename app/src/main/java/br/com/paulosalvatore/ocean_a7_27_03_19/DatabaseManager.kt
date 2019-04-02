package br.com.paulosalvatore.ocean_a7_27_03_19

import android.content.ContentValues

class DatabaseManager private constructor(val helper: DatabaseHelper) {
    init {
        DatabaseManager.instancia = this
    }

    companion object {
        private var instancia: DatabaseManager? = null

        fun getInstancia(helper: DatabaseHelper) = instancia ?: DatabaseManager(helper)

        /*fun getInstancia(helper: DatabaseHelper): DatabaseManager {
            if (instancia == null) {
                instancia = DatabaseManager(helper)
            }

            return instancia!!
        }*/
    }

    private var db = helper.writableDatabase

    fun abrirConexao() {
        if (!db.isOpen) {
            db = helper.writableDatabase
        }
    }

    fun fecharConexao() {
        if (db.isOpen) {
            db.close()
        }
    }

    fun criarPosicao(posicao: Posicao) {
        val contentValues = ContentValues()
        contentValues.put("latitude", posicao.latitude)
        contentValues.put("longitude", posicao.longitude)
        contentValues.put("data_hora", posicao.dataHora)

        abrirConexao()
        val id = db.insert("posicoes", null, contentValues)
        fecharConexao()

        posicao.id = id.toInt()
    }

    fun obterPosicoes(): List<Posicao> {
        val posicoes = mutableListOf<Posicao>()

        val sql = "SELECT * FROM posicoes"
        abrirConexao()
        val cursor = db.rawQuery(sql, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val latitude = cursor.getDouble(cursor.getColumnIndex("latitude"))
                val longitude = cursor.getDouble(cursor.getColumnIndex("longitude"))
                val dataHora = cursor.getString(cursor.getColumnIndex("data_hora"))

                val posicao = Posicao(id, latitude, longitude, dataHora)
                posicoes.add(posicao)
            } while (cursor.moveToNext())

            cursor.close()
        }

        fecharConexao()

        return posicoes
    }
}