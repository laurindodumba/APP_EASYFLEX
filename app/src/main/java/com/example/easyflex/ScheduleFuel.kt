package com.example.easyflex

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ScheduleFuel : AppCompatActivity() {

    private lateinit var txInputAlcool: TextInputLayout
    private lateinit var editAlcool: TextInputEditText
    private lateinit var txInputGasolina: TextInputLayout
    private lateinit var editGasolina: TextInputEditText
    private lateinit var btnComparar: Button


    private lateinit var spinnerGasolina: Spinner
    private lateinit var spinnerAlcool: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_schedule_fuel)

        inicializar()
        configurarSpinners()

        btnComparar.setOnClickListener {
            val resultado = calcularMelhorPreco()
            if (resultado != null) {

                val intent = Intent(this, ResultadoActivity::class.java)
                intent.putExtra("resultado", resultado)

                // Também envia o tipo selecionado nos Spinners
                intent.putExtra("tipoGasolina", spinnerGasolina.selectedItem.toString())
                intent.putExtra("tipoAlcool", spinnerAlcool.selectedItem.toString())

                startActivity(intent)
            }
        }
    }

    private fun inicializar() {
        txInputAlcool = findViewById(R.id.inputAlcool)
        editAlcool = findViewById(R.id.editAlcool)
        txInputGasolina = findViewById(R.id.inputGasolina)
        editGasolina = findViewById(R.id.editGasolina)
        btnComparar = findViewById(R.id.btnalcool)

        spinnerGasolina = findViewById(R.id.spinner_menu)
        spinnerAlcool = findViewById(R.id.spinner_menu2)
    }

    private fun configurarSpinners() {
        val gasolinaAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.lista_gasolina,
            android.R.layout.simple_spinner_item
        )
        gasolinaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGasolina.adapter = gasolinaAdapter

        val alcoolAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.lista_alcool,
            android.R.layout.simple_spinner_item
        )
        alcoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAlcool.adapter = alcoolAdapter
    }

    private fun calcularMelhorPreco(): String? {
        val precoAlcool = editAlcool.text.toString()
        val precoGasolina = editGasolina.text.toString()

        if (precoAlcool.isEmpty()) {
            txInputAlcool.error = "Digite o preço do álcool"
            return null
        }

        if (precoGasolina.isEmpty()) {
            txInputGasolina.error = "Digite o preço da gasolina"
            return null
        }

        val alcool = precoAlcool.toDouble()
        val gasolina = precoGasolina.toDouble()
        val resultado = alcool / gasolina

        return if (resultado >= 0.7) {
            "Melhor usar Gasolina"
        } else {
            "Melhor usar Álcool"
        }
    }
}
