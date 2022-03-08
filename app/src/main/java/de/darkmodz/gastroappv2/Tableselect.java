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

import java.util.ArrayList;
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
                ArrayList<String[]> sorted = sortArray(result);
                for (String[] vaule : sorted) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            createButton(Integer.valueOf(vaule[0]), vaule[0]);
                        }
                    });
                }
            }
        });

    }

    // Buble sort
    private ArrayList<String[]> sortArray(HashMap<String,String> map) {
        ArrayList<String[]> array = new ArrayList<>();
        // Hole mir alle Infos vom table
        for (String vaule : map.keySet()) {
            String[] splitted = map.get(vaule).split(";");
            array.add(splitted);
        }
        // Sortiere die array
        int vergleiche = 0;
        // Die äußere Schleife läuft vom letzten Element immer eins weniger
        for(int repeat = array.size() - 1; repeat > 0; repeat--) {
            vergleiche++;
            // Die innere Schleife läuft vom ersten bis zum repeat eintrag
            for (int sort = 0; sort < repeat; sort++) {
                if (Integer.valueOf(array.get(sort)[0]) > Integer.valueOf(array.get(sort + 1)[0])) {
                    // Tausche die benachbarten Elemente
                    String[] temp = array.get(sort);
                    array.set(sort, array.get(sort + 1));
                    array.set(sort + 1, temp);
                }
            }
        }
        return array;
    }
}