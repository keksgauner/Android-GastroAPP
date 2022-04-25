package de.darkmodz.gastroappv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import de.darkmodz.gastroapp.api.Inventory;

public class Bestellmenue extends AppCompatActivity {
    /**
     * Globalvariablen
     */
    private Bestellmenue context;
    private static Inventory inventory;

    /**
     * Methoden
     */
    public Bestellmenue getContext() {
        return context;
    }
    public static Inventory getInventory() {
        return inventory;
    }

    private void switchTo(String menu) {
        Class toClass = null;
        switch (menu) {
            case "Vorspeise":
                toClass = Vorspeise.class;
                break;
            case "Haupspeise":
                toClass = Hauptspeise.class;
                break;
            case "Nachspeise":
                toClass = Nachspeise.class;
                break;
            case "Getraenke":
                toClass = Getraenke.class;
                break;
            case "Menues":
                toClass = Menues.class;
                break;
            case "Bestellen":
                toClass = Bestellen.class;
                break;
            case "Abrechnung":
                toClass = Abrechnung.class;
                break;
        }

        Intent switchActivityIntent = new Intent(this, toClass);
        startActivity(switchActivityIntent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bestellmenue);
        setTitle("Bestellmenü");

        /**
         * Variablen initialisieren
         */
        context = this;
        inventory = new Inventory(findViewById(R.id.textUebersichtBestellung));

        /**
         * Setze die Listeners um die Fenster zu wechseln
         */
        Button buttonVorspeise = (Button) findViewById(R.id.buttonVorspeise);
        buttonVorspeise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchTo("Vorspeise");
            }
        });

        Button buttonHaupspeise = (Button) findViewById(R.id.buttonHaupspeise);
        buttonHaupspeise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchTo("Haupspeise");
            }
        });

        Button buttonNachspeise = (Button) findViewById(R.id.buttonNachspeise);
        buttonNachspeise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchTo("Nachspeise");
            }
        });

        Button buttonGetraenke = (Button) findViewById(R.id.buttonGetraenke);
        buttonGetraenke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchTo("Getraenke");
            }
        });

        Button buttonMenues = (Button) findViewById(R.id.buttonMenues);
        buttonMenues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchTo("Menues");
            }
        });

        Button buttonBestellen = (Button) findViewById(R.id.buttonBestellen);
        buttonBestellen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchTo("Bestellen");
            }
        });

        Button buttonAbrechnung = (Button) findViewById(R.id.buttonAbrechnung);
        buttonAbrechnung.setVisibility(View.INVISIBLE);
        if(MainActivity.getKellnerID() > 0)buttonAbrechnung.setVisibility(View.VISIBLE);
        buttonAbrechnung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchTo("Abrechnung");
            }
        });

    }
}