package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView resultTxt;
    private Button calcButton;
    private RadioButton mujerButton;
    private RadioButton hombreButton;
    private EditText edadText;
    private EditText metrosText;
    private EditText cmText;
    private EditText pesoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG).show();

        findViews();
        setupButtonClickListener();
    }

    private void setupButtonClickListener() {
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Calculando Francisco Vargas...", Toast.LENGTH_SHORT).show();
                calculateIMC();
            }
        });
    }

    private void calculateIMC() {
        try {
            String edadStr = edadText.getText().toString().trim();
            String metrosStr = metrosText.getText().toString().trim();
            String cmStr = cmText.getText().toString().trim();
            String pesoStr = pesoText.getText().toString().trim();

            if (edadStr.isEmpty() || metrosStr.isEmpty() || cmStr.isEmpty() || pesoStr.isEmpty()) {
                resultTxt.setText("Por favor, ingresa todos los valores.");
                return;
            }

            int edad = Integer.parseInt(edadStr);
            int metros = Integer.parseInt(metrosStr);
            double cm = Double.parseDouble(cmStr);
            int pesoKg = Integer.parseInt(pesoStr);

            double alturaMetros = metros + (cm / 100);

            if (alturaMetros <= 0) {
                resultTxt.setText("La altura debe ser mayor que 0.");
                return;
            }

            double imc = pesoKg / (alturaMetros * alturaMetros);

            // Si la edad es menor a 16 años, mostrar la advertencia correspondiente
            if (edad < 16) {
                String advertencia = mujerButton.isChecked() ?
                        "Para una interpretación correcta consulta los percentiles de talla y peso para niñas." :
                        "Para una interpretación correcta consulta los percentiles de talla y peso para niños.";

                resultTxt.setText("Tu IMC es: " + new DecimalFormat("0.00").format(imc) + "\n" + advertencia);
                return;
            }

            // Mostrar resultado normal para mayores de 16 años
            displayResult(imc);

        } catch (NumberFormatException e) {
            resultTxt.setText("Error: Ingresa valores numéricos válidos.");
        }
    }

    private void displayResult(double imc) {
        DecimalFormat df = new DecimalFormat("0.00");
        String result = "Tu IMC es: " + df.format(imc) + "\n";

        String categoria;
        if (imc < 18.5)
            categoria = "Bajo peso";
        else if (imc < 25)
            categoria = "Normal";
        else if (imc < 30)
            categoria = "Sobrepeso";
        else
            categoria = "Obesidad";

        resultTxt.setText(result + categoria);
    }

    private void findViews() {
        resultTxt = findViewById(R.id.text_view_result);
        mujerButton = findViewById(R.id.radio_button_mujer);
        hombreButton = findViewById(R.id.radio_button_hombre);
        edadText = findViewById(R.id.edit_text_edad);
        metrosText = findViewById(R.id.edit_text_metros);
        cmText = findViewById(R.id.edit_text_cm);
        pesoText = findViewById(R.id.edit_text_peso);
        calcButton = findViewById(R.id.button_calcular);
    }
}
