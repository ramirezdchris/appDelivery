package com.fm.modules.app.perfil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.fm.modules.R;
import com.fm.modules.app.login.Logon;
import com.fm.modules.app.login.Logued;
import com.fm.modules.models.Driver;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PerfilDriver extends AppCompatActivity {

    AppCompatTextView tvHelloUser;
    AppCompatTextView tvHoras;
    MaterialButton btnLogout;

    Driver d = Logued.driverLogued;
    SimpleDateFormat ffhora = new SimpleDateFormat("HH:mm");

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_profile);
        getSupportActionBar().hide();
        sharedPreferences = getApplicationContext().getSharedPreferences("LogonData", Context.MODE_PRIVATE);

        tvHelloUser = findViewById(R.id.tvHelloUser);
        tvHoras = findViewById(R.id.tvHoras);
        btnLogout = findViewById(R.id.btnLogout);

        System.out.println("Hora Entrada: " +d.getHoraDeEntrada());
        System.out.println("Hora Salida: " +d.getHoraDeSalida());

        double diferencia;
        diferencia =  d.getHoraDeSalida().getHours() - d.getHoraDeEntrada().getHours();
        tvHelloUser.setText("Hola " +d.getNombreDriver());
        tvHoras.setText("Horario: " +ffhora.format(d.getHoraDeEntrada()) +"-" +ffhora.format(d.getHoraDeSalida()) + " Difencia: " +diferencia);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    editor = sharedPreferences.edit();
                    editor.putString("email", "neles");
                    editor.putString("password", "neles");
                    editor.commit();
                } catch (Exception ignore) {
                }

                Logued.driverLogued = null;
                Intent intent = new Intent(getApplicationContext(), Logon.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }
}
