package de.darkmodz.gastroapp.api;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class Inventory {
    private HashMap<String, ArrayList<String>> inventar = new HashMap<String, ArrayList<String>>();
    private TextView view;
    private String build;
    private float gesammtpreis;

    public Inventory(TextView view) {
        this.view = view;
    }

    public void add(String what, String entry) {
        if(inventar.get(what) != null)
            inventar.get(what).add(entry);
        else {
            inventar.put(what, new ArrayList<String>());
            inventar.get(what).add(entry);
        }
    }

    public void update() {
        build = "";
        gesammtpreis = 0;
        for (String vaule : inventar.keySet()) {
                build += "\n" + vaule + ": ";
                for (int i=0; i < inventar.get(vaule).size(); i++) {
                    String[] splitted = inventar.get(vaule).get(i).split(";");
                    build += " " + splitted[1] + "/" + splitted[2] + "\n";
                    gesammtpreis += Float.valueOf(splitted[2]);

                }
        }
        gesammtpreis = Math.round(gesammtpreis*100);
        gesammtpreis = gesammtpreis/100;

        build += "\n" + "Aktuell bei " + gesammtpreis + " Euro";
        view.setText(build);
    }

    public void absenden() {
        for (String vaule : inventar.keySet()) {
            for (int i=0; i < inventar.get(vaule).size(); i++) {
                String[] splitted = inventar.get(vaule).get(i).split(";");
                System.out.println("Database send insert request");

                new Request().sendOrder(0, 1, Integer.valueOf(splitted[0]));

            }
        }
        inventar.clear();
        update();
    }
    public void finished() {
        for (String vaule : inventar.keySet()) {
            for (int i=0; i < inventar.get(vaule).size(); i++) {
                String[] splitted = inventar.get(vaule).get(i).split(";");
                System.out.println("Database send insert request");

                new Request().setFinishOrder(Integer.valueOf(splitted[0]));

            }
        }
        inventar.clear();
        update();
    }
}
