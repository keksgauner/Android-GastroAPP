package de.darkmodz.gastroapp.api;

import java.util.ArrayList;
import java.util.HashMap;

// request muss asyncron sein sonst gibt es ein NetworkOnMainThreadException
// deshalb muss es im Background abgespielt werden

// Communicating with the main thread


public class Request {
    private MySQL mysql;

    public Request() {
        // Setze Datenbank Daten
        mysql = new MySQL("85.14.222.246", "3306", "GastroApp","7LEErmZJAe6c16S[", "GastroApp");
    }

    public void getTables(final RepositoryCallback<HashMap<String,String>> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Database is getting ask");
                    Result.Feedback<ArrayList<HashMap<String,String>>> results = mysql.query("SELECT " +
                            "`Tables`.`ID`, " +
                            "`Tables`.`Nummer`, " +
                            "`Tables`.`Ort` " +
                            "FROM `Tables` " +
                            "WHERE 1 ORDER BY `Tables`.`ID` ASC;");

                    HashMap<String,String> result = new HashMap<>();

                    for(int i=0; i < results.getData().size(); i++)
                    {
                        HashMap<String,String> oneMap = results.getData().get(i);
                        // ID TypeID Name Price Description Available
                        result.put(oneMap.get("ID"), oneMap.get("ID") + ";" + oneMap.get("Nummer") + ";" + "");
                    }

                    callback.onComplete(result);
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onComplete(null);
                }
            }
        }).start();
    }

    public void getProducts(final String name, final RepositoryCallback<HashMap<String,String>> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Database is getting ask");
                    Result.Feedback<ArrayList<HashMap<String,String>>> results = mysql.query("SELECT " +
                            "`Products`.`ID`, " +
                            "`Products`.`TypeID`, " +
                            "`Products`.`Name`, " +
                            "`Products`.`Price`, " +
                            "`Products`.`Description`, " +
                            "`Products`.`Available`, " +
                            "`Products`.`Weight`" +
                            "FROM `Products` " +
                            "LEFT JOIN `Type` ON `Type`.`Name` = '" + name +"' " +
                            "WHERE `TypeID` = `Type`.`ID` AND `Products`.`Available` = true ORDER BY `Products`.`Weight` ASC;");

                    HashMap<String,String> result = new HashMap<>();

                    for(int i=0; i < results.getData().size(); i++)
                    {
                        HashMap<String,String> oneMap = results.getData().get(i);
                        // ID TypeID Name Price Description Available
                        result.put(oneMap.get("ID"), oneMap.get("ID") + ";" + oneMap.get("Name") + ";" + oneMap.get("Price")+ ";" + oneMap.get("Description"));
                    }

                    callback.onComplete(result);
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onComplete(null);
                }
            }
        }).start();
    }

    public void sendOrder(final int accountID, final int tabelID, final int productID) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Database is getting ask");
                    HashMap<String,String> map = new HashMap<String, String>();
                    // `AccountID`, `TabelID`, `ProductID`, `Quantity`, `Paid`, `Processing`, `Timestamp`
                    if(accountID != 0)
                        map.put("`AccountID`", String.valueOf(accountID));
                    else
                        map.put("`AccountID`", null);
                    map.put("`TabelID`", String.valueOf(tabelID));
                    map.put("`ProductID`", String.valueOf(productID));
                    map.put("`Quantity`", "1");
                    map.put("`Paid`", "false");
                    map.put("`Processing`", "true");
                    map.put("`Timestamp`" , "NOW()");
                    mysql.insert("`Orders`", map);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getCurrentOrders(final String name, final RepositoryCallback<HashMap<String,String>> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Database is getting ask");
                    Result.Feedback<ArrayList<HashMap<String,String>>> results = mysql.query("SELECT " +
                            "`Orders`.`ID`, " +
                            "`Orders`.`AccountID`, " +
                            "`Orders`.`TabelID`, " +
                            "`Orders`.`ProductID`, " +
                            "`Products`.`Name`, " +
                            "`Products`.`Price`, " +
                            "`Orders`.`Quantity`, " +
                            "`Orders`.`Paid`, " +
                            "`Orders`.`Processing`," +
                            "`Orders`.`Timestamp`" +
                            "FROM `Orders` " +
                            "LEFT JOIN `Type` ON `Type`.`Name` = '" + name +"' " +
                            "LEFT JOIN `Products` ON `Products`.`ID` = `Orders`.`ProductID` " +
                            "WHERE  `Products`.`ID` = `Orders`.`ProductID` " +
                            "AND `Products`.`TypeID` = `Type`.`ID` " +
                            "AND `Orders`.`Paid` = false " +
                            "AND `Processing` = true "  +
                            ";");

                    HashMap<String,String> result = new HashMap<>();

                    for(int i=0; i < results.getData().size(); i++)
                    {
                        HashMap<String,String> oneMap = results.getData().get(i);
                        // `ID`, `AccountID`, `TabelID`, `ProductID`, `Quantity`, `Paid`, `Processing`, `Timestamp`
                        result.put(oneMap.get("ID"), oneMap.get("ID") + ";" + oneMap.get("Name") + ";" + oneMap.get("Price") + ";" + oneMap.get("Quantity"));
                    }

                    callback.onComplete(result);
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onComplete(null);
                }
            }
        }).start();
    }

    public void setFinishOrder(final int orderID) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Database is getting ask");
                    HashMap<String,String> toUpdate = new HashMap<String, String>();
                    toUpdate.put("`Processing`", "false");
                    HashMap<String,String> whereUpdate = new HashMap<String, String>();
                    whereUpdate.put("`ID`", String.valueOf(orderID));
                    mysql.update("`Orders`", toUpdate, whereUpdate);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void setAllFinishOrder(final int tabelID) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Database is getting ask");
                    HashMap<String,String> toUpdate = new HashMap<String, String>();
                    toUpdate.put("`Processing`", "false");
                    HashMap<String,String> whereUpdate = new HashMap<String, String>();
                    whereUpdate.put("`TabelID`", String.valueOf(tabelID));
                    mysql.update("`Orders`", toUpdate, whereUpdate);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
