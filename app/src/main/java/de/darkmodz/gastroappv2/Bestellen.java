package de.darkmodz.gastroappv2;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import de.darkmodz.gastroapp.api.RepositoryCallback;
import de.darkmodz.gastroapp.api.Request;

public class Bestellen extends AppCompatActivity {
    /**
     * Globalvariablen
     */
    private Bestellen context;
    private TableLayout tableLayout;

    /**
     * Methoden
     */
    public Bestellen getContext() {
        return context;
    }

    public Button createNewButton(){
        /*
         *  Button erstellen und positionieren
         */
        Button entry = new Button(getContext());
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,30,0,0);
        layoutParams.gravity = Gravity.RIGHT;
        layoutParams.weight = 5;
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
        entry.setText("Löschen");
        return entry;
    }

    public TextView createNewTextView(String[] splitted){
        /*
         *  TextView erstellen und positionieren
         */
        TextView entry = new TextView(getContext());
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,40,0,0);
        layoutParams.gravity = Gravity.LEFT;
        layoutParams.weight = 1;
        entry.setLayoutParams(layoutParams);

        /*
         *  TextView Design festlegen
         */
        entry.setTextColor(Color.BLACK);
        entry.setGravity(Gravity.LEFT);

        /*
         * TextView konfiguration
         */
        entry.setText(splitted[1] + "\n" + splitted[2] + " Euro");
        return entry;
    }
    public TextView createNewTextView(String text){
        /*
         *  TextView erstellen und positionieren
         */
        TextView entry = new TextView(getContext());
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,40,0,0);
        layoutParams.gravity = Gravity.LEFT;
        layoutParams.weight = 1;
        entry.setLayoutParams(layoutParams);

        /*
         *  TextView Design festlegen
         */
        entry.setTextColor(Color.BLACK);
        entry.setGravity(Gravity.LEFT);

        /*
         * TextView konfiguration
         */
        entry.setText(text);
        return entry;
    }

    public TableRow createNewTableRow(){
        /*
         *  TableRow erstellen und positionieren
         */
        TableRow entry = new TableRow(getContext());
        float pixelsWidth =  400 * context.getResources().getDisplayMetrics().density;
        float pixelsHeight =  50 * context.getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) pixelsWidth, (int) pixelsHeight);
        entry.setLayoutParams(layoutParams);

        /*
         * TableRow konfiguration
         */

        return entry;
    }

    public View createNewView(){
        /*
         *  View erstellen und positionieren
         */
        View entry = new View(getContext());
        float pixelsHeight =  1 * context.getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.height = (int) pixelsHeight;
        layoutParams.setMargins(0,10,0,10);
        entry.setLayoutParams(layoutParams);

        /*
         * View konfiguration
         */
        entry.setBackgroundColor(Color.BLACK);

        return entry;
    }

    public void createCatecory(String catecory){

        /*
         * Erstelle ein TableRow um zwei Objekte nebeneinander zu bekommen
         */
        final TableRow tableRow = createNewTableRow();

        /*
         * Erstelle ein neuen Button, Text und View
         */
        TextView textView = createNewTextView(catecory);
        View view = createNewView();

        /*
         * Button und Text zum TableRow Hinzufügen
         */
        tableRow.addView(textView);

        /*
         * View und TableRow zum eigentlichen Layout hinzufügen
         */
        tableLayout.addView(tableRow);
        tableLayout.addView(view);
    }

    public void createButton(String catecory, String[] splitted, int position){

        /*
         * Erstelle ein TableRow um zwei Objekte nebeneinander zu bekommen
         */
        final TableRow tableRow = createNewTableRow();

        /*
         * Erstelle ein neuen Button, Text und View
         */
        Button button = createNewButton();
        TextView textView = createNewTextView(splitted);
        View view = createNewView();

        /*
         * Button und Text zum TableRow Hinzufügen
         */
        tableRow.addView(textView);
        tableRow.addView(button);

        /*
         * View und TableRow zum eigentlichen Layout hinzufügen
         */
        tableLayout.addView(tableRow);
        tableLayout.addView(view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), ""+splitted[1]+" wurde aus der Liste gelöscht!",
                        Toast.LENGTH_SHORT).show();

                MainActivity.setCostomColorClick(button, Color.BLACK, Color.RED);

                Bestellmenue.getInventory().remove(catecory, position);
                Bestellmenue.getInventory().update();
                TextView gesamt = findViewById(R.id.textPreisBestellen);
                gesamt.setText("Es kostet " + Bestellmenue.getInventory().getCurrentPrize() + " Euro");
                doReloadList();
            }
        });
    }

    public void doReloadList() {
        tableLayout.removeAllViews();

        /**
         * Request Delete
         */
        for (String vaule : Bestellmenue.getInventory().getMap().keySet()) {
            createCatecory(vaule);
            for (int i=0; i < Bestellmenue.getInventory().getMap().get(vaule).size(); i++) {
                String[] splitted = Bestellmenue.getInventory().getMap().get(vaule).get(i).split(";");
                createButton(vaule, splitted, i);
            }
            TextView gesamt = findViewById(R.id.textPreisBestellen);
            gesamt.setText("Es kostet " + Bestellmenue.getInventory().getCurrentPrize() + " Euro");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bestellen);
        setTitle("Bestellt");
        /**
         * Nutzerelemente hinzufügen
         */
        tableLayout = (TableLayout) findViewById(R.id.bestellenTableInvoices);

        /**
         * Variablen initialisieren
         */
        context = this;

        /**
         * Handle zurück Button
         */
        Button back = findViewById(R.id.buttonZurueckBestellen);
        back.setOnClickListener(view -> finish());

        Button submit = findViewById(R.id.buttonSubmitbestellen);
        submit.setOnClickListener(view -> {
            if(!Bestellmenue.getInventory().getMap().isEmpty()) {
                Toast.makeText(getContext(), "Bestellung wurde gesendet.", Toast.LENGTH_SHORT).show();
                Bestellmenue.getInventory().absenden(0, Tableselect.getTischnummer());
                finish();
            } else
                Toast.makeText(getContext(), "Bestellung konnte nicht gesendet werden!", Toast.LENGTH_SHORT).show();
            }
        );

        doReloadList();

    }
}