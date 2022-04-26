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

    public void createButton(int id, String[] splitted){

        /**
         * Erstelle ein TableRow um zwei Objekte nebeneinander zu bekommen
         */
        final TableRow tableRow = createNewTableRow();

        /**
         * Erstelle ein neuen Button, Text und View
         */
        Button button = createNewButton("Add");
        TextView textView = createNewTextView(splitted);
        View view = createNewView();

        /**
         * Button und Text zum TableRow Hinzufügen
         */
        tableRow.addView(textView);
        tableRow.addView(button);

        /**
         * View und TableRow zum eigentlichen Layout hinzufügen
         */
        tableLayout.addView(tableRow);
        tableLayout.addView(view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), ""+splitted[1]+" wurde hinzugefügt.",
                        Toast.LENGTH_SHORT).show();

                // Aus der Tabelle löschen
                tableLayout.removeView(tableRow);

                getInventory().add("Rechnung", splitted[0] + ";" + splitted[1] + ";" + splitted[2]);
                getInventory().update();
                TextView gesamt = findViewById(R.id.textAbrechnung2);
                gesamt.setText("Es kostet " + getInventory().getCurrentPrize() + " Euro");
                doReloadList();

                /*
                new Request().setFinishOrder(Integer.valueOf(splitted[0]));

                tableLayout.removeView(tableRow);

                // Update after a second
                try {
                    tableLayout.removeAllViews();
                    // Refresh the Activity
                    context.recreate();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    update();
                }
                */

            }
        });
    }

    public void createButton(String catecory, String[] splitted, int position){

        /*
         * Erstelle ein TableRow um zwei Objekte nebeneinander zu bekommen
         */
        final TableRow tableRow = createNewTableRow();

        /*
         * Erstelle ein neuen Button, Text und View
         */
        Button button = createNewButton("Remove");
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
        tableLayout2.addView(tableRow);
        tableLayout2.addView(view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), ""+splitted[1]+" wurde aus der Liste gelöscht!",
                        Toast.LENGTH_SHORT).show();

                getInventory().remove(catecory, position);
                getInventory().update();
                TextView gesamt = findViewById(R.id.textPreisBestellen);
                gesamt.setText("Es kostet " + getInventory().getCurrentPrize() + " Euro");
                doReloadList();
            }
        });
    }

    public void update() {
        tableLayout.removeAllViews();
        gesamtpreis = 0;

        request();
        //request("Menues");

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
                            createButton(Integer.valueOf(splitted[0]), splitted);
                            gesamtpreis += Double.valueOf(splitted[2]);
                            System.out.println("Abrechnug: " + gesamtpreis + " mit " + splitted[2]);

                            preis(gesamtpreis);
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

    public void createCatecory(String[] catecory){

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
        tableLayout2.addView(tableRow);
        tableLayout2.addView(view);
    }

    public void doReloadList() {
        tableLayout2.removeAllViews();

        /**
         * Request Delete
         */
        for (String vaule : getInventory().getMap().keySet()) {
            createCatecory(vaule);
            for (int i=0; i < getInventory().getMap().get(vaule).size(); i++) {
                String[] splitted = getInventory().getMap().get(vaule).get(i).split(";");
                createButton(vaule, splitted, i);
            }
            TextView gesamt = findViewById(R.id.textAbrechnung2);
            gesamt.setText("Es kostet " + getInventory().getCurrentPrize() + " Euro");
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
        inventory = new Inventory(findViewById(R.id.textAbrechnung2));

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