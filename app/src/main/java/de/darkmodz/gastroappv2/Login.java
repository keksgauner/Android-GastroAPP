package de.darkmodz.gastroappv2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import de.darkmodz.gastroapp.api.RepositoryCallback;
import de.darkmodz.gastroapp.api.Request;

public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        // Implementierung der EventListener für die Button back
        final Button back = (Button) findViewById(R.id.login_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Implementierung der EventListener für den Button login
        final Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Wenn username und passwort korrekt sind, dann weiterleitung zur Hauptseite
                final String username = ((EditText) findViewById(R.id.username)).getText().toString();
                final String password = ((EditText) findViewById(R.id.password)).getText().toString();
                new Request().validLogin(username, password, new RepositoryCallback<HashMap<String, String>>() {
                    @Override
                    public void onComplete(HashMap<String, String> result) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                // Führe die Abfrage auf dem main thread aus
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (result.get("0") == "1") {
                                            // Schreibe eine Toast-Meldung, dass der Login erfolgreich war
                                            Toast.makeText(getApplicationContext(), "Login erfolgreich", Toast.LENGTH_SHORT).show();
                                            new Request().setLogin(username, password);
                                            System.out.println(MainActivity.kellnerID);
                                            finish();
                                        } else {
                                            // Schreibe eine Toast-Meldung, dass die Eingabe falsch war
                                            Toast.makeText(getApplicationContext(), "Login fehlgeschlagen", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                    }
                });

            }
        });
    }
}