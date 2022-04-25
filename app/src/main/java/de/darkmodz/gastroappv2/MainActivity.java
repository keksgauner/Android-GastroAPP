package de.darkmodz.gastroappv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    // Create a int with the kellnerID
    public static int kellnerID;
    public static void setKellnerID(int kellnerID) {
        MainActivity.kellnerID = kellnerID;
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
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToLogin();
            }
        });
    }


    /**
     * @customMethoden
     */
    private void switchToTableselect() {
        Intent switchActivityIntent = new Intent(this, Tableselect.class);
        startActivity(switchActivityIntent);
    }

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