package de.darkmodz.gastroappv2;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import de.darkmodz.gastroapp.api.Result;

public class MainActivity extends AppCompatActivity {

    // Create a int with the kellnerID
    private static int kellnerID = new Integer(-1);
    public static void setKellnerID(int kellnerID) {
        MainActivity.kellnerID = kellnerID;
    }
    // getKelderID
    public static int getKellnerID() {
        return kellnerID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Startseite");

        /**
         * Erstellung der Nutzerkomponenten
         */
        final Button homeNext = (Button) findViewById(R.id.btn_homeNext);
        final TextView txtgreeting = (TextView) findViewById(R.id.tV_homeGreeting);
        final ImageView logo = (ImageView) findViewById(R.id.iV_homeLogo);

        /**
         * @EventListener
         */
        homeNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToTableselect();
            }
        });

        // Implementierung der EventListener für die Button btn_login
        final Button btn_login = (Button) findViewById(R.id.btn_login);
        reloadLoginButton();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(MainActivity.getKellnerID() > 0) {
                    setKellnerID(-1);
                    reloadLoginButton();
                    // Erstelle eine Toast-Meldung und zeige diese an, dass erfolgreich ausgeloggt wurde
                    Toast.makeText(getApplicationContext(), "Erfolgreich ausgeloggt", Toast.LENGTH_SHORT).show();
                } else {
                    switchToLogin();
                }
            }
        });
    }

    // If Login finished on LoginActivity reload the login button
    @Override
    protected void onResume() {
        super.onResume();
        reloadLoginButton();
    }

    /**
     * Reload the Button out of the MainActivity
     */
    // If the user is logged in, the button will be set to logout
    public void reloadLoginButton() {
        final Button btn_login = (Button) findViewById(R.id.btn_login);
        System.out.println("Reload THE Login Button");

         if(MainActivity.getKellnerID() != -1)
            btn_login.setText("Logout");
         else
             btn_login.setText("Login");
    }

    /**
     * Methode zum Wechseln zur Tischauswahl
     */
    private void switchToTableselect() {
        Intent switchActivityIntent = new Intent(this, Tableselect.class);
        startActivity(switchActivityIntent);
    }

    /**
     * Switch to the LoginActivity
     */
    private void switchToLogin() {
        Intent switchActivityIntent = new Intent(this, Login.class);
        startActivity(switchActivityIntent);
    }

    public static void setCostomColorClick(Button button, int colorFrom, int colorTo) {
        button.setBackgroundColor(colorTo);
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            button.setBackgroundColor(colorFrom);
        }).start();
    }
}