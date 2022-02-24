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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import de.darkmodz.gastroapp.api.RepositoryCallback;
import de.darkmodz.gastroapp.api.Request;

public class Abrechnung extends AppCompatActivity {
    /**
     * Globalvariablen
     */
    private Abrechnung context;
    private TableLayout tableLayout;
    private double gesamtpreis;

    /**
     * Methoden
     */
    public Abrechnung getContext() {
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
        entry.setText("Bezahlt");
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

    public void createButton(int id, String[] splitted){

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
                Toast.makeText(getContext(), ""+splitted[1]+" ist Bezahlt.",
                        Toast.LENGTH_SHORT).show();

                new Request().setFinishOrder(Integer.valueOf(splitted[0]));

                tableLayout.removeView(tableRow);

                // Update after a second
                try {
                    tableLayout.removeAllViews();
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    update();
                }

            }
        });
    }

    public void update() {
        tableLayout.removeAllViews();
        gesamtpreis = 0;

        request("Vorspeise");
        request("Hauptspeise");
        request("Nachspeise");
        request("Trinken");
        request("Menues");

        TextView feedback = findViewById(R.id.textAbrechnung);
        feedback.setText("Aktuell bei " + gesamtpreis + " Euro");

    }

    public void request(String name) {
        /**
         * Request Product
         */
        new Request().getCurrentOrders(name, new RepositoryCallback<HashMap<String, String>>() {
            @Override
            public void onComplete(HashMap<String, String> result) {

                for (String vaule : result.keySet()) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            String[] splitted = result.get(vaule).split(";");
                            createButton(Integer.valueOf(splitted[0]), splitted);
                            gesamtpreis += Double.valueOf(splitted[2]);
                        }
                    });
                }
            }
        });
        gesamtpreis = Math.round(gesamtpreis*100);
        gesamtpreis /= 100;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abrechnung);
        setTitle("Abrechnung");

        /**
         * Nutzerelemente hinzufügen
         */
        tableLayout = (TableLayout) findViewById(R.id.abrechnungTableInvoices);


        /**
         * Variablen initialisieren
         */
        context = this;

        /**
         * Handle finish all
         */
        Button buttonAbrechnung = (Button) findViewById(R.id.buttonAbrechnungSave);
        buttonAbrechnung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button buttonFinish = (Button) findViewById(R.id.buttonAbrechnungFinihed);
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Request().setAllFinishOrder(Tableselect.getTischnummer());
                finish();
            }
        });
        update();
    }
}