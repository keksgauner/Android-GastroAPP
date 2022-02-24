package de.darkmodz.gastroappv2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.HashMap;

import de.darkmodz.gastroapp.api.RepositoryCallback;
import de.darkmodz.gastroapp.api.Request;

public class Tableselect extends AppCompatActivity {

    /**
     * Globalvariablen
     */
    private Tableselect context;
    private LinearLayout tableLayout;
    private static int Tischnummer;

    /**
     * Methoden
     */
    public Tableselect getContext() {
        return context;
    }

    public  LinearLayout getLinearLayout(){
        return tableLayout;
    }

    public static int getTischnummer() {
        return Tischnummer;
    }

    private void switchToBestellmenue() {
        Intent switchActivityIntent = new Intent(this, Bestellmenue.class);
        startActivity(switchActivityIntent);
    }

    public void createButton(int id, String text){
        /*
        *  Button erstellen und positionieren
        */
        Button entry = new Button(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(20,30,20,0);
        entry.setLayoutParams(layoutParams);

        /*
        *  Button Design festlegen
        */
        entry.setTextColor(Color.WHITE);
        entry.setBackgroundColor(Color.BLACK);
        entry.setGravity(Gravity.CENTER);

        /*
        * Button konfiguration
        */
        entry.setId(id);
        entry.setText(text);
        getLinearLayout().addView(entry);

        entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tischnummer = id;
                Toast.makeText(getContext(), "Sie haben Tisch "+id+" Ausgewählt!",
                        Toast.LENGTH_SHORT).show();
                MainActivity.setCostomColorClick(entry, Color.BLACK, Color.GREEN);
                switchToBestellmenue();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tableselect);
        setTitle("Wählen Sie Ihre Tischnummer");

        /**
         * Nutzerelemente hinzufügen
         */
        ScrollView tableView = findViewById(R.id.tableScrollView);
        tableLayout = tableView.findViewById(R.id.tableLinearLayout);

        /**
         * Variablen initialisieren
         */
        context = this;

        /**
         * Request Tables
         */
        new Request().getTables(new RepositoryCallback<HashMap<String, String>>() {
            @Override
            public void onComplete(HashMap<String, String> result) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Load Data...", Toast.LENGTH_SHORT).show();
                    }
                });
                for (String vaule : result.keySet()) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            String[] splitted = result.get(vaule).split(";");
                            createButton(Integer.valueOf(splitted[0]), splitted[0]);
                        }
                    });
                }
            }
        });

    }
}