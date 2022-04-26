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

import de.darkmodz.gastroapp.api.Inventory;
import de.darkmodz.gastroapp.api.RepositoryCallback;
import de.darkmodz.gastroapp.api.Request;

public class Abrechnung extends AppCompatActivity {
    /**
     * Globalvariablen
     */
    private static Inventory inventory;
    private static Inventory inventory2;

    private Abrechnung context;
    private TableLayout tableLayout;
    private TableLayout tableLayout2;
    private double gesamtpreis;

    /**
     * Methoden
     */
    public Abrechnung getContext() {
        return context;
    }
    public static Inventory getInventory() {
        return inventory;
    }
    public static Inventory getInventory2() {
        return inventory2;
    }

    public Button createNewButton(String text){
        /**
         *  Button erstellen und positionieren
         */
        Button entry = new Button(getContext());
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,30,0,0);
        layoutParams.gravity = Gravity.RIGHT;
        layoutParams.weight = 5;
        entry.setLayoutParams(layoutParams);

        /**
         *  Button Design festlegen
         */
        entry.setTextColor(Color.WHITE);
        entry.setBackgroundColor(Color.BLACK);
        entry.setGravity(Gravity.CENTER);

        /**
         * Button konfiguration
         */
        entry.setText(text);
        return entry;
    }

    public TextView createNewTextView(String[] splitted){
        /**
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
        /**
         *  TableRow erstellen und positionieren
         */
        TableRow entry = new TableRow(getContext());
        float pixelsWidth =  400 * context.getResources().getDisplayMetrics().density;
        float pixelsHeight =  50 * context.getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) pixelsWidth, (int) pixelsHeight);
        entry.setLayoutParams(layoutParams);

        /**
         * TableRow konfiguration
         */

        return entry;
    }

    public View createNewView(){
        /**
         *  View erstellen und positionieren
         */
        View entry = new View(getContext());
        float pixelsHeight =  1 * context.getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.height = (int) pixelsHeight;
        layoutParams.setMargins(0,10,0,10);
        entry.setLayoutParams(layoutParams);

        /**
         * View konfiguration
         */
        entry.setBackgroundColor(Color.BLACK);

        return entry;
    }

    public void createButton(String catecory, String[] splitted, int position, TableLayout tableLayout3){

        /*
         * Erstelle ein TableRow um zwei Objekte nebeneinander zu bekommen
         */
        final TableRow tableRow = createNewTableRow();

        /*
         * Erstelle ein neuen Button, Text und View
         */
        Button button = createNewButton("Move");
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
        tableLayout3.addView(tableRow);
        tableLayout3.addView(view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), ""+splitted[1]+" wurde gemoved!",
                        Toast.LENGTH_SHORT).show();

                if(tableLayout3 == tableLayout2){
                    getInventory().add(catecory, getInventory2().getMap().get(catecory).get(position));
                    getInventory2().remove(catecory, position);

                    // Reload ALL
                    getInventory().update();
                    getInventory2().update();

                    TextView gesamt = findViewById(R.id.textAbrechnung);
                    gesamt.setText("Es kostet " + getInventory().getCurrentPrize() + " Euro");
                    TextView gesamt2 = findViewById(R.id.textAbrechnung2);
                    gesamt2.setText("Es kostet " + getInventory2().getCurrentPrize() + " Euro");
                    doReloadList();
                    doReloadList2();
                }
                if(tableLayout3 == tableLayout){
                    // Aus der Tabelle löschen
                    tableLayout.removeView(tableRow);
                    getInventory2().add(catecory, getInventory().getMap().get(catecory).get(position));
                    getInventory().remove(catecory, position);

                    // Reload ALL
                    getInventory().update();
                    getInventory2().update();

                    TextView gesamt = findViewById(R.id.textAbrechnung);
                    gesamt.setText("Es kostet " + getInventory().getCurrentPrize() + " Euro");
                    TextView gesamt2 = findViewById(R.id.textAbrechnung2);
                    gesamt2.setText("Es kostet " + getInventory2().getCurrentPrize() + " Euro");
                    doReloadList();
                    doReloadList2();
                 }
            }
        });
    }

    public void update() {
        tableLayout.removeAllViews();
        gesamtpreis = 0;

        request();

    }

    public void request() {
        /**
         * Request Product
         */
        new Request().getCurrentOrders(Tableselect.getTischnummer(), new RepositoryCallback<HashMap<String, String>>() {
            @Override
            public void onComplete(HashMap<String, String> result) {

                if(result != null)
                for (String vaule : result.keySet()) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            String[] splitted = result.get(vaule).split(";");
                            gesamtpreis += Double.valueOf(splitted[2]);
                            System.out.println("Abrechnug: " + gesamtpreis + " mit " + splitted[2]);

                            preis(gesamtpreis);
                            getInventory2().add("Rechnung", splitted[0] + ";" + splitted[1] + ";" + splitted[2]);

                            doReloadList2();
                        }
                    });
                }


            }
        });

    }

    public void preis(double gesamtpreis2) {
        gesamtpreis2 = Math.round(gesamtpreis2*100);
        gesamtpreis2 /= 100;
        setText("Aktuell bei " + gesamtpreis2 + " Euro");
        System.out.println("Aktuell bei " + gesamtpreis2 + " Euro");
    }

    public void setText(String text) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                TextView feedback = findViewById(R.id.textAbrechnung);
                feedback.setText(text);
            }
        });

    }

    public void doReloadList() {
        tableLayout.removeAllViews();

        /**
         * Request Delete
         */
        for (String vaule : getInventory().getMap().keySet()) {
            for (int i=0; i < getInventory().getMap().get(vaule).size(); i++) {
                String[] splitted = getInventory().getMap().get(vaule).get(i).split(";");
                createButton(vaule, splitted, i, tableLayout);
            }
            TextView gesamt = findViewById(R.id.textAbrechnung);
            gesamt.setText("Es kostet " + getInventory().getCurrentPrize() + " Euro");
        }
    }
    public void doReloadList2() {
        tableLayout2.removeAllViews();

        /**
         * Request Delete
         */
        for (String vaule : getInventory2().getMap().keySet()) {
            for (int i=0; i < getInventory2().getMap().get(vaule).size(); i++) {
                String[] splitted = getInventory2().getMap().get(vaule).get(i).split(";");
                createButton(vaule, splitted, i, tableLayout2);
            }
            TextView gesamt = findViewById(R.id.textAbrechnung2);
            gesamt.setText("Es kostet " + getInventory2().getCurrentPrize() + " Euro");
        }
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
        tableLayout2 = (TableLayout) findViewById(R.id.abrechnungTableInvoices2);


        /**
         * Variablen initialisieren
         */
        context = this;
        inventory = new Inventory(findViewById(R.id.textAbrechnung));
        inventory2 = new Inventory(findViewById(R.id.textAbrechnung2));

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
                //new Request().setAllFinishOrder(Tableselect.getTischnummer());
                getInventory().finished();
                finish();
            }
        });
        Button buttonFinish2 = (Button) findViewById(R.id.buttonAbrechnungFinihed2);
        buttonFinish2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInventory2().finished();
                finish();
            }
        });
        update();
    }
}