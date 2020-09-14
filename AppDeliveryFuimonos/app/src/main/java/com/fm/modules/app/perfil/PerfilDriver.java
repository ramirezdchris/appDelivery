package com.fm.modules.app.perfil;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.fm.modules.R;
import com.fm.modules.app.login.Logued;
import com.fm.modules.models.Driver;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PerfilDriver extends AppCompatActivity {

    AppCompatTextView tvHelloUser;
    AppCompatTextView tvHoras;

    Driver d = Logued.driverLogued;
    SimpleDateFormat ffhora = new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_profile);
        getSupportActionBar().hide();

        tvHelloUser = findViewById(R.id.tvHelloUser);
        tvHoras = findViewById(R.id.tvHoras);

        System.out.println("Hora Entrada: " +d.getHoraDeEntrada());
        System.out.println("Hora Salida: " +d.getHoraDeSalida());

        double diferencia;
        diferencia = d.getHoraDeEntrada().getHours() - d.getHoraDeSalida().getHours();
        tvHelloUser.setText("Hola " +d.getNombreDriver());
        tvHoras.setText("Horario: " +ffhora.format(d.getHoraDeEntrada()) +"-" +ffhora.format(d.getHoraDeSalida()) + " Difencia: " +diferencia);

    }
}
