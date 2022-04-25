package de.darkmodz.gastroapp.api;

import android.app.Activity;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MySQL extends Activity {

    private String db_host = null;
    private String db_port = null;
    private String db_user = null;
    private String db_password = null;
    private String db_database = null;

    private static final String DRIVER = "com.mysql.jdbc.Driver";


    public MySQL(String db_host, String db_port, String db_user, String db_password, String db_database) {
        // Das Objekt wird hier gefüllt
        this.db_host = db_host;
        this.db_port = db_port;
        this.db_user = db_user;
        this.db_password = db_password;
        this.db_database = db_database;
    }

    public Result.Feedback<Connection> createConnection() {
        try {
            // Treiber initialisieren
            Class.forName(DRIVER);

            // Uri für die Verbindung zu der Datenbank
            String sqlUrl = "jdbc:mysql://" + db_host + ":" + db_port + "/" + db_database + "?characterEncoding=utf8&user=" + db_user + "&password=" + db_password;

            // Verbindung herstellen.
            Connection connection = DriverManager.getConnection(sqlUrl);
            return new Result.Feedback<Connection>(connection);
        } catch (SQLException e) {
            System.out.println("SQL Fehler: " + e.getLocalizedMessage());
            e.printStackTrace();
            return new Result.Feedback<Connection>(null);
        } catch (ClassNotFoundException e) {
            System.out.println("Database ClassNotFoundException: " + e.getMessage());
            e.printStackTrace();
            return new Result.Feedback<Connection>(null);
        }
    }

    public Result.Feedback<ArrayList<HashMap<String,String>>> query(String code) {
        try {
            Result.Feedback<Connection> connection = createConnection();

            // Statement mit Benennung der Tablle
            Statement stmt = connection.getData().createStatement();
            // Fülle ResultSet
            ResultSet result = stmt.executeQuery(code);

            ArrayList<HashMap<String, String>> entries = new ArrayList<HashMap<String, String>>();

            int columns = result.getMetaData().getColumnCount();

            // Ich zeige den Inhalt der Tabelle an. Normaler Weise würde man die
            // Ergebnisse in eine Liste schreiben und diese zurück liefern.
            while (result.next()) {
                HashMap<String, String> addArray = new HashMap<String, String>();
                for (int i = 1; i <= columns; i++) {
                    addArray.put(result.getMetaData().getColumnLabel(i), result.getString(i));
                }
                if(addArray == null)
                    System.out.println("Database can't handle this!");
                else
                    entries.add(addArray);
            }

            // Ich schließe die Streams wieder und gebe die Tabelle wieder frei.
            stmt.close();
            result.close();
            connection.getData().close();
            return new Result.Feedback<ArrayList<HashMap<String,String>>>(entries);
        } catch (SQLException e) {
            System.out.println("SQL Fehler: " + e.getLocalizedMessage());
            e.printStackTrace();
            return new Result.Feedback<ArrayList<HashMap<String,String>>>(null);
        }
    }

    public Result.Feedback<ArrayList<HashMap<String,String>>> size(String code) {
        try {
            Result.Feedback<Connection> connection = createConnection();

            // Statement mit Benennung der Tablle
            Statement stmt = connection.getData().createStatement();
            // Fülle ResultSet
            ResultSet result = stmt.executeQuery(code);

            // Muss leider die selbe syntax wie oben, da es sonst nicht funktioniert
            ArrayList<HashMap<String,String>> entries = new ArrayList<HashMap<String,String>>();

            HashMap<String, String> addArray = new HashMap<String, String>();
            int entriesSize = 0;
            while (result.next()) {
                entriesSize++;
            }
            addArray.put("0", entriesSize + "");
            if(addArray == null)
                System.out.println("Database can't handle this!");
            else
                entries.add(addArray);

            // Ich schließe die Streams wieder und gebe die Tabelle wieder frei.
            stmt.close();
            result.close();
            connection.getData().close();
            return new Result.Feedback<ArrayList<HashMap<String,String>>>(entries);
        } catch (SQLException e) {
            System.out.println("SQL Fehler: " + e.getLocalizedMessage());
            e.printStackTrace();
            return new Result.Feedback<ArrayList<HashMap<String,String>>>(null);
        }
    }

    public void insert(String tableName, HashMap<String, String> row_arrays) {
        try {
            System.out.println("Database get insert request");

            String colums = null;
            for (String vaule:row_arrays.keySet()) {
                if(colums != null)
                    colums += "," +vaule;
                else
                    colums = vaule;
            }

            String vaules = null;
            for (String vaule:row_arrays.values()) {
                if(vaules != null)
                    vaules += "," + vaule;
                else
                    vaules = vaule;
            }

            Result.Feedback<Connection> connection = createConnection();
            // Statement mit Benennung der Tablle
            String query = "INSERT INTO " + tableName + " (" + colums + ") VALUES (" + vaules + ");";
            Statement stmt = connection.getData().createStatement();
            // Sende Query
            stmt.executeUpdate(query);

            // Ich schließe die Streams wieder und gebe die Tabelle wieder frei.
            stmt.close();
            connection.getData().close();
        } catch (SQLException e) {
            System.out.println("SQL Fehler: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public void update(String tableName, HashMap<String,String> row_fields, HashMap<String,String> row_where) {
        try {

            String field_update = null;
            for (String vaule:row_fields.keySet()) {
                if(field_update != null)
                    field_update += "," + vaule + "=" + row_fields.get(vaule);
                else
                    field_update = vaule + "=" + row_fields.get(vaule);
            }

            String field_where = null;
            for (String vaule:row_where.keySet()) {
                if(field_where != null)
                    field_where += " AND " + vaule + "=" + row_where.get(vaule);
                else
                    field_where = vaule + "=" + row_where.get(vaule);

            }

            Result.Feedback<Connection> connection = createConnection();

            // Statement mit Benennung der Tablle
            String query = "UPDATE " + tableName + " SET " + field_update + " WHERE " + field_where + ";";
            System.out.println(query);
            Statement stmt = connection.getData().createStatement();
            // Sende Query
            stmt.executeUpdate(query);


            // Ich schließe die Streams wieder und gebe die Tabelle wieder frei.
            stmt.close();
            connection.getData().close();
        } catch (SQLException e) {
            System.out.println("SQL Fehler: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    @Deprecated
    public void delete(String tableName, ArrayList<String> array_where) {

        try {
            String field_where = null;
            for (int i=0; i < array_where.size(); i++) {
                field_where = array_where.get(i) + " and " + field_where;
            }

            Result.Feedback<Connection> connection = createConnection();

            // Statement mit Benennung der Tablle
            String query = "DELETE FROM " + tableName + " WHERE (" + field_where + ");";
            Statement stmt = connection.getData().createStatement();
            // Fülle ResultSet
            ResultSet result = stmt.executeQuery(query);

            // Ich schließe die Streams wieder und gebe die Tabelle wieder frei.
            stmt.close();
            result.close();
            connection.getData().close();
    } catch (SQLException e) {
        System.out.println("SQL Fehler: " + e.getLocalizedMessage());
        e.printStackTrace();
    }
    }

    public String validation(String data) {
        data.replace("'", "\'");

        return data;
    }

}
