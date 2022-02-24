package de.darkmodz.gastroapp.api;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class Inventory {
    private HashMap<String, ArrayList<String>> inventar = new HashMap<String, ArrayList<String>>();
    private TextView view;
    private String build;
    private double gesamtpreis;

    public Inventory(TextView view) {
        this.view = view;
    }

    public HashMap<String, ArrayList<String>> getMap() {
        return inventar;
    }
    public double getCurrentPrize() {
        return gesamtpreis;
    }

    public void add(String what, String entry) {
        if(inventar.get(what) != null)
            inventar.get(what).add(entry);
        else {
            inventar.put(what, new ArrayList<String>());
            inventar.get(what).add(entry);
        }
    }

    public void remove(String what, int position) {
        System.out.println("Inventory: Try to remove from position " + position);
        inventar.get(what).remove(position);
    }

    public void update() {
        build = "";
        gesamtpreis = 0;
        for (String vaule : inventar.keySet()) {
                build += "\n" + vaule + ": \n";
                for (int i=0; i < inventar.get(vaule).size(); i++) {
                    String[] splitted = inventar.get(vaule).get(i).split(";");
                    build += " - " + splitted[1] + "/" + splitted[2] + "\n";
                    gesamtpreis += Double.valueOf(splitted[2]);

                }
        }
        gesamtpreis = Math.round(gesamtpreis*100);
        gesamtpreis /= 100;

        build += "\n" + "Aktuell bei " + gesamtpreis + " Euro";
        view.setText(build);
    }

    public void absenden(int accountID, int tabelID) {
        for (String vaule : inventar.keySet()) {
            for (int i=0; i < inventar.get(vaule).size(); i++) {
                String[] splitted = inventar.get(vaule).get(i).split(";");
                System.out.println("Database send insert request");

                new Request().sendOrder(accountID, tabelID, Integer.valueOf(splitted[0]));

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
