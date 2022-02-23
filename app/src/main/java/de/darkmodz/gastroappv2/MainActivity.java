package de.darkmodz.gastroappv2;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import de.darkmodz.gastroapp.api.Inventory;

public class MainActivity extends AppCompatActivity {

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
    }

    /**
     * @customMethoden
     */
    private void switchToTableselect() {
        Intent switchActivityIntent = new Intent(this, Tableselect.class);
        startActivity(switchActivityIntent);
    }
}